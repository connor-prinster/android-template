package inc.blubz.fitsync.model.db.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.dbtools.android.room.DatabaseViewQuery
import org.blubz.fitsync.model.db.converter.KotlinDateTimeTextConverter
import org.blubz.fitsync.model.db.main.directoryitem.DirectoryItemDao
import org.blubz.fitsync.model.db.main.directoryitem.DirectoryItemEntityView
import org.blubz.fitsync.model.db.main.household.HouseholdDao
import org.blubz.fitsync.model.db.main.household.HouseholdEntity
import org.blubz.fitsync.model.db.main.individual.IndividualDao
import org.blubz.fitsync.model.db.main.individual.IndividualEntity

@Database(
    entities = [
        IndividualEntity::class,
        HouseholdEntity::class
    ],
    views = [
        DirectoryItemEntityView::class
    ],
    version = 3
)
@TypeConverters(KotlinDateTimeTextConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun individualDao(): IndividualDao
    abstract fun householdDao(): HouseholdDao
    abstract fun directoryItemDao(): DirectoryItemDao

    companion object {
        const val DATABASE_NAME = "main.db"
        val DATABASE_VIEW_QUERIES = listOf(
            DatabaseViewQuery(DirectoryItemEntityView.VIEW_NAME, DirectoryItemEntityView.VIEW_QUERY)
        )
    }
}