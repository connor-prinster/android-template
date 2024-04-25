package inc.blubz.fitsync.model.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.blubz.fitsync.model.domain.inline.CreatedTime
import org.blubz.fitsync.model.domain.inline.Email
import org.blubz.fitsync.model.domain.inline.FirstName
import org.blubz.fitsync.model.domain.inline.HouseholdId
import org.blubz.fitsync.model.domain.inline.IndividualId
import org.blubz.fitsync.model.domain.inline.LastModifiedTime
import org.blubz.fitsync.model.domain.inline.LastName
import org.blubz.fitsync.model.domain.inline.Phone
import org.blubz.fitsync.model.domain.type.IndividualType
import java.util.UUID

data class Individual(
    val id: IndividualId = IndividualId(UUID.randomUUID().toString()),
    val householdId: HouseholdId? = null,

    val individualType: IndividualType = IndividualType.HEAD,
    val firstName: FirstName? = null,
    val lastName: LastName? = null,
    val birthDate: LocalDate? = null,
    val alarmTime: LocalTime? = null,
    val phone: Phone? = null,
    val email: Email? = null,
    val available: Boolean = false,

    val created: CreatedTime = CreatedTime(Clock.System.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(Clock.System.now())
) {
    fun getFullName(): String = "${firstName?.value.orEmpty()} ${lastName?.value.orEmpty()}"
}
