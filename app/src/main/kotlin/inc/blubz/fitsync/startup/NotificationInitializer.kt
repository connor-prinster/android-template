package inc.blubz.fitsync.startup

import android.content.Context
import androidx.startup.Initializer
import inc.blubz.fitsync.ui.notification.NotificationChannels

class NotificationInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        NotificationChannels.registerAllChannels(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}