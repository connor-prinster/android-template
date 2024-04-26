package inc.blubz.fitsync.startup

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import inc.blubz.fitsync.BuildConfig
import inc.blubz.fitsync.inject.IoDispatcher
import inc.blubz.fitsync.model.repository.SettingsRepository
import javax.inject.Inject

class AppUpgrade
@Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun upgradeApp() = runBlocking(ioDispatcher) {
        val lastInstalledVersionCode = settingsRepository.getLastInstalledVersionCode()
        Logger.i { "Checking for app upgrade from [$lastInstalledVersionCode]" }

        if (lastInstalledVersionCode == 0) {
            Logger.i { "Skipping app upgrade on fresh install" }
            settingsRepository.setLastInstalledVersionCodeAsync(inc.blubz.fitsync.BuildConfig.VERSION_CODE)
            return@runBlocking
        }

//        if (lastInstalledVersionCode < VERSION_CODE_HERE) {
//            migrateXXX()
//        }

        // set the current version
        settingsRepository.setLastInstalledVersionCodeAsync(inc.blubz.fitsync.BuildConfig.VERSION_CODE)
    }
}
