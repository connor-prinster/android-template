package inc.blubz.fitsync.model.datastore.migration

import androidx.datastore.preferences.core.Preferences
import inc.blubz.fitsync.util.datastore.PreferenceMigration

object DevicePreferenceMigration2 : PreferenceMigration(1, 2) {
    override suspend fun migrate(currentData: Preferences): Preferences {
        val mutablePreferences = currentData.toMutablePreferences()

        // do preference migrations from version 1 to 2 here

        return mutablePreferences.toPreferences()
    }
}