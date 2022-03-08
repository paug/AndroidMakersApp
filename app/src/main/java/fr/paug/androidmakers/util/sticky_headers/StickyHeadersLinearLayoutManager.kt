package fr.paug.androidmakers.util.sticky_headers

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.paug.androidmakers.util.sticky_headers.StickyHeaders
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import fr.paug.androidmakers.util.sticky_headers.StickyHeadersLinearLayoutManager.HeaderPositionsAdapterDataObserver
import android.os.Parcelable
import android.graphics.PointF
import fr.paug.androidmakers.util.sticky_headers.StickyHeaders.ViewSetup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.os.Parcel
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import fr.paug.androidmakers.R
import androidx.annotation.RequiresApi
import android.content.res.ColorStateList
import android.net.wifi.WifiManager
import android.net.wifi.WifiConfiguration
import fr.paug.androidmakers.util.WifiUtil
import android.net.wifi.WifiInfo
import android.view.View
import java.util.ArrayList

/**
 * From https://github.com/Doist/RecyclerViewExtensions
 *
 * Adds sticky headers capabilities to your [RecyclerView.Adapter]. It must implement [StickyHeaders] to
 * indicate which items are headers.
 */
class StickyHeadersLinearLayoutManager<T> :
    LinearLayoutManager where T : RecyclerView.Adapter<*>?, T : StickyHeaders? {
    private var mAdapter: T? = null
    private var mTranslationX = 0f
    private var mTranslationY = 0f

    // Header positions for the currently displayed list and their observer.
    private val mHeaderPositions: MutableList<Int> = ArrayList(0)
    private val mHeaderPositionsObserver: AdapterDataObserver = HeaderPositionsAdapterDataObserver()

    // Sticky header's ViewHolder and dirty state.
    private var mStickyHeader: View? = null
    private var mStickyHeaderPosition = RecyclerView.NO_POSITION
    private var mPendingScrollPosition = RecyclerView.NO_POSITION
    private var mPendingScrollOffset = 0

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
    }

    /**
     * Offsets the vertical location of the sticky header relative to the its default position.
     */
    fun setStickyHeaderTranslationY(translationY: Float) {
        mTranslationY = translationY
        requestLayout()
    }

    /**
     * Offsets the horizontal location of the sticky header relative to the its default position.
     */
    fun setStickyHeaderTranslationX(translationX: Float) {
        mTranslationX = translationX
        requestLayout()
    }

    /**
     * Returns true if `view` is the current sticky header.
     */
    fun isStickyHeader(view: View): Boolean {
        return view === mStickyHeader
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        setAdapter(view.adapter)
    }

    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) {
        super.onAdapterChanged(oldAdapter, newAdapter)
        setAdapter(newAdapter)
    }

    private fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        if (mAdapter != null) {
            mAdapter!!.unregisterAdapterDataObserver(mHeaderPositionsObserver)
        }
        if (adapter is StickyHeaders) {
            mAdapter = adapter as T
            mAdapter!!.registerAdapterDataObserver(mHeaderPositionsObserver)
            mHeaderPositionsObserver.onChanged()
        } else {
            mAdapter = null
            mHeaderPositions.clear()
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val ss = SavedState()
        ss.superState = super.onSaveInstanceState()
        ss.pendingScrollPosition = mPendingScrollPosition
        ss.pendingScrollOffset = mPendingScrollOffset
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var state: Parcelable? = state
        if (state is SavedState) {
            val ss = state
            mPendingScrollPosition = ss.pendingScrollPosition
            mPendingScrollOffset = ss.pendingScrollOffset
            state = ss.superState
        }
        super.onRestoreInstanceState(state)
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        detachStickyHeader()
        val scrolled = super.scrollVerticallyBy(dy, recycler, state)
        attachStickyHeader()
        if (scrolled != 0) {
            updateStickyHeader(recycler, false)
        }
        return scrolled
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        detachStickyHeader()
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        attachStickyHeader()
        if (scrolled != 0) {
            updateStickyHeader(recycler, false)
        }
        return scrolled
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachStickyHeader()
        super.onLayoutChildren(recycler, state)
        attachStickyHeader()
        if (!state.isPreLayout) {
            updateStickyHeader(recycler, true)
        }
    }

    override fun scrollToPosition(position: Int) {
        scrollToPositionWithOffset(position, INVALID_OFFSET)
    }

    override fun scrollToPositionWithOffset(position: Int, offset: Int) {
        scrollToPositionWithOffset(position, offset, true)
    }

    private fun scrollToPositionWithOffset(
        position: Int,
        offset: Int,
        adjustForStickyHeader: Boolean
    ) {
        // Reset pending scroll.
        setPendingScroll(RecyclerView.NO_POSITION, INVALID_OFFSET)

        // Adjusting is disabled.
        if (!adjustForStickyHeader) {
            super.scrollToPositionWithOffset(position, offset)
            return
        }

        // There is no header above or the position is a header.
        val headerIndex = findHeaderIndexOrBefore(position)
        if (headerIndex == -1 || findHeaderIndex(position) != -1) {
            super.scrollToPositionWithOffset(position, offset)
            return
        }

        // The position is right below a header, scroll to the header.
        if (findHeaderIndex(position - 1) != -1) {
            super.scrollToPositionWithOffset(position - 1, offset)
            return
        }

        // Current sticky header is the same as at the position. Adjust the scroll offset and reset pending scroll.
        if (mStickyHeader != null && headerIndex == findHeaderIndex(mStickyHeaderPosition)) {
            val adjustedOffset =
                (if (offset != INVALID_OFFSET) offset else 0) + mStickyHeader!!.height
            super.scrollToPositionWithOffset(position, adjustedOffset)
            return
        }

        // Remember this position and offset and scroll to it to trigger creating the sticky header.
        setPendingScroll(position, offset)
        super.scrollToPositionWithOffset(position, offset)
    }

    override fun computeVerticalScrollExtent(state: RecyclerView.State): Int {
        detachStickyHeader()
        val extent = super.computeVerticalScrollExtent(state)
        attachStickyHeader()
        return extent
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        detachStickyHeader()
        val offset = super.computeVerticalScrollOffset(state)
        attachStickyHeader()
        return offset
    }

    override fun computeVerticalScrollRange(state: RecyclerView.State): Int {
        detachStickyHeader()
        val range = super.computeVerticalScrollRange(state)
        attachStickyHeader()
        return range
    }

    override fun computeHorizontalScrollExtent(state: RecyclerView.State): Int {
        detachStickyHeader()
        val extent = super.computeHorizontalScrollExtent(state)
        attachStickyHeader()
        return extent
    }

    override fun computeHorizontalScrollOffset(state: RecyclerView.State): Int {
        detachStickyHeader()
        val offset = super.computeHorizontalScrollOffset(state)
        attachStickyHeader()
        return offset
    }

    override fun computeHorizontalScrollRange(state: RecyclerView.State): Int {
        detachStickyHeader()
        val range = super.computeHorizontalScrollRange(state)
        attachStickyHeader()
        return range
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        detachStickyHeader()
        val vector = super.computeScrollVectorForPosition(targetPosition)
        attachStickyHeader()
        return vector
    }

    override fun onFocusSearchFailed(
        focused: View, focusDirection: Int, recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): View? {
        detachStickyHeader()
        val view = super.onFocusSearchFailed(focused, focusDirection, recycler, state)
        attachStickyHeader()
        return view
    }

    private fun detachStickyHeader() {
        if (mStickyHeader != null) {
            detachView(mStickyHeader!!)
        }
    }

    private fun attachStickyHeader() {
        if (mStickyHeader != null) {
            attachView(mStickyHeader!!)
        }
    }

    /**
     * Updates the sticky header state (creation, binding, display), to be called whenever there's a layout or scroll
     */
    private fun updateStickyHeader(recycler: RecyclerView.Recycler, layout: Boolean) {
        val headerCount = mHeaderPositions.size
        val childCount = childCount
        if (headerCount > 0 && childCount > 0) {
            // Find first valid child.
            var anchorView: View? = null
            var anchorIndex = -1
            var anchorPos = -1
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val params = child!!.layoutParams as RecyclerView.LayoutParams
                if (isViewValidAnchor(child, params)) {
                    anchorView = child
                    anchorIndex = i
                    anchorPos = params.viewAdapterPosition
                    break
                }
            }
            if (anchorView != null && anchorPos != -1) {
                val headerIndex = findHeaderIndexOrBefore(anchorPos)
                val headerPos = if (headerIndex != -1) mHeaderPositions[headerIndex] else -1
                val nextHeaderPos =
                    if (headerCount > headerIndex + 1) mHeaderPositions[headerIndex + 1] else -1

                // Show sticky header if:
                // - There's one to show;
                // - It's on the edge or it's not the anchor view;
                // - Isn't followed by another sticky header;
                if (headerPos != -1 && (headerPos != anchorPos || isViewOnBoundary(anchorView))
                    && nextHeaderPos != headerPos + 1
                ) {
                    // Ensure existing sticky header, if any, is of correct type.
                    if (mStickyHeader != null
                        && getItemViewType(mStickyHeader!!) != mAdapter!!.getItemViewType(headerPos)
                    ) {
                        // A sticky header was shown before but is not of the correct type. Scrap it.
                        scrapStickyHeader(recycler)
                    }

                    // Ensure sticky header is created, if absent, or bound, if being laid out or the position changed.
                    if (mStickyHeader == null) {
                        createStickyHeader(recycler, headerPos)
                    }
                    if (layout || getPosition(mStickyHeader!!) != headerPos) {
                        bindStickyHeader(recycler, headerPos)
                    }

                    // Draw the sticky header using translation values which depend on orientation, direction and
                    // position of the next header view.
                    var nextHeaderView: View? = null
                    if (nextHeaderPos != -1) {
                        nextHeaderView = getChildAt(anchorIndex + (nextHeaderPos - anchorPos))
                        // The header view itself is added to the RecyclerView. Discard it if it comes up.
                        if (nextHeaderView === mStickyHeader) {
                            nextHeaderView = null
                        }
                    }
                    mStickyHeader!!.translationX = getX(mStickyHeader, nextHeaderView)
                    mStickyHeader!!.translationY = getY(mStickyHeader, nextHeaderView)
                    return
                }
            }
        }
        if (mStickyHeader != null) {
            scrapStickyHeader(recycler)
        }
    }

    /**
     * Creates [RecyclerView.ViewHolder] for `position`, including measure / layout, and assigns it to
     * [.mStickyHeader].
     */
    private fun createStickyHeader(recycler: RecyclerView.Recycler, position: Int) {
        val stickyHeader = recycler.getViewForPosition(position)

        // Setup sticky header if the adapter requires it.
        if (mAdapter is ViewSetup) {
            (mAdapter as ViewSetup).setupStickyHeaderView(stickyHeader)
        }

        // Add sticky header as a child view, to be detached / reattached whenever LinearLayoutManager#fill() is called,
        // which happens on layout and scroll (see overrides).
        addView(stickyHeader)
        measureAndLayout(stickyHeader)

        // Ignore sticky header, as it's fully managed by this LayoutManager.
        ignoreView(stickyHeader)
        mStickyHeader = stickyHeader
        mStickyHeaderPosition = position
    }

    /**
     * Binds the [.mStickyHeader] for the given `position`.
     */
    private fun bindStickyHeader(recycler: RecyclerView.Recycler, position: Int) {
        // Bind the sticky header.
        recycler.bindViewToPosition(mStickyHeader!!, position)
        mStickyHeaderPosition = position
        measureAndLayout(mStickyHeader)

        // If we have a pending scroll wait until the end of layout and scroll again.
        if (mPendingScrollPosition != RecyclerView.NO_POSITION) {
            val vto = mStickyHeader!!.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    vto.removeOnGlobalLayoutListener(this)
                    if (mPendingScrollPosition != RecyclerView.NO_POSITION) {
                        scrollToPositionWithOffset(mPendingScrollPosition, mPendingScrollOffset)
                        setPendingScroll(RecyclerView.NO_POSITION, INVALID_OFFSET)
                    }
                }
            })
        }
    }

    /**
     * Measures and lays out `stickyHeader`.
     */
    private fun measureAndLayout(stickyHeader: View?) {
        measureChildWithMargins(stickyHeader!!, 0, 0)
        if (orientation == VERTICAL) {
            stickyHeader.layout(paddingLeft, 0, width - paddingRight, stickyHeader.measuredHeight)
        } else {
            stickyHeader.layout(0, paddingTop, stickyHeader.measuredWidth, height - paddingBottom)
        }
    }

    /**
     * Returns [.mStickyHeader] to the [RecyclerView]'s [RecyclerView.RecycledViewPool], assigning it
     * to `null`.
     *
     * @param recycler If passed, the sticky header will be returned to the recycled view pool.
     */
    private fun scrapStickyHeader(recycler: RecyclerView.Recycler?) {
        val stickyHeader = mStickyHeader
        mStickyHeader = null
        mStickyHeaderPosition = RecyclerView.NO_POSITION

        // Revert translation values.
        stickyHeader!!.translationX = 0f
        stickyHeader.translationY = 0f

        // Teardown holder if the adapter requires it.
        if (mAdapter is ViewSetup) {
            (mAdapter as ViewSetup).teardownStickyHeaderView(stickyHeader)
        }

        // Stop ignoring sticky header so that it can be recycled.
        stopIgnoringView(stickyHeader)

        // Remove and recycle sticky header.
        removeView(stickyHeader)
        recycler?.recycleView(stickyHeader)
    }

    /**
     * Returns true when `view` is a valid anchor, ie. the first view to be valid and visible.
     */
    private fun isViewValidAnchor(view: View?, params: RecyclerView.LayoutParams): Boolean {
        return if (!params.isItemRemoved && !params.isViewInvalid) {
            if (orientation == VERTICAL) {
                if (reverseLayout) {
                    view!!.top + view.translationY <= height + mTranslationY
                } else {
                    view!!.bottom - view.translationY >= mTranslationY
                }
            } else {
                if (reverseLayout) {
                    view!!.left + view.translationX <= width + mTranslationX
                } else {
                    view!!.right - view.translationX >= mTranslationX
                }
            }
        } else {
            false
        }
    }

    /**
     * Returns true when the `view` is at the edge of the parent [RecyclerView].
     */
    private fun isViewOnBoundary(view: View): Boolean {
        return if (orientation == VERTICAL) {
            if (reverseLayout) {
                view.bottom - view.translationY > height + mTranslationY
            } else {
                view.top + view.translationY < mTranslationY
            }
        } else {
            if (reverseLayout) {
                view.right - view.translationX > width + mTranslationX
            } else {
                view.left + view.translationX < mTranslationX
            }
        }
    }

    /**
     * Returns the position in the Y axis to position the header appropriately, depending on orientation, direction and
     * [android.R.attr.clipToPadding].
     */
    private fun getY(headerView: View?, nextHeaderView: View?): Float {
        return if (orientation == VERTICAL) {
            var y = mTranslationY
            if (reverseLayout) {
                y += (height - headerView!!.height).toFloat()
            }
            if (nextHeaderView != null) {
                y = if (reverseLayout) {
                    Math.max(nextHeaderView.bottom.toFloat(), y)
                } else {
                    Math.min((nextHeaderView.top - headerView!!.height).toFloat(), y)
                }
            }
            y
        } else {
            mTranslationY
        }
    }

    /**
     * Returns the position in the X axis to position the header appropriately, depending on orientation, direction and
     * [android.R.attr.clipToPadding].
     */
    private fun getX(headerView: View?, nextHeaderView: View?): Float {
        return if (orientation != VERTICAL) {
            var x = mTranslationX
            if (reverseLayout) {
                x += (width - headerView!!.width).toFloat()
            }
            if (nextHeaderView != null) {
                x = if (reverseLayout) {
                    Math.max(nextHeaderView.right.toFloat(), x)
                } else {
                    Math.min((nextHeaderView.left - headerView!!.width).toFloat(), x)
                }
            }
            x
        } else {
            mTranslationX
        }
    }

    /**
     * Finds the header index of `position` in `mHeaderPositions`.
     */
    private fun findHeaderIndex(position: Int): Int {
        var low = 0
        var high = mHeaderPositions.size - 1
        while (low <= high) {
            val middle = (low + high) / 2
            if (mHeaderPositions[middle] > position) {
                high = middle - 1
            } else if (mHeaderPositions[middle] < position) {
                low = middle + 1
            } else {
                return middle
            }
        }
        return -1
    }

    /**
     * Finds the header index of `position` or the one before it in `mHeaderPositions`.
     */
    private fun findHeaderIndexOrBefore(position: Int): Int {
        var low = 0
        var high = mHeaderPositions.size - 1
        while (low <= high) {
            val middle = (low + high) / 2
            if (mHeaderPositions[middle] > position) {
                high = middle - 1
            } else if (middle < mHeaderPositions.size - 1 && mHeaderPositions[middle + 1] <= position) {
                low = middle + 1
            } else {
                return middle
            }
        }
        return -1
    }

    /**
     * Finds the header index of `position` or the one next to it in `mHeaderPositions`.
     */
    private fun findHeaderIndexOrNext(position: Int): Int {
        var low = 0
        var high = mHeaderPositions.size - 1
        while (low <= high) {
            val middle = (low + high) / 2
            if (middle > 0 && mHeaderPositions[middle - 1] >= position) {
                high = middle - 1
            } else if (mHeaderPositions[middle] < position) {
                low = middle + 1
            } else {
                return middle
            }
        }
        return -1
    }

    private fun setPendingScroll(position: Int, offset: Int) {
        mPendingScrollPosition = position
        mPendingScrollOffset = offset
    }

    /**
     * Handles header positions while adapter changes occur.
     *
     * This is used in detriment of [RecyclerView.LayoutManager]'s callbacks to control when they're received.
     */
    private inner class HeaderPositionsAdapterDataObserver : AdapterDataObserver() {
        override fun onChanged() {
            // There's no hint at what changed, so go through the adapter.
            mHeaderPositions.clear()
            val itemCount = mAdapter!!.itemCount
            for (i in 0 until itemCount) {
                if (mAdapter!!.isStickyHeader(i)) {
                    mHeaderPositions.add(i)
                }
            }

            // Remove sticky header immediately if the entry it represents has been removed. A layout will follow.
            if (mStickyHeader != null && !mHeaderPositions.contains(mStickyHeaderPosition)) {
                scrapStickyHeader(null)
            }
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            // Shift headers below down.
            val headerCount = mHeaderPositions.size
            if (headerCount > 0) {
                var i = findHeaderIndexOrNext(positionStart)
                while (i != -1 && i < headerCount) {
                    mHeaderPositions[i] = mHeaderPositions[i] + itemCount
                    i++
                }
            }

            // Add new headers.
            for (i in positionStart until positionStart + itemCount) {
                if (mAdapter!!.isStickyHeader(i)) {
                    val headerIndex = findHeaderIndexOrNext(i)
                    if (headerIndex != -1) {
                        mHeaderPositions.add(headerIndex, i)
                    } else {
                        mHeaderPositions.add(i)
                    }
                }
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            var headerCount = mHeaderPositions.size
            if (headerCount > 0) {
                // Remove headers.
                for (i in positionStart + itemCount - 1 downTo positionStart) {
                    val index = findHeaderIndex(i)
                    if (index != -1) {
                        mHeaderPositions.removeAt(index)
                        headerCount--
                    }
                }

                // Remove sticky header immediately if the entry it represents has been removed. A layout will follow.
                if (mStickyHeader != null && !mHeaderPositions.contains(mStickyHeaderPosition)) {
                    scrapStickyHeader(null)
                }

                // Shift headers below up.
                var i = findHeaderIndexOrNext(positionStart + itemCount)
                while (i != -1 && i < headerCount) {
                    mHeaderPositions[i] = mHeaderPositions[i] - itemCount
                    i++
                }
            }
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            // Shift moved headers by toPosition - fromPosition.
            // Shift headers in-between by -itemCount (reverse if upwards).
            val headerCount = mHeaderPositions.size
            if (headerCount > 0) {
                if (fromPosition < toPosition) {
                    var i = findHeaderIndexOrNext(fromPosition)
                    while (i != -1 && i < headerCount) {
                        val headerPos = mHeaderPositions[i]
                        if (headerPos >= fromPosition && headerPos < fromPosition + itemCount) {
                            mHeaderPositions[i] = headerPos - (toPosition - fromPosition)
                            sortHeaderAtIndex(i)
                        } else if (headerPos >= fromPosition + itemCount && headerPos <= toPosition) {
                            mHeaderPositions[i] = headerPos - itemCount
                            sortHeaderAtIndex(i)
                        } else {
                            break
                        }
                        i++
                    }
                } else {
                    var i = findHeaderIndexOrNext(toPosition)
                    while (i != -1 && i < headerCount) {
                        val headerPos = mHeaderPositions[i]
                        if (headerPos >= fromPosition && headerPos < fromPosition + itemCount) {
                            mHeaderPositions[i] = headerPos + (toPosition - fromPosition)
                            sortHeaderAtIndex(i)
                        } else if (headerPos >= toPosition && headerPos <= fromPosition) {
                            mHeaderPositions[i] = headerPos + itemCount
                            sortHeaderAtIndex(i)
                        } else {
                            break
                        }
                        i++
                    }
                }
            }
        }

        private fun sortHeaderAtIndex(index: Int) {
            val headerPos = mHeaderPositions.removeAt(index)
            val headerIndex = findHeaderIndexOrNext(headerPos)
            if (headerIndex != -1) {
                mHeaderPositions.add(headerIndex, headerPos)
            } else {
                mHeaderPositions.add(headerPos)
            }
        }
    }

    class SavedState : Parcelable {
        var superState: Parcelable? = null
        var pendingScrollPosition = 0
        var pendingScrollOffset = 0

        constructor() {}
        constructor(`in`: Parcel) {
            superState = `in`.readParcelable(SavedState::class.java.classLoader)
            pendingScrollPosition = `in`.readInt()
            pendingScrollOffset = `in`.readInt()
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeParcelable(superState, flags)
            dest.writeInt(pendingScrollPosition)
            dest.writeInt(pendingScrollOffset)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}