package inc.blubz.fitsync.ux.settings

import android.app.Application
import android.os.Build
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.blubz.fitsync.R
import org.blubz.fitsync.model.domain.type.DisplayThemeType
import org.blubz.fitsync.model.repository.SettingsRepository
import inc.blubz.fitsync.ui.compose.dialog.DialogUiState
import inc.blubz.fitsync.ui.compose.dialog.DropDownMenuDialogUiState
import inc.blubz.fitsync.ui.compose.dialog.InputDialogUiState
import inc.blubz.fitsync.ui.compose.dialog.RadioDialogDataItem
import inc.blubz.fitsync.ui.compose.dialog.RadioDialogDataItems
import inc.blubz.fitsync.ui.compose.dialog.RadioDialogUiState
import inc.blubz.fitsync.ui.compose.dialog.dismissDialog
import org.blubz.fitsync.util.ext.stateInDefault
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val application: Application,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    val uiState = SettingsUiState(
        dialogUiStateFlow = dialogUiStateFlow,
        currentThemeTitleFlow = settingsRepository.themeFlow.map { theme -> theme.getString(application) }.stateInDefault(viewModelScope, null),
        currentLastInstalledVersionCodeFlow = settingsRepository.lastInstalledVersionCodeFlow.map { versionCode -> versionCode.toString() }.stateInDefault(viewModelScope, null),
        rangeFlow = settingsRepository.rangeFlow.map { it.toString() }.stateInDefault(viewModelScope, null),
        dynamicThemeFlow = settingsRepository.dynamicThemeFlow.stateInDefault(viewModelScope, false),
        sortByLastNameFlow = settingsRepository.directorySortByLastNameFlow.stateInDefault(viewModelScope, false),

        onThemeSettingClicked = { onThemeSettingClicked() },

        onLastInstalledVersionCodeClicked = { onLastInstalledVersionCodeClicked() },
        onRangeClicked = { onRangeClicked() },
        setDynamicTheme = { checked -> settingsRepository.setDynamicThemeAsync(checked) },
        setSortByLastName = { setSortByLastName(it) }
    )

    private fun onThemeSettingClicked() = viewModelScope.launch {
        val currentTheme = settingsRepository.themeFlow.first()

        val supportedThemeTypes = if (Build.VERSION.SDK_INT > 28) {
            DisplayThemeType.values().toList()
        } else {
            listOf(DisplayThemeType.LIGHT, DisplayThemeType.DARK)
        }
        val radioItems = supportedThemeTypes.map { RadioDialogDataItem(it) { it.getString(application) } }

        dialogUiStateFlow.value = RadioDialogUiState(
            items = RadioDialogDataItems(radioItems, currentTheme),
            title = { stringResource(R.string.theme) },
            onConfirm = { theme ->
                settingsRepository.setThemeAsync(theme)
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun onLastInstalledVersionCodeClicked() = viewModelScope.launch {
        val currentValue = settingsRepository.getLastInstalledVersionCode()
        dialogUiStateFlow.value = InputDialogUiState(
            title = { "Version Code" },
            initialTextFieldText = { currentValue.toString() },
            onConfirm = { setLastInstalledVersionCode(it) },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) },
            minLength = 1
        )
    }

    private fun onRangeClicked() = viewModelScope.launch {
        val currentValue = settingsRepository.getRange()
        dialogUiStateFlow.value = DropDownMenuDialogUiState(
            title = { "Range" },
            options = SettingsRepository.RANGE_OPTIONS,
            optionToText = { it.toString() },
            initialSelectedOption = currentValue,
            onConfirm = { setRange(it) },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) },
        )
    }

    private fun setLastInstalledVersionCode(value: String) {
        value.toIntOrNull()?.let {
            settingsRepository.setLastInstalledVersionCodeAsync(it)
        }
        dismissDialog(dialogUiStateFlow)
    }

    private fun setRange(value: Int) {
        settingsRepository.setRangeAsync(value)
        dismissDialog(dialogUiStateFlow)
    }

    private fun setSortByLastName(checked: Boolean) {
        settingsRepository.setSortByLastNameAsync(checked)
    }
}
