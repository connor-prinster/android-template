package inc.blubz.fitsync.ux

import androidx.activity.viewModels
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import inc.blubz.fitsync.ux.individual.IndividualEditScreenFields
import inc.blubz.fitsync.ux.main.MainActivity
import inc.blubz.fitsync.ux.main.MainViewModel
import kotlinx.coroutines.runBlocking
import org.jdc.template.R
import org.junit.Rule
import org.junit.Test

/**
 * ./gradlew connectedAndroidTest
 */
class SmokeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun stringResource(resId: Int, vararg formatArgs: Any?): String = composeTestRule.activity.getString(resId, *formatArgs)

    @Test
    fun addIndividual() {
        // make sure we have sample data
        val viewModel: MainViewModel by composeTestRule.activity.viewModels()
        runBlocking { viewModel.createSampleData() }

        // click the "Add" FAB
        composeTestRule.onNodeWithContentDescription(stringResource(R.string.add)).performClick()

        // From Individual Edit, add the user data (except first name...)
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.LAST_NAME.name).performTextReplacement("Anderson")
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.EMAIL.name).performTextReplacement("bob@bob.com")
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.PHONE.name).performTextReplacement("555-111-2222")

        // Save edit (with missing first name)
        composeTestRule.onNodeWithText(stringResource(R.string.save)).performClick()

        // Verify error message
        composeTestRule.onNodeWithText(stringResource(R.string.required)).assertIsDisplayed()

        // Add the missing field
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.FIRST_NAME.name).performTextReplacement("Bob")

        // Save edit (with everything )
        composeTestRule.onNodeWithText(stringResource(R.string.save)).performClick()

        // Validate new individual was created and is visible (from DirectoryScreen)... then click on it
        composeTestRule.waitForIdle() // wait for render to complete
        composeTestRule.onNodeWithText("Bob Anderson").assertIsDisplayed()

        // then click on new individual
        composeTestRule.onNodeWithText("Bob Anderson").performClick()

        // Make sure all elements are visible in IndividualView
        composeTestRule.onNodeWithText("Bob Anderson").assertIsDisplayed()
        composeTestRule.onNodeWithText("bob@bob.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("555-111-2222").assertIsDisplayed()
    }

    @Test
    fun editIndividual() {
        // make sure we have sample data
        val viewModel: MainViewModel by composeTestRule.activity.viewModels()
        runBlocking { viewModel.createSampleData() }

        // Click on Individual from Directory
        composeTestRule.onNodeWithText("Jeff Campbell").performClick()

        // Make sure all elements are visible in IndividualView
        composeTestRule.waitForIdle() // wait for render to complete
        composeTestRule.onNodeWithText("Jeff Campbell").assertIsDisplayed()
        composeTestRule.onNodeWithText("801-555-0000").assertIsDisplayed()
        composeTestRule.onNodeWithText("January 1, 1970").assertIsDisplayed()
//        composeTestRule.onNodeWithText("7:00 AM").assertIsDisplayed()

        // Edit
        composeTestRule.onNodeWithContentDescription(stringResource(R.string.edit)).performClick()

        // Change name
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.FIRST_NAME.name).performTextReplacement("Jeff1")

        // Save edit
        composeTestRule.onNodeWithText(stringResource(R.string.save)).performClick()

        // Validate edit (from IndividualScreen)
        composeTestRule.waitForIdle() // wait for render to complete
        composeTestRule.onNodeWithText("Jeff1 Campbell").assertIsDisplayed()
        composeTestRule.onNodeWithText("801-555-0000").assertIsDisplayed()
        composeTestRule.onNodeWithText("January 1, 1970").assertIsDisplayed()
//        composeTestRule.onNodeWithText("7:00 AM").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(stringResource(R.string.back)).performClick()

        // Validate edit (from DirectoryScreen)
        composeTestRule.onNodeWithText("Jeff1 Campbell").assertIsDisplayed()
    }

    @Test
    fun deleteIndividual() {
        // make sure we have sample data
        val viewModel: MainViewModel by composeTestRule.activity.viewModels()
        runBlocking { viewModel.createSampleData() }

        composeTestRule.waitForIdle() // wait for render to complete

        // Click on Individual from Directory
        composeTestRule.onNodeWithText("John Miller").performClick()

        // Delete
        composeTestRule.onNodeWithContentDescription(stringResource(R.string.delete)).performClick()

        // Confirm delete from Dialog
        composeTestRule.onNodeWithText(stringResource(android.R.string.ok)).performClick()

        // Validate delete (from DirectoryScreen)
        composeTestRule.waitForIdle() // wait for render to complete
        composeTestRule.onNodeWithText("John Miller").assertDoesNotExist()
    }
}
