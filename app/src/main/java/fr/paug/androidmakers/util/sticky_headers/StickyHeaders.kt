package fr.paug.androidmakers.util.sticky_headers

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * From https://github.com/Doist/RecyclerViewExtensions
 *
 * Adds sticky headers capabilities to the [RecyclerView.Adapter]. Should return `true` for all
 * positions that represent sticky headers.
 */
interface StickyHeaders {
    fun isStickyHeader(position: Int): Boolean
    interface ViewSetup {
        /**
         * Adjusts any necessary properties of the `holder` that is being used as a sticky header.
         *
         * [.teardownStickyHeaderView] will be called sometime after this method
         * and before any other calls to this method go through.
         */
        fun setupStickyHeaderView(stickyHeader: View?)

        /**
         * Reverts any properties changed in [.setupStickyHeaderView].
         *
         * Called after [.setupStickyHeaderView].
         */
        fun teardownStickyHeaderView(stickyHeader: View?)
    }
}