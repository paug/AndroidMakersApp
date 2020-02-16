package io.openfeedback.android

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.ui.core.setContent

class OpenFeedbackView @JvmOverloads constructor(context: Context,
                                                 attrs: AttributeSet? = null,
                                                 defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    fun setVotes(
            votes: List<VoteModel>,
            columnCount: Int = 2,
            onModelClicked: (VoteModel) -> Unit) {
        setContent {
            OpenFeedback(votes = votes, columnCount = columnCount, onModelClicked = onModelClicked)
        }
    }
}