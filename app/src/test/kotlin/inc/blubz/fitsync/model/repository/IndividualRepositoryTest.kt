package inc.blubz.fitsync.model.repository

import android.app.Application
import androidx.room.Room
import assertk.assertThat
import assertk.assertions.isEqualTo
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalTime
import org.dbtools.android.room.jdbc.JdbcSQLiteOpenHelperFactory
import inc.blubz.fitsync.TestFilesystem
import inc.blubz.fitsync.inject.CommonTestModule
import inc.blubz.fitsync.inject.CoroutinesModule
import inc.blubz.fitsync.model.datastore.UserPreferenceDataSource
import inc.blubz.fitsync.model.db.main.MainDatabase
import inc.blubz.fitsync.model.db.main.MainDatabaseWrapper
import inc.blubz.fitsync.model.domain.Individual
import inc.blubz.fitsync.model.domain.inline.FirstName
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.test.BeforeTest
import kotlin.test.Test

class IndividualRepositoryTest {

    @Inject
    lateinit var individualRepository: IndividualRepository

    @BeforeTest
    fun setUp() {
        TestFilesystem.deleteFilesystem()

        val component = DaggerIndividualRepositoryTestComponent.builder().individualRepositoryTestModule(IndividualRepositoryTestModule()).build()
        component.inject(this)
    }

    @Test
    fun testIndividual() = runTest {
        // === CREATE / INSERT ===
        val individual = Individual(
            firstName = FirstName("Jeff"),
            alarmTime = LocalTime(10, 30)
        )
        individualRepository.saveIndividual(individual)

        assertThat(individualRepository.getIndividualCount()).isEqualTo(1)

        // === UPDATE ===
        individualRepository.saveIndividual(individual.copy(firstName = FirstName("Jeffery")))

        val dbFirstName = individualRepository.getIndividualFirstName(individual.id)
        assertThat(dbFirstName).isEqualTo("Jeffery")

        // === DELETE ===
        individualRepository.deleteIndividual(individual.id)
        assertThat(individualRepository.getIndividualCount()).isEqualTo(0)
    }
}

@Module
@DisableInstallInCheck // prevent Hilt from checking for @InstallIn (https://issuetracker.google.com/issues/158758786)
class IndividualRepositoryTestModule {
    @Provides
    @Singleton
    fun provideUserPreferenceDataSource(): UserPreferenceDataSource {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideMainDatabaseWrapper(application: Application): MainDatabaseWrapper {
        return MainDatabaseTestWrapper(application)
    }
}

@Singleton
@Component(modules = [CommonTestModule::class, IndividualRepositoryTestModule::class, CoroutinesModule::class])
interface IndividualRepositoryTestComponent {
    fun inject(test: IndividualRepositoryTest)
}

class MainDatabaseTestWrapper(
    application: Application
) : MainDatabaseWrapper(application) {

    override fun createDatabase(): MainDatabase {
        return Room.databaseBuilder(mockk<Application>(), MainDatabase::class.java, MainDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .openHelperFactory(JdbcSQLiteOpenHelperFactory(TestFilesystem.INTERNAL_DATABASES_DIR_PATH))
            .build()
    }
}
