package fr.paug.androidmakers.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import fr.paug.androidmakers.ui.view.FloorPlanView

class VenueFloorPlanFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val scrollView = ScrollView(context!!)
        scrollView.addView(FloorPlanView(context!!))
        return scrollView
    }
}