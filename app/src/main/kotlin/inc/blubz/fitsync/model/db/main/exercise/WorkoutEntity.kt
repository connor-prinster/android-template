package inc.blubz.fitsync.model.db.main.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey
import inc.blubz.fitsync.model.domain.inline.ExerciseId
import inc.blubz.fitsync.model.domain.inline.ExerciseName
import inc.blubz.fitsync.model.domain.inline.Reps
import inc.blubz.fitsync.model.domain.inline.Sets
import kotlinx.datetime.Instant

@Entity("Exercise")
data class WorkoutEntity(
    @PrimaryKey
    val id: ExerciseId,
    val name: ExerciseName,

    val created: Instant,
    val lastModified: Instant
)
