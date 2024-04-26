package inc.blubz.fitsync.model.domain

import kotlinx.datetime.Clock
import inc.blubz.fitsync.model.domain.inline.CreatedTime
import inc.blubz.fitsync.model.domain.inline.HouseholdId
import inc.blubz.fitsync.model.domain.inline.LastModifiedTime
import inc.blubz.fitsync.model.domain.inline.LastName
import java.util.UUID

data class Household(
    val id: HouseholdId = HouseholdId(UUID.randomUUID().toString()),
    val name: LastName,

    val created: CreatedTime = CreatedTime(Clock.System.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(Clock.System.now())
)