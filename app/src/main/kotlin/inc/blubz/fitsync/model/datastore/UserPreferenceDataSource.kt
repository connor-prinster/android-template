package inc.blubz.fitsync.model.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import co.touchlab.kermit.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceDataSource
@Inject constructor(
    private val application: Application
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user",
        corruptionHandler = ReplaceFileCorruptionHandler {
            Logger.e(it) { "UserPreferenceDataSource Corrupted... recreating..." }
            emptyPreferences()
        }
    )

    val directorySortByLastNamePref: DatastorePrefItem<Boolean> = DatastorePrefItem.create(application.dataStore, Keys.DIRECTORY_SORT, true)

    suspend fun clearAll() {
        application.dataStore.edit { it.clear() }
    }

    private object Keys {
        val DIRECTORY_SORT = booleanPreferencesKey("directorySort")
    }
}