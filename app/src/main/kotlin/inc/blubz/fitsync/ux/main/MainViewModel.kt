package inc.blubz.fitsync.ux.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import inc.blubz.fitsync.model.domain.type.DisplayThemeType
import inc.blubz.fitsync.model.repository.SettingsRepository
import inc.blubz.fitsync.ui.navigation.DefaultNavBarConfig
import inc.blubz.fitsync.ui.navigation.ViewModelNavBar
import inc.blubz.fitsync.ui.navigation.ViewModelNavBarImpl
import inc.blubz.fitsync.util.ext.stateInDefault
import inc.blubz.fitsync.work.WorkScheduler
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val workScheduler: WorkScheduler,
    settingsRepository: SettingsRepository,
) : ViewModel(), ViewModelNavBar<NavBarItem> by ViewModelNavBarImpl(NavBarItem.ABOUT, DefaultNavBarConfig(NavBarItem.getNavBarItemRouteMap())) {
    val uiState = MainUiState(
        selectedAppThemeFlow = combine(
            settingsRepository.themeFlow.stateInDefault(viewModelScope, null),
            settingsRepository.dynamicThemeFlow.stateInDefault(viewModelScope, null)
        ) { displayThemeType, dynamicTheme ->
            SelectedAppTheme(displayThemeType ?: DisplayThemeType.SYSTEM_DEFAULT, dynamicTheme ?: false)
        }.stateInDefault(viewModelScope, null)
    )

    private var startupComplete = false
    fun startup() = viewModelScope.launch {
        if (startupComplete) {
            return@launch
        }

        // run any startup/initialization code here (NOTE: these tasks should NOT exceed 1000ms (per Google Guidelines))
        Logger.i { "Startup task..." }

        // schedule workers
        workScheduler.startPeriodicWorkSchedules()

        // Startup finished
        Logger.i { "Startup finished" }

        startupComplete = true
    }
}

data class SelectedAppTheme(val displayThemeType: DisplayThemeType, val dynamicTheme: Boolean)