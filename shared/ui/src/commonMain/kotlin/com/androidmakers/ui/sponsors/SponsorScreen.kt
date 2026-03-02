package com.androidmakers.ui.sponsors

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.ErrorLayout
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.model.Lce
import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.model.PartnerGroup
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SponsorsScreen() {
  val viewModel = koinViewModel<SponsorsViewModel>()
  val sponsors by viewModel.values.collectAsStateWithLifecycle()
  val urlOpener = LocalUriHandler.current.toUrlOpener()

  SponsorsView(
      partnerList = sponsors,
      onSponsorClick = { viewModel.openPartnerLink(urlOpener, it) }
  )
}

@Composable
fun SponsorsView(
    partnerList: Lce<List<PartnerGroup>>,
    onSponsorClick: (partner: Partner) -> Unit
) {
  when (partnerList) {
    is Lce.Loading -> LoadingLayout()
    is Lce.Error -> ErrorLayout(enabled = true)
    is Lce.Content -> SponsorsContent(
        partnerGroups = partnerList.content,
        onSponsorClick = onSponsorClick
    )
  }
}

@Composable
private fun SponsorsContent(
    partnerGroups: List<PartnerGroup>,
    onSponsorClick: (Partner) -> Unit
) {
  LazyColumn(
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(partnerGroups) { partnerGroup ->
      TierCard(
          partnerGroup = partnerGroup,
          onSponsorClick = onSponsorClick
      )
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TierCard(
    partnerGroup: PartnerGroup,
    onSponsorClick: (Partner) -> Unit
) {
  Surface(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      color = MaterialTheme.colorScheme.surfaceContainerHigh
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      SectionHeader(
          title = partnerGroup.title.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
          }
      )
      FlowRow(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        for (partner in partnerGroup.partners) {
          PartnerLogo(
              partner = partner,
              onClick = { onSponsorClick(partner) }
          )
        }
      }
    }
  }
}

@Composable
private fun SectionHeader(title: String) {
  Text(
      modifier = Modifier.padding(bottom = 8.dp),
      text = title,
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
  )
}

@Composable
private fun PartnerLogo(
    partner: Partner,
    onClick: () -> Unit
) {
  val logoUrl = if (isSystemInDarkTheme()) {
    partner.logoUrlDark
  } else {
    partner.logoUrl
  }

  Column(
      modifier = Modifier.width(120.dp),
      horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
      AsyncImage(
          model = logoUrl,
          contentDescription = partner.name,
          modifier = Modifier
              .width(120.dp)
              .height(80.dp)
              .padding(12.dp),
          contentScale = ContentScale.Fit
      )
    }
    Text(
        text = partner.name,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 4.dp)
    )
  }
}
