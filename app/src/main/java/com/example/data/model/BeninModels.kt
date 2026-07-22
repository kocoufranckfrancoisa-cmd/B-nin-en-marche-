package com.example.data.model

import androidx.annotation.DrawableRes

enum class CategoryType(val label: String) {
    HISTORY("Histoire"),
    GEOGRAPHY("Géographie"),
    CULTURE("Culture"),
    RELIGION("Religions"),
    TOURISM("Découverte"),
    PROVERB("Proverbes")
}

data class KeyFact(
    val title: String,
    val value: String,
    val description: String,
    val iconName: String = "info"
)

data class HistoricalPeriod(
    val id: String,
    val era: String,
    val periodName: String,
    val title: String,
    val description: String,
    val keyDetails: List<String>,
    val imageUrl: String? = null
)

data class DepartmentInfo(
    val name: String,
    val chefLieu: String,
    val population: String,
    val areaKm2: String,
    val communesCount: Int,
    val communesList: List<String>,
    val description: String,
    val geographyHighlights: String,
    val religions: ReligionInfo,
    val touristSpots: List<String>
)

data class ReligionInfo(
    val traditionalPct: Int,
    val christianityPct: Int,
    val islamPct: Int,
    val otherPct: Int,
    val mainSites: List<String>,
    val keyFestivals: List<String>,
    val description: String
)

data class CulturalItem(
    val id: String,
    val title: String,
    val category: String, // Musique, Danse, Gastronomie, Artisanat, Langue, Personnalité, Fête
    val subtitle: String,
    val description: String,
    val extraDetails: String,
    val originRegion: String = "Tout le Bénin"
)

data class Proverb(
    val id: String,
    val originalText: String,
    val language: String,
    val frenchTranslation: String,
    val meaning: String
)

data class TourismPlace(
    val id: String,
    val name: String,
    val category: String, // Ville, Palais, Musée, Plage, Lieu Historique, Hôtel, Restaurant
    val department: String,
    val description: String,
    val highlights: List<String>,
    val addressOrAccess: String,
    val hotelOrFoodTip: String? = null
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String
)

data class SearchResultItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val snippet: String,
    val categoryType: CategoryType
)
