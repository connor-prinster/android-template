package inc.blubz.fitsync.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.touchlab.kermit.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import inc.blubz.fitsync.model.config.RemoteConfig

@HiltWorker
class RemoteConfigSyncWorker
@AssistedInject constructor(
    private val remoteConfig: RemoteConfig,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Logger.i { "RemoteConfigSyncWorker: fetching and activating" }
        remoteConfig.fetchAndActivateNow()
        return Result.success()
    }

    companion object {
        const val UNIQUE_ONE_TIME_WORK_NAME = "OneTimeRemoteConfigSyncWorker"
        const val UNIQUE_PERIODIC_WORK_NAME = "PeriodicRemoteConfigSyncWorker"
    }
}
