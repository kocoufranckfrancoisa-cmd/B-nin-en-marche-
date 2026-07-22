package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.CulturalItem
import com.example.data.model.Proverb
import com.example.data.model.SearchResultItem
import com.example.data.model.CategoryType
import com.example.ui.components.DetailArticleDialog
import com.example.ui.theme.BeninGold
import com.example.ui.theme.BeninGreen
import com.example.ui.viewmodel.MainViewModel

@Composable
fun CultureScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val culturalItems = viewModel.repository.getCulturalItems()
    val proverbs = viewModel.repository.getProverbs()

    var selectedCategoryFilter by remember { mutableStateOf("Tous") }
    val categories = listOf("Tous", "Personnalités", "Fêtes", "Musiques", "Danses", "Gastronomie", "Artisanat", "Proverbes")

    var activeDetailItem by remember { mutableStateOf<CulturalItem?>(null) }

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
                    contentDescription = "Culture du Bénin",
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
                        text = "Culture Général Béninoise",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Personnalités, Gastronomie, Danses, Musiques & Proverbes",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Filter chips
        item {
            ScrollableTabRow(
                selectedTabIndex = categories.indexOf(selectedCategoryFilter),
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { cat ->
                    Tab(
                        selected = selectedCategoryFilter == cat,
                        onClick = { selectedCategoryFilter = cat },
                        text = { Text(cat, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }

        if (selectedCategoryFilter == "Proverbes") {
            items(proverbs) { proverb ->
                ProverbCard(proverb = proverb)
            }
        } else {
            val filtered = culturalItems.filter { item ->
                if (selectedCategoryFilter == "Tous") true else item.category == selectedCategoryFilter
            }

            items(filtered) { item ->
                CulturalItemCard(
                    item = item,
                    onClick = { activeDetailItem = item }
                )
            }
        }
    }

    // Detail Dialog Modal
    activeDetailItem?.let { item ->
        val searchItem = SearchResultItem(
            id = item.id,
            title = item.title,
            subtitle = "${item.category} • ${item.subtitle}",
            snippet = item.description,
            categoryType = CategoryType.CULTURE
        )
        val favorites by viewModel.favoritesList.collectAsState()
        val isFav = favorites.any { it.id == item.id }

        DetailArticleDialog(
            title = item.title,
            subtitle = item.subtitle,
            category = item.category,
            description = item.description,
            details = listOf(item.extraDetails, "Région d'origine : ${item.originRegion}"),
            isFavorite = isFav,
            onToggleFavorite = { viewModel.toggleFavoriteItem(searchItem) },
            onShare = { viewModel.shareContent(context, item.title, item.description) },
            onDismiss = { activeDetailItem = null }
        )
    }
}

@Composable
fun CulturalItemCard(
    item: CulturalItem,
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
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}

@Composable
fun ProverbCard(proverb: Proverb) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = BeninGold,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Proverbe ${proverb.language}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "\"${proverb.originalText}\"",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Traduction : \"${proverb.frenchTranslation}\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            Text(
                text = "Sagesse & Sens : ${proverb.meaning}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
