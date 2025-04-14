package fr.paug.androidmakers.wear.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun SaveableLaunchedEffect(
  key: Any,
  block: suspend () -> Unit
) {
  val lastLaunchedKey = rememberSaveable { mutableStateOf<Any?>(null) }
  if (lastLaunchedKey.value != key) {
    lastLaunchedKey.value = key
    LaunchedEffect(Unit) {
      block()
    }
  }
}
