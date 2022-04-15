package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingLayout() {
  Box(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight(),
      contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

