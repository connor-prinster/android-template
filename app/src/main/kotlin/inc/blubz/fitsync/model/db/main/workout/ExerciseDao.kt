package inc.blubz.fitsync.model.db.main.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import inc.blubz.fitsync.model.domain.inline.ExerciseId
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseEntity: WorkoutEntity): Long

    @Query("DELETE FROM Exercise")
    suspend fun deleteAll()

    @Query("SELECT count(1) FROM Exercise")
    suspend fun findCount(): Int

    @Query("SELECT * FROM Exercise WHERE id = :id")
    suspend fun findById(id: ExerciseId): WorkoutEntity?

    @Query("SELECT * FROM Exercise WHERE id = :id")
    fun findByIdFlow(id: ExerciseId): Flow<WorkoutEntity?>

    @Query("SELECT * FROM Exercise")
    suspend fun findAll(): List<WorkoutEntity>

    @Query("SELECT * FROM Exercise")
    fun findAllFlow(): Flow<List<WorkoutEntity>>

    @Query("DELETE FROM Exercise WHERE id = :id")
    suspend fun deleteById(id: ExerciseId)

    @Query("SELECT lastModified FROM Exercise WHERE id = :id")
    suspend fun findLastModified(id: ExerciseId): Instant?
}
