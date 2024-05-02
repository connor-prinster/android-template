package inc.blubz.fitsync.ux.about

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import inc.blubz.fitsync.model.config.RemoteConfig
import inc.blubz.fitsync.model.webservice.colors.ColorService
import inc.blubz.fitsync.model.webservice.colors.dto.ColorsDto
import inc.blubz.fitsync.ui.navigation.ViewModelNav
import inc.blubz.fitsync.ui.navigation.ViewModelNavImpl
import inc.blubz.fitsync.util.ext.ApiResponse
import inc.blubz.fitsync.util.ext.message
import inc.blubz.fitsync.util.ext.onError
import inc.blubz.fitsync.util.ext.onException
import inc.blubz.fitsync.util.ext.onFailure
import inc.blubz.fitsync.util.ext.onSuccess
import inc.blubz.fitsync.util.ext.readText
import inc.blubz.fitsync.ux.about.typography.TypographyRoute
import inc.blubz.fitsync.ux.acknowledgement.AcknowledgmentsRoute
import inc.blubz.fitsync.work.WorkScheduler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import javax.inject.Inject

@HiltViewModel
class AboutViewModel
@Inject constructor(
    private val application: Application,
    private val colorService: ColorService,
    private val workScheduler: WorkScheduler,
    private val remoteConfig: RemoteConfig,
    private val fileSystem: FileSystem
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    private val resetServiceEnabledFlow: StateFlow<Boolean> = MutableStateFlow(remoteConfig.isColorServiceEnabled()).asStateFlow()

    val uiState: AboutUiState = AboutUiState(
        resetServiceEnabledFlow = resetServiceEnabledFlow,
        testQueryWebServiceCall = { testQueryWebServiceCall() },
        testFullUrlQueryWebServiceCall = { testFullUrlQueryWebServiceCall() },
        testSaveQueryWebServiceCall = { testSaveQueryWebServiceCall() },
        workManagerSimpleTest = { workManagerSimpleTest() },
        workManagerSyncTest = { workManagerSyncTest() },
        licensesClicked = { navigate(AcknowledgmentsRoute.createRoute()) },
        m3TypographyClicked = { navigate(TypographyRoute.createRoute()) }
    )

    private fun testQueryWebServiceCall() = viewModelScope.launch {
        Logger.i { "TypeSafe Call..." }
        if (!remoteConfig.isColorServiceEnabled()) {
            Logger.e { "Color Service is NOT enabled... skipping" }
            return@launch
        }

        val response = colorService.getColorsBySafeArgs()
        processWebServiceResponse(response)

        Logger.i { "========================================================" }

        processWebServiceResponse2(colorService.getColorsBySafeArgs())
    }

    private fun testFullUrlQueryWebServiceCall() = viewModelScope.launch {
        Logger.i { "Full URL Call..." }
        val response = colorService.getColorsByFullUrl()
        processWebServiceResponse(response)
    }

    private fun testSaveQueryWebServiceCall() = viewModelScope.launch {
        val outputFile = application.filesDir.toOkioPath() / "colors.json"
        colorService.getColorsToFile(fileSystem, outputFile)

        Logger.i { "Downloaded file contents:\n ${fileSystem.readText(outputFile)}" }
    }

    private fun processWebServiceResponse(response: ApiResponse<ColorsDto, Unit>) {
        response.onSuccess {
            data.colors.forEach {
                Logger.i { "Result: ${it.colorName}" }
            }
        }.onFailure {
            Logger.e { "Web Service FAILURE ${message()}" }
        }.onError {

        }.onException {

        }
    }

    private fun processWebServiceResponse2(response: ApiResponse<ColorsDto, Unit>) {
        response.onSuccess {
            data.colors.forEach {
                Logger.i { "Result: ${it.colorName}" }
            }
        }.onFailure {
            Logger.e { "Web Service FAILURE (message: [${message()}]" }
        }
    }

    private fun workManagerSimpleTest() = viewModelScope.launch {
        workScheduler.scheduleSimpleWork("test1")
        workScheduler.scheduleSimpleWork("test2")

        delay(3000)

        workScheduler.scheduleSimpleWork("test3")
    }

    private fun workManagerSyncTest() = viewModelScope.launch {
        workScheduler.scheduleSync()
        workScheduler.scheduleSync(true)

        delay(3000)

        workScheduler.scheduleSync()
    }
}