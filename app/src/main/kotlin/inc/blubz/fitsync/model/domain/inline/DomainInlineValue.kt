package inc.blubz.fitsync.model.domain.inline

import inc.blubz.fitsync.model.domain.type.Feeling
import kotlinx.datetime.Instant
import java.util.UUID

@JvmInline
value class ExerciseId(val value: String = UUID.randomUUID().toString()) {
    init {
        require(value.isNotBlank()) { "ExerciseId cannot have a empty value" }
    }
}

@JvmInline
value class ExerciseName(val value: String)

@JvmInline
value class LastName(val value: String)

@JvmInline
value class Sets(val value: Int)

@JvmInline
value class Reps(val value: Int)

@JvmInline
value class Feeling(val value: Feeling)

@JvmInline
value class CreatedTime(val value: Instant)

@JvmInline
value class LastModifiedTime(val value: Instant)
