package inc.blubz.fitsync.domain.individual

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import inc.blubz.fitsync.inject.DefaultDispatcher
import inc.blubz.fitsync.model.domain.Household
import inc.blubz.fitsync.model.domain.Individual
import inc.blubz.fitsync.model.domain.inline.FirstName
import inc.blubz.fitsync.model.domain.inline.LastName
import inc.blubz.fitsync.model.domain.inline.Phone
import inc.blubz.fitsync.model.domain.type.IndividualType
import inc.blubz.fitsync.model.repository.IndividualRepository
import javax.inject.Inject

class CreateIndividualTestDataUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(defaultDispatcher) {
        // clear any existing items
        individualRepository.deleteAllIndividuals()

        val household1 = Household(
            name = LastName("Campbell")
        )

        val individual1 = Individual(
            householdId = household1.id,
            firstName = FirstName("Jeff"),
            lastName = LastName("Campbell"),
            phone = Phone("801-555-0000"),
            individualType = IndividualType.HEAD,
            birthDate = LocalDate(1970, 1, 1),
            alarmTime = LocalTime(7, 0),
        )

        val individual1a = Individual(
            householdId = household1.id,
            firstName = FirstName("Ty"),
            lastName = LastName("Campbell"),
            phone = Phone("801-555-0001"),
            individualType = IndividualType.CHILD,
            birthDate = LocalDate(1970, 1, 1),
            alarmTime = LocalTime(7, 0),
        )

        val household2 = Household(
            name = LastName("Miller")
        )

        val individual2 = Individual(
            householdId = household2.id,
            firstName = FirstName("John"),
            lastName = LastName("Miller"),
            phone = Phone("303-555-1111"),
            individualType = IndividualType.HEAD,
            birthDate = LocalDate(1970, 1, 2),
            alarmTime = LocalTime(6, 0),
        )

        individualRepository.saveNewHousehold(household1, listOf(individual1, individual1a))
        individualRepository.saveNewHousehold(household2, listOf(individual2))
    }
}
