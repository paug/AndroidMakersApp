package com.androidmakers.ui.sponsors

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidmakers.ui.model.Lce
import com.seiko.imageloader.rememberImagePainter
import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.model.PartnerGroup
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun SponsorsScreen(
    onSponsorClick: (partner: Partner) -> Unit
) {
  val viewModel = koinViewModel(SponsorsViewModel::class)
  val sponsors by viewModel.values.collectAsState()

  SponsorsView(
      partnerList = sponsors,
      onSponsorClick = onSponsorClick
  )
}

@Composable
fun SponsorsView(
    partnerList: Lce<List<PartnerGroup>>,
    onSponsorClick: (partner: Partner) -> Unit
) {
  when (partnerList) {
    is Lce.Loading, Lce.Error -> {
      Box(
          modifier = Modifier
              .fillMaxWidth()
              .padding(8.dp),
          contentAlignment = Alignment.Center
      ) {
        CircularProgressIndicator()
      }
    }

    is Lce.Content -> {

      LazyVerticalGrid(
          columns = GridCells.Adaptive(minSize = 128.dp),
          contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 8.dp)
      ) {

        for (partnerGroup in partnerList.content) {

          // Sponsor "group" (e.g. Organizers, Gold, etc.)
          item(span = {
            GridItemSpan(maxLineSpan)
          }) {
            Text(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = partnerGroup.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
          }

          // Sponsor logo
          for (partner in partnerGroup.partners) {
            item {
              val painter = rememberImagePainter(partner.logoUrl)
              Image(
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(80.dp)
                      .clickable {
                        onSponsorClick(partner)
                      },
                  painter = painter,
                  contentDescription = partner.name
              )
            }
          }
        }
      }
    }
  }
}
