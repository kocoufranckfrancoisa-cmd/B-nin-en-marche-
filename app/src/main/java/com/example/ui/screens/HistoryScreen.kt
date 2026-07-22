package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.HistoricalPeriod
import com.example.data.model.SearchResultItem
import com.example.data.model.CategoryType
import com.example.ui.components.DetailArticleDialog
import com.example.ui.theme.BeninGold
import com.example.ui.theme.BeninGreen
import com.example.ui.viewmodel.MainViewModel

@Composable
fun HistoryScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val periods = viewModel.repository.getHistoricalPeriods()
    var selectedPeriod by remember { mutableStateOf<HistoricalPeriod?>(null) }
    var selectedEraFilter by remember { mutableStateOf("Tous") }

    val eraFilters = listOf("Tous", "XVII-XIXe", "Résistance", "1960", "1990+")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_culture_benin),
                    contentDescription = "Histoire du Bénin",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.65f))
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Histoire & Héritage",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Des rois du Danxomè et chevaliers du Nord à la démocratie de 1990",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Interactive Timeline Header
        item {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Frise Chronologique Interactive",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(eraFilters) { era ->
                        FilterChip(
                            selected = era == selectedEraFilter,
                            onClick = { selectedEraFilter = era },
                            label = { Text(era) }
                        )
                    }
                }
            }
        }

        // Timeline Items list
        val filteredPeriods = periods.filter { period ->
            when (selectedEraFilter) {
                "XVII-XIXe" -> period.era.contains("XVII") || period.era.contains("XV")
                "Résistance" -> period.era.contains("1890")
                "1960" -> period.era.contains("1960") || period.era.contains("1975")
                "1990+" -> period.era.contains("1990")
                else -> true
            }
        }

        items(filteredPeriods) { period ->
            TimelineCardItem(
                period = period,
                onClick = { selectedPeriod = period }
            )
        }
    }

    // Detail Modal
    selectedPeriod?.let { period ->
        val searchItem = SearchResultItem(
            id = period.id,
            title = period.title,
            subtitle = "${period.periodName} (${period.era})",
            snippet = period.description,
            categoryType = CategoryType.HISTORY
        )
        val favorites by viewModel.favoritesList.collectAsState()
        val isFav = favorites.any { it.id == period.id }

        DetailArticleDialog(
            title = period.title,
            subtitle = "${period.periodName} • ${period.era}",
            category = "Histoire",
            description = period.description,
            details = period.keyDetails,
            isFavorite = isFav,
            onToggleFavorite = { viewModel.toggleFavoriteItem(searchItem) },
            onShare = { viewModel.shareContent(context, period.title, period.description) },
            onDismiss = { selectedPeriod = null }
        )
    }
}

@Composable
fun TimelineCardItem(
    period: HistoricalPeriod,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Timeline Bullet Icon
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.HistoryEdu,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = period.era,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = period.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = period.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
