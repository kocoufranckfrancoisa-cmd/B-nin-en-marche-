package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BeninBottomBar
import com.example.ui.components.BeninTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.BeninTheme
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = viewModel()
            val currentTab by viewModel.currentTab.collectAsState()
            val darkModeTheme by viewModel.darkModeTheme.collectAsState()
            val textSizeScale by viewModel.textSizeScale.collectAsState()
            val isSyncing by viewModel.isSyncing.collectAsState()

            BeninTheme(darkModeTheme = darkModeTheme) {
                val currentDensity = LocalDensity.current
                val scaledDensity = Density(
                    density = currentDensity.density,
                    fontScale = currentDensity.fontScale * textSizeScale
                )

                CompositionLocalProvider(LocalDensity provides scaledDensity) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            BeninTopAppBar(
                                currentTab = currentTab,
                                isSyncing = isSyncing,
                                onSearchClick = { viewModel.selectTab(AppTab.SEARCH) },
                                onFavoritesClick = { viewModel.selectTab(AppTab.FAVORITES) },
                                onSettingsClick = { viewModel.selectTab(AppTab.SETTINGS) }
                            )
                        },
                        bottomBar = {
                            BeninBottomBar(
                                selectedTab = currentTab,
                                onTabSelected = { viewModel.selectTab(it) }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            Crossfade(
                                targetState = currentTab,
                                label = "TabCrossfade"
                            ) { tab ->
                                when (tab) {
                                    AppTab.HOME -> HomeScreen(viewModel = viewModel)
                                    AppTab.HISTORY -> HistoryScreen(viewModel = viewModel)
                                    AppTab.GEOGRAPHY -> GeographyScreen(viewModel = viewModel)
                                    AppTab.CULTURE -> CultureScreen(viewModel = viewModel)
                                    AppTab.RELIGION -> ReligionsScreen(viewModel = viewModel)
                                    AppTab.DISCOVER -> DiscoverScreen(viewModel = viewModel)
                                    AppTab.QUIZ -> QuizScreen(viewModel = viewModel)
                                    AppTab.SEARCH -> SearchScreen(viewModel = viewModel)
                                    AppTab.FAVORITES -> FavoritesScreen(viewModel = viewModel)
                                    AppTab.SETTINGS -> SettingsScreen(viewModel = viewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
