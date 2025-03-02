package com.androidmakers.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.ic_network_blog
import fr.paug.androidmakers.ui.ic_network_linkedin
import fr.paug.androidmakers.ui.ic_network_x
import org.jetbrains.compose.resources.painterResource


@Composable
fun SocialButtons(
    speaker: Speaker,
    onClickOnItem: (SocialsItem) -> Unit
) {
  Row(
      horizontalArrangement = Arrangement.End
  ) {
    for (socialsItem in speaker.socials.orEmpty().filterNotNull()) {
      IconButton(
          onClick = {
            onClickOnItem(socialsItem)
          }
      ) {
        val socialName = socialsItem.name?.lowercase().orEmpty()
        when {
          socialName.contains("twitter") || socialName == "x" -> {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Res.drawable.ic_network_x),
                contentDescription = socialsItem.name
            )
          }

          socialName.contains("blog") -> {
            Icon(
                painter = painterResource(Res.drawable.ic_network_blog),
                contentDescription = socialsItem.name
            )
          }

          socialName.contains("linkedin") -> {
            Icon(
                painter = painterResource(Res.drawable.ic_network_linkedin),
                contentDescription = socialsItem.name
            )
          }

          else -> {
            Icon(
                imageVector = Icons.Rounded.Public,
                contentDescription = socialsItem.name
            )
          }
        }
      }
    }
  }
}
