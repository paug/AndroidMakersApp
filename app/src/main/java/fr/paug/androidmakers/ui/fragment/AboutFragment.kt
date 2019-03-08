package fr.paug.androidmakers.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore

import fr.paug.androidmakers.BuildConfig
import fr.paug.androidmakers.R
import fr.paug.androidmakers.databinding.FragmentAboutBinding
import fr.paug.androidmakers.manager.AndroidMakersStore
import fr.paug.androidmakers.model.Logo
import fr.paug.androidmakers.model.PartnerCollection
import fr.paug.androidmakers.model.PartnerGroup
import fr.paug.androidmakers.model.Partners
import fr.paug.androidmakers.util.CustomTabUtil
import fr.paug.androidmakers.util.WifiUtil

class AboutFragment : Fragment(), View.OnClickListener {

    private var fragmentAboutBinding: FragmentAboutBinding? = null

    private val wifiStateChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onConnectivityChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keeps this Fragment alive during configuration changes
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentAboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

//        val partners = AgendaRepository.getInstance().partners
//
//        if (partners != null) {
//            for (partnerType in PartnerGroup.PartnerType.values()) {
//                val sponsorGroup = partners[partnerType]
//                addPartnerTypeToView(sponsorGroup)
//            }
//        }

//        val partnerCollectionList = AndroidMakersStore().getPartners()
//        for (partnerCollection in partnerCollectionList) {
//            addPartnerCollectionToView(partnerCollection)
//        }

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("partners")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("Firestore", document.id + " => " + document.data)
                        val partnerCollection = document.toObject(PartnerCollection::class.java)
                        addPartnerCollectionToView(partnerCollection)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error getting documents.", exception)
                }

        // Listen to network state change
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        context!!.registerReceiver(wifiStateChangedReceiver, intentFilter)

        fragmentAboutBinding!!.twitterUserButton.setOnClickListener(this)
        fragmentAboutBinding!!.twitterHashtagButton.setOnClickListener(this)
        fragmentAboutBinding!!.youtubeButton.setOnClickListener(this)
        fragmentAboutBinding!!.wifiConnectButton.setOnClickListener(this)
        fragmentAboutBinding!!.versionTextView.text = String.format(getString(R.string.version), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)

        return fragmentAboutBinding!!.root
    }

    override fun onClick(v: View) {
        if (v === fragmentAboutBinding!!.twitterUserButton) {
            openTwitterUser()
        } else if (v === fragmentAboutBinding!!.twitterHashtagButton) {
            openTwitterHashtag()
        } else if (v === fragmentAboutBinding!!.youtubeButton) {
            openYoutube()
        } else if (v === fragmentAboutBinding!!.wifiConnectButton) {
            connectToVenuesWifi()
        }
    }

    internal fun openTwitterUser() {
        var twitterIntent: Intent
        try {
            // get the Twitter app if possible
            activity!!.packageManager.getPackageInfo("com.twitter.android", 0)
            twitterIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=" + getString(R.string.twitter_user_name)))
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (e: Exception) {
            // no Twitter app, revert to browser
            twitterIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/" + getString(R.string.twitter_user_name)))
        }

        startActivity(twitterIntent)
    }

    internal fun openTwitterHashtag() {
        var twitterIntent: Intent
        try {
            // get the Twitter app if possible
            activity!!.packageManager.getPackageInfo("com.twitter.android", 0)
            twitterIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://search?query=%23" + getString(R.string.twitter_hashtag_for_query)))
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (e: Exception) {
            // no Twitter app, revert to browser
            twitterIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/search?q=%23" + getString(R.string.twitter_hashtag_for_query)))
        }

        startActivity(twitterIntent)
    }

    internal fun openYoutube() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_channel))))
    }

    internal fun connectToVenuesWifi() {
        val context = context
        val networkId = WifiUtil.getVenuesWifiNetworkId(context!!)
        if (networkId != -1) {
            if (WifiUtil.connectToWifi(context, networkId)) {
                fragmentAboutBinding!!.wifiConnectButton.visibility = View.GONE
                fragmentAboutBinding!!.wifiAutoconnectProgress.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context!!.unregisterReceiver(wifiStateChangedReceiver)
    }

    private fun onConnectivityChanged() {
        if (WifiUtil.isCurrentlyConnectedToVenuesWifi(context!!)) {
            fragmentAboutBinding!!.wifiConnectButton.visibility = View.INVISIBLE
            fragmentAboutBinding!!.wifiAutoconnectProgress.visibility = View.GONE
        } else if (fragmentAboutBinding!!.wifiAutoconnectProgress.visibility == View.GONE) {
            // case where we are not currently trying to connect to the venue's wifi
            fragmentAboutBinding!!.wifiConnectButton.visibility = View.VISIBLE
            fragmentAboutBinding!!.wifiAutoconnectProgress.visibility = View.GONE
        } else {
            fragmentAboutBinding!!.wifiConnectButton.visibility = View.GONE
            fragmentAboutBinding!!.wifiAutoconnectProgress.visibility = View.VISIBLE
        }
    }

    private fun addPartnerTypeToView(partnerGroup: PartnerGroup?) {
        if (partnerGroup == null) {
            return
        }

        val partnersList = partnerGroup.partnersList
        if (partnersList != null && partnersList.size > 0) {
            val partnersGroupLinearLayout = LayoutInflater.from(context).inflate(R.layout.partners_group, null) as LinearLayout

            val partnerGroupHeader = partnersGroupLinearLayout.findViewById<TextView>(R.id.partners_title)
            partnerGroupHeader.setText(partnerGroup.partnerType.getName())

            val partnerLogoLayout = partnersGroupLinearLayout.findViewById<LinearLayout>(R.id.partners_layout)
            val partnerLogoSizePriority = partnerGroup.partnerType.partnerLogoSizePriority

            var index = 0
            while (index < partnersList.size) {
                val partnerRow = LayoutInflater.from(context).inflate(R.layout.partner_row, null) as LinearLayout
                partnerRow.weightSum = partnerLogoSizePriority.toFloat()

                if (partnerLogoSizePriority > 0) {
                    val partner1 = partnerRow.findViewById<ImageView>(R.id.partner1)
                    setLogoInfo(partner1, partnersList[index])
                    partner1.contentDescription = partnersList[index].name
                }

                if (partnerLogoSizePriority > 1 && partnersList.size > index + 1) {
                    val partner2 = partnerRow.findViewById<ImageView>(R.id.partner2)
                    setLogoInfo(partner2, partnersList[index + 1])
                    partner2.contentDescription = partnersList[index].name
                }

                if (partnerLogoSizePriority > 2 && partnersList.size > index + 2) {
                    val partner3 = partnerRow.findViewById<ImageView>(R.id.partner3)
                    setLogoInfo(partner3, partnersList[index + 2])
                    partner3.contentDescription = partnersList[index].name
                }

                partnerLogoLayout.addView(partnerRow)
                index += partnerLogoSizePriority
            }

            fragmentAboutBinding!!.sponsorsLayout.addView(partnersGroupLinearLayout)
        }
    }

    private fun addPartnerCollectionToView(partnerCollection: PartnerCollection) {
        val partnersList = partnerCollection.logos
        if (partnersList.isNotEmpty()) {
            val partnersGroupLinearLayout = LayoutInflater.from(context).inflate(R.layout.partners_group, null) as LinearLayout

            val partnerGroupHeader = partnersGroupLinearLayout.findViewById<TextView>(R.id.partners_title)
            partnerGroupHeader.text = partnerCollection.title

            val partnerLogoLayout = partnersGroupLinearLayout.findViewById<LinearLayout>(R.id.partners_layout)
            //val partnerLogoSizePriority = partnerGroup.partnerType.partnerLogoSizePriority

            var index = 0
            while (index < partnersList.size) {
                //TODO Flow layouts
                val partnerRow = LayoutInflater.from(context).inflate(R.layout.partner_row, null) as LinearLayout
//                partnerRow.weightSum = partnerLogoSizePriority.toFloat()
//
//                if (partnerLogoSizePriority > 0) {
                    val partner1 = partnerRow.findViewById<ImageView>(R.id.partner1)
                    setLogo(partner1, partnersList[index])
                    partner1.contentDescription = partnersList[index].name
//                }
//
                if (/*partnerLogoSizePriority > 1 &&*/ partnersList.size > index + 1) {
                    val partner2 = partnerRow.findViewById<ImageView>(R.id.partner2)
                    setLogo(partner2, partnersList[index + 1])
                    partner2.contentDescription = partnersList[index].name
                }
//
                if (/*partnerLogoSizePriority > 2 &&*/ partnersList.size > index + 2) {
                    val partner3 = partnerRow.findViewById<ImageView>(R.id.partner3)
                    setLogo(partner3, partnersList[index + 2])
                    partner3.contentDescription = partnersList[index].name
                }

                partnerLogoLayout.addView(partnerRow)
                index += 1
            }

            fragmentAboutBinding!!.sponsorsLayout.addView(partnersGroupLinearLayout)
        }
    }

    private fun setLogoInfo(partnerLogo: ImageView, partner: Partners) {
        partnerLogo.visibility = View.VISIBLE
        val options = RequestOptions()
                .placeholder(R.color.light_grey)
        Glide.with(context!!)
                .load(String.format("http://androidmakers.fr/img/partners/%s", partner.imageUrl))
                .apply(options)
                .into(partnerLogo)
        partnerLogo.setOnClickListener { CustomTabUtil.openChromeTab(context, partner.link) }
    }

    private fun setLogo(partnerLogo: ImageView, logo: Logo) {
        partnerLogo.visibility = View.VISIBLE
        val options = RequestOptions()
                .placeholder(R.color.light_grey)
        //TODO Change svg to png on website
        Glide.with(context!!)
                .load(String.format("https://androidmakers.fr", logo.logoUrl.replace("..", "")))
//                .load("https://paug.github.io/android-makers-2018/img/partners/xebia.png")
                .apply(options)
                .into(partnerLogo)
        partnerLogo.setOnClickListener { CustomTabUtil.openChromeTab(context, logo.url) }
    }

}// Required empty public constructor