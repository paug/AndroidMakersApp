package fr.paug.androidmakers.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

@Composable
fun stringResource(stringRes: StringResource, vararg args: Any): String {
  val context = LocalContext.current
  return StringDesc.ResourceFormatted(stringRes, *args).toString(context)
}
