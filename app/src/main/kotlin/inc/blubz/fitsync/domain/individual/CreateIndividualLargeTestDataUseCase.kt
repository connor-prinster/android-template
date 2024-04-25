package inc.blubz.fitsync.domain.individual

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.blubz.fitsync.inject.DefaultDispatcher
import org.blubz.fitsync.model.domain.Individual
import org.blubz.fitsync.model.domain.inline.FirstName
import org.blubz.fitsync.model.domain.inline.LastName
import org.blubz.fitsync.model.domain.inline.Phone
import org.blubz.fitsync.model.domain.type.IndividualType
import org.blubz.fitsync.model.repository.IndividualRepository
import javax.inject.Inject

class CreateIndividualLargeTestDataUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(defaultDispatcher) {
        // clear any existing items
        individualRepository.deleteAllIndividuals()

        val individualList = mutableListOf<Individual>()
        for (i in 1..25) {
            individualList.add(Individual(
                firstName = FirstName("Person"),
                lastName = LastName("$i"),
                phone = Phone("801-555-00$i"),
                individualType = IndividualType.HEAD,
                birthDate = LocalDate(1970, 1, 1),
                alarmTime = LocalTime(7, 0),
            ))
        }

        individualRepository.saveIndividuals(individualList)
    }
}
