package fr.paug.androidmakers.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.adapter.VenuePagerAdapter

class VenuePagerFragment : Fragment() {

    private var viewPager: ViewPager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.venue_pager, container, false)
        viewPager = view.findViewById(R.id.venue_viewpager)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager!!.adapter = VenuePagerAdapter(context, childFragmentManager)
    }

}