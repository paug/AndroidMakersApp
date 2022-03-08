/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.paug.androidmakers.ui.util

import android.content.Context
import android.util.AttributeSet
import fr.paug.androidmakers.util.EmojiUtils.getLanguageInEmoji
import fr.paug.androidmakers.ui.adapter.RoomSchedule
import fr.paug.androidmakers.ui.adapter.ScheduleSession
import fr.paug.androidmakers.model.ScheduleSlot
import fr.paug.androidmakers.util.EmojiUtils
import androidx.fragment.app.FragmentPagerAdapter
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.fragment.VenueConferenceFragment
import fr.paug.androidmakers.ui.fragment.VenueAfterPartyFragment
import fr.paug.androidmakers.ui.fragment.VenueFloorPlanFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Checkable
import fr.paug.androidmakers.ui.util.CheckableFloatingActionButton

/**
 * An extension of [FloatingActionButton] which implements [Checkable].
 */
class CheckableFloatingActionButton : FloatingActionButton, Checkable {
    private var mChecked = false

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun setChecked(checked: Boolean) {
        if (checked == mChecked) return
        mChecked = checked
        refreshDrawableState()
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}