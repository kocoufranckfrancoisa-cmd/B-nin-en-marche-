package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.TourismPlace
import com.example.data.model.SearchResultItem
import com.example.data.model.CategoryType
import com.example.ui.components.DetailArticleDialog
import com.example.ui.theme.BeninGold
import com.example.ui.theme.BeninGreen
import com.example.ui.viewmodel.MainViewModel

@Composable
fun DiscoverScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val places = viewModel.repository.getTourismPlaces()

    var activePlaceFilter by remember { mutableStateOf("Tous") }
    val categories = listOf("Tous", "Ville lacustre", "Lieu Historique", "Palais royal & Musée", "Réserve Naturelle", "Architecture vernaculaire", "Culture & Architecture")

    var selectedPlace by remember { mutableStateOf<TourismPlace?>(null) }

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
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_tourism_benin),
                    contentDescription = "Découvrir le Bénin",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Découvrir le Bénin",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Villes touristiques, Palais royaux, Musées, Plages, Hôtels & Gastronomie",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Filter chips
        item {
            ScrollableTabRow(
                selectedTabIndex = categories.indexOf(activePlaceFilter),
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { cat ->
                    Tab(
                        selected = activePlaceFilter == cat,
                        onClick = { activePlaceFilter = cat },
                        text = { Text(cat, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }

        val filtered = places.filter { place ->
            if (activePlaceFilter == "Tous") true else place.category.contains(activePlaceFilter) || activePlaceFilter.contains(place.category)
        }

        items(filtered) { place ->
            TourismPlaceCard(
                place = place,
                onClick = { selectedPlace = place }
            )
        }
    }

    // Detail modal
    selectedPlace?.let { place ->
        val searchItem = SearchResultItem(
            id = place.id,
            title = place.name,
            subtitle = "${place.category} • ${place.department}",
            snippet = place.description,
            categoryType = CategoryType.TOURISM
        )
        val favorites by viewModel.favoritesList.collectAsState()
        val isFav = favorites.any { it.id == place.id }

        val detailsList = mutableListOf<String>()
        detailsList.addAll(place.highlights)
        detailsList.add("Accès : ${place.addressOrAccess}")
        place.hotelOrFoodTip?.let { detailsList.add("Recommandation Hébergement/Resto : $it") }

        DetailArticleDialog(
            title = place.name,
            subtitle = "${place.category} • ${place.department}",
            category = "Tourisme",
            description = place.description,
            details = detailsList,
            isFavorite = isFav,
            onToggleFavorite = { viewModel.toggleFavoriteItem(searchItem) },
            onShare = { viewModel.shareContent(context, place.name, place.description) },
            onDismiss = { selectedPlace = null }
        )
    }
}

@Composable
fun TourismPlaceCard(
    place: TourismPlace,
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
                        text = place.category,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = place.department,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = place.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = place.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )

            place.hotelOrFoodTip?.let { tip ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Hotel,
                        contentDescription = null,
                        tint = BeninGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
