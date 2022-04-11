package fr.paug.androidmakers.ui.activity

import android.media.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VenueLayout() {

    val state = rememberScrollableState {
        it
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scrollable(
                orientation = Orientation.Vertical,
                state = state
            )
    ) {
        Image {
            
        }
    }
}
