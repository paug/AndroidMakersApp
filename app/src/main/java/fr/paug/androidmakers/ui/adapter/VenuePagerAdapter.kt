package fr.paug.androidmakers.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.fragment.VenueAfterPartyFragment
import fr.paug.androidmakers.ui.fragment.VenueConferenceFragment
import fr.paug.androidmakers.ui.fragment.VenueFloorPlanFragment

class VenuePagerAdapter(private val context: Context, fm: FragmentManager?) : FragmentPagerAdapter(
    fm!!
) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.venue_conference_tab)
            1 -> context.getString(R.string.venue_afterparty_tab)
            2 -> context.getString(R.string.venue_floor_plan_tab)
            else -> super.getPageTitle(position)
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> VenueConferenceFragment()
            1 -> VenueAfterPartyFragment()
            2 -> VenueFloorPlanFragment()
            else -> error("")
        }
    }

    override fun getCount(): Int {
        return 3
    }
}