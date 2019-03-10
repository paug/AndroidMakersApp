package fr.paug.androidmakers.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import fr.paug.androidmakers.R
import fr.paug.androidmakers.databinding.VenueItemFragmentBinding

import fr.paug.androidmakers.manager.AgendaRepository
import fr.paug.androidmakers.manager.AndroidMakersStore
import fr.paug.androidmakers.model.Venue
import fr.paug.androidmakers.util.CustomTabUtil
import java.util.*

class VenueAfterPartyFragment : Fragment(), View.OnClickListener {

    private var venueItemFragmentBinding: VenueItemFragmentBinding? = null

    private val venueCoordinatesUri: Uri
        get() = Uri.parse("geo:" + venueInformation?.coordinates +
                "?q=" + Uri.encode(venueInformation?.name))

    private var venueInformation: Venue? = null

    private val venueDescription: String?
        get() {
            val fr = Locale.FRENCH
            return if (Locale.getDefault().language === fr.language) {
                venueInformation?.descriptionFr
            } else {
                venueInformation?.description
            }
        }

    init {
        // Keeps this Fragment alive during configuration changes
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        venueItemFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.venue_item_fragment, container, false)
        AndroidMakersStore().getVenue("afterparty") {
            venueInformation = it
            venueItemFragmentBinding!!.venue = venueInformation
            venueItemFragmentBinding!!.venueDirections.text = Html.fromHtml(venueDescription)
            venueItemFragmentBinding!!.venueLocateButton.setOnClickListener(this)
        }
        return venueItemFragmentBinding!!.root
    }

    override fun onClick(v: View) {
        if (v === venueItemFragmentBinding!!.venueLocateButton) {
            openMap()
        }
    }

    fun openMap() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, venueCoordinatesUri)
            startActivity(intent)
        } catch (e: Exception) {
            val view = view
            if (view != null) {
                Snackbar.make(view, R.string.no_maps_app_found, Snackbar.LENGTH_SHORT).show()
            }

            // Open in Webview
            CustomTabUtil.openChromeTab(context, "https://www.google.com/maps/?q=" + venueInformation?.coordinates?.replace(" ", ""))
        }
    }
}