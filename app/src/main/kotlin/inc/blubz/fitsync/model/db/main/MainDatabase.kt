package inc.blubz.fitsync.model.db.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import inc.blubz.fitsync.model.db.converter.KotlinDateTimeTextConverter
import inc.blubz.fitsync.model.db.main.exercise.WorkoutDao
import inc.blubz.fitsync.model.db.main.exercise.WorkoutEntity

@Database(
    entities = [
        WorkoutEntity::class,
    ],
    version = 3
)
@TypeConverters(KotlinDateTimeTextConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun exerciseDao(): WorkoutDao

    companion object {
        const val DATABASE_NAME = "main.db"
    }
}