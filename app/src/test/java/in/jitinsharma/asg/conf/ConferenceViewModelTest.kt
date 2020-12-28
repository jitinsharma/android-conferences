package `in`.jitinsharma.asg.conf

import `in`.jitinsharma.asg.conf.viewmodel.ConferenceListUiState
import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ConferenceViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val fakeConferenceRepository = FakeConferenceRepository()
    private val conferenceViewModel =
        ConferenceViewModel(fakeConferenceRepository, coroutineTestRule.dispatcherProvider)

    @Test
    fun testViewModelUiStateEmission_withSuccessState() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            assert(conferenceViewModel.uiState.first() is ConferenceListUiState.Success)
            val uiState = conferenceViewModel.uiState.first() as ConferenceListUiState.Success
            assert(uiState.conferenceList.size == 1)
        }
    }
}
