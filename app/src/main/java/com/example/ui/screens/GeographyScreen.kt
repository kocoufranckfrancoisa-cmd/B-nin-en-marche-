package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.DepartmentInfo
import com.example.ui.components.DepartmentSelectorChipRow
import com.example.ui.viewmodel.MainViewModel

@Composable
fun GeographyScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val departments = viewModel.repository.getDepartments()
    val selectedDeptName by viewModel.selectedDepartmentName.collectAsState()
    val activeDepartment = departments.find { it.name == selectedDeptName } ?: departments.first()

    var activeSubTab by remember { mutableStateOf("Départements") }
    val subTabs = listOf("Départements", "Relief & Climat", "Fleuves & Lacs", "Faune & Flore")

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
                    painter = painterResource(id = R.drawable.img_geography_benin),
                    contentDescription = "Géographie du Bénin",
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
                        text = "Géographie du Bénin",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "12 Départements, 77 Communes, Atacora, Fleuves & Parcs",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Sub Tab Selector
        item {
            ScrollableTabRow(
                selectedTabIndex = subTabs.indexOf(activeSubTab),
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                subTabs.forEach { tabName ->
                    Tab(
                        selected = activeSubTab == tabName,
                        onClick = { activeSubTab = tabName },
                        text = { Text(tabName, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }

        when (activeSubTab) {
            "Départements" -> {
                item {
                    DepartmentSelectorChipRow(
                        departments = departments,
                        selectedDepartmentName = selectedDeptName,
                        onDepartmentSelected = { viewModel.selectDepartment(it) }
                    )
                }

                item {
                    DepartmentDetailCard(dept = activeDepartment)
                }
            }

            "Relief & Climat" -> {
                item {
                    GeographyTopicCard(
                        title = "Relief du Bénin",
                        icon = Icons.Default.Terrain,
                        description = "Le relief béninois est peu élevé mais varié :\n• La plaine côtière basse et sableuse bordée de lagunes.\n• Les plateaux de terre de barre dans le Sud.\n• Le plateau des Collines (Dassa-Zoumé, Savè) aux reliefs granitiques.\n• La chaîne de l'Atacora au Nord-Ouest abritant le Mont Sokbaro (658 m), point le plus élevé du pays."
                    )
                }
                item {
                    GeographyTopicCard(
                        title = "Climat du Bénin",
                        icon = Icons.Default.WbSunny,
                        description = "Deux zones climatiques principales :\n• Climat Sud (Subéquatorial) : 4 saisons (2 saisons de pluies d'avril à juillet et de septembre à novembre; 2 saisons sèches).\n• Climat Nord (Soudanien) : 2 saisons (1 saison sèche de novembre à avril balayée par l'Harmattan, et 1 saison de pluies de mai à octobre)."
                    )
                }
            }

            "Fleuves & Lacs" -> {
                item {
                    GeographyTopicCard(
                        title = "Fleuves du Bénin",
                        icon = Icons.Default.Water,
                        description = "Le réseau hydrographique est dense :\n• Fleuve Ouémé (330 km), le plus long cours d'eau intérieur, arrosant les vallées fertiles du Sud.\n• Fleuve Mono, marquant la frontière avec le Togo.\n• Fleuve Couffo, se jetant dans le lac Ahémé.\n• Fleuve Niger, formant la frontière naturelle au Nord."
                    )
                }
                item {
                    GeographyTopicCard(
                        title = "Lacs & Cités Lacustres",
                        icon = Icons.Default.Pool,
                        description = "• Lac Nokoué : Berceau de Ganvié, la plus grande cité sur pilotis d'Afrique.\n• Lac Ahémé : Réputé pour sa pêche traditionnelle au calain et ses sources thermales de Possotomè.\n• Lagune de Porto-Novo et Lagune de Ouidah."
                    )
                }
            }

            "Faune & Flore" -> {
                item {
                    GeographyTopicCard(
                        title = "Parcs Nationaux & Faune Exceptionnelle",
                        icon = Icons.Default.Pets,
                        description = "• Parc National de la Pendjari : Réserve de biosphère UNESCO dans l'Atacora. Abrite des lions d'Afrique de l'Ouest, des éléphants, des hippopotames, des babouins, des guépards et plus de 300 espèces d'oiseaux.\n• Parc National du W : Réserve transfrontalière entre le Bénin, le Niger et le Burkina Faso."
                    )
                }
                item {
                    GeographyTopicCard(
                        title = "Flore & Forêts Sacrées",
                        icon = Icons.Default.Forest,
                        description = "• Baobabs centenaires majestueux dans les savanes du Nord.\n• Forêts sacrées (Forêt de Kpassè à Ouidah, Forêt d'Ita-Gbogbo) préservées par les autorités traditionnelles.\n• Cocoteraies du littoral, rônier et forêts galeries."
                    )
                }
            }
        }
    }
}

@Composable
fun DepartmentDetailCard(dept: DepartmentInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Département de ${dept.name}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "Chef-lieu : ${dept.chefLieu}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Population : ${dept.population}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Superficie : ${dept.areaKm2}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            HorizontalDivider()

            Text(
                text = dept.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Les ${dept.communesCount} Communes :",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = dept.communesList.joinToString(" • "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Sites touristiques majeurs :",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            dept.touristSpots.forEach { spot ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = spot,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun GeographyTopicCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
