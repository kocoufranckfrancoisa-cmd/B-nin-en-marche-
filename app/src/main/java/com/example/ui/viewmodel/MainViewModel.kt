package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.local.BeninDatabase
import com.example.data.model.*
import com.example.data.repository.BeninRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val title: String, val iconName: String) {
    HOME("Accueil", "home"),
    HISTORY("Histoire", "history_edu"),
    GEOGRAPHY("Géographie", "public"),
    CULTURE("Culture", "palette"),
    RELIGION("Religions", "auto_awesome"),
    DISCOVER("Découvrir", "explore"),
    QUIZ("Quiz", "quiz"),
    SEARCH("Recherche", "search"),
    FAVORITES("Favoris", "bookmark"),
    SETTINGS("Paramètres", "settings")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        BeninDatabase::class.java,
        "benin_app_database"
    ).build()

    val repository = BeninRepository(db.favoriteDao())

    // --- TAB STATE ---
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // --- SETTINGS STATE ---
    // Dark mode: 0 = System, 1 = Light, 2 = Dark
    private val _darkModeTheme = MutableStateFlow(0)
    val darkModeTheme: StateFlow<Int> = _darkModeTheme.asStateFlow()

    fun setDarkModeTheme(theme: Int) {
        _darkModeTheme.value = theme
    }

    // Text size scale: 0.85f, 1.0f, 1.15f, 1.3f
    private val _textSizeScale = MutableStateFlow(1.0f)
    val textSizeScale: StateFlow<Float> = _textSizeScale.asStateFlow()

    fun setTextSizeScale(scale: Float) {
        _textSizeScale.value = scale
    }

    // --- FAVORITES FROM ROOM ---
    val favoritesList = repository.allFavorites.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun toggleFavoriteItem(item: SearchResultItem) {
        viewModelScope.launch {
            repository.toggleFavorite(item)
        }
    }

    fun removeFavoriteById(id: String) {
        viewModelScope.launch {
            repository.removeFavorite(id)
        }
    }

    // --- DEPARTMENT SELECTION FOR GÉOGRAPHIE & RELIGIONS ---
    private val _selectedDepartmentName = MutableStateFlow("Atlantique")
    val selectedDepartmentName: StateFlow<String> = _selectedDepartmentName.asStateFlow()

    fun selectDepartment(deptName: String) {
        _selectedDepartmentName.value = deptName
    }

    // --- SEARCH STATE ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchResultItem>>(emptyList())
    val searchResults: StateFlow<List<SearchResultItem>> = _searchResults.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _searchResults.value = repository.searchAll(query)
    }

    // --- QUIZ STATE ---
    private val _quizQuestions = MutableStateFlow(repository.getQuizQuestions())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, Int>> = _selectedAnswers.asStateFlow()

    private val _quizCompleted = MutableStateFlow(false)
    val quizCompleted: StateFlow<Boolean> = _quizCompleted.asStateFlow()

    fun selectQuizAnswer(questionIndex: Int, answerIndex: Int) {
        val updated = _selectedAnswers.value.toMutableMap()
        updated[questionIndex] = answerIndex
        _selectedAnswers.value = updated
    }

    fun nextQuizQuestion() {
        if (_currentQuizIndex.value < _quizQuestions.value.size - 1) {
            _currentQuizIndex.value += 1
        } else {
            _quizCompleted.value = true
        }
    }

    fun previousQuizQuestion() {
        if (_currentQuizIndex.value > 0) {
            _currentQuizIndex.value -= 1
        }
    }

    fun resetQuiz() {
        _currentQuizIndex.value = 0
        _selectedAnswers.value = emptyMap()
        _quizCompleted.value = false
    }

    fun calculateQuizScore(): Int {
        var score = 0
        val questions = _quizQuestions.value
        val answers = _selectedAnswers.value
        questions.forEachIndexed { index, question ->
            if (answers[index] == question.correctAnswerIndex) {
                score++
            }
        }
        return score
    }

    // --- SHARE FUNCTIONALITY ---
    fun shareContent(context: Context, title: String, text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, "$title\n\n$text\n\n- Extrait de l'application Découvrir le Bénin")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Partager via")
        context.startActivity(shareIntent)
    }

    // --- API SYNC ARCHITECTURE STATUS ---
    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    private val _syncMessage = MutableStateFlow<String?>(null)
    val syncMessage: StateFlow<String?> = _syncMessage.asStateFlow()

    fun triggerApiSync() {
        viewModelScope.launch {
            _isSyncing.value = true
            _syncMessage.value = "Connexion au serveur de données du Bénin..."
            repository.syncDataFromRemoteApi()
            _isSyncing.value = false
            _syncMessage.value = "Informations à jour (Données locales synchronisées)"
        }
    }
}
