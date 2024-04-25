package inc.blubz.fitsync.model.db.main.individual

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.blubz.fitsync.model.domain.inline.Email
import org.blubz.fitsync.model.domain.inline.FirstName
import org.blubz.fitsync.model.domain.inline.HouseholdId
import org.blubz.fitsync.model.domain.inline.IndividualId
import org.blubz.fitsync.model.domain.inline.LastName
import org.blubz.fitsync.model.domain.inline.Phone
import org.blubz.fitsync.model.domain.type.IndividualType

@Entity("Individual")
data class IndividualEntity(
    @PrimaryKey
    val id: IndividualId,
    val householdId: HouseholdId?,
    val individualType: IndividualType,
    val firstName: FirstName?,
    val lastName: LastName?,
    val birthDate: LocalDate?,
    val alarmTime: LocalTime?,
    val phone: Phone?,
    val email: Email?,
    val available: Boolean,

    val created: Instant,
    val lastModified: Instant
)
