package `in`.jitinsharma.asg.conf

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import `in`.jitinsharma.asg.conf.repository.ConferenceRepositoryImpl
import `in`.jitinsharma.asg.conf.viewmodel.ConferenceListUiState
import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class ConferenceViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun testViewModelUiStateEmission_withSuccessState() {
        val conferenceRepository = mock(ConferenceRepository::class.java)
        coroutineTestRule.testDispatcher.runBlockingTest {
            `when`(conferenceRepository.getConferenceDataList()).thenReturn(
                flowOf(
                    listOf(
                        ConferenceData(
                            name = "Test Conference",
                            city = "Test City",
                            country = "Test Country"
                        )
                    )
                )
            )
            val conferenceViewModel =
                ConferenceViewModel(conferenceRepository, coroutineTestRule.dispatcherProvider)
            assert(conferenceViewModel.uiState.first() is ConferenceListUiState.Success)
            val uiState = conferenceViewModel.uiState.first() as ConferenceListUiState.Success
            assert(uiState.conferenceList.size == 1)
        }
    }

    @Test
    fun testViewModelUiStateEmission_withErrorState() {
        val conferenceRepository = mock(ConferenceRepository::class.java)
        coroutineTestRule.testDispatcher.runBlockingTest {
            `when`(conferenceRepository.loadConferenceData()).thenThrow(RuntimeException())
            val conferenceViewModel =
                ConferenceViewModel(conferenceRepository, coroutineTestRule.dispatcherProvider)
            assert(conferenceViewModel.uiState.first() is ConferenceListUiState.Error)
        }
    }

    @Test
    fun testViewModelUiStateEmission_withLoadingState() {
        val conferenceRepository = mock(ConferenceRepositoryImpl::class.java)
        coroutineTestRule.testDispatcher.runBlockingTest {
            `when`(conferenceRepository.getConferenceDataList()).thenReturn(flowOf(emptyList()))
            val conferenceViewModel =
                ConferenceViewModel(conferenceRepository, coroutineTestRule.dispatcherProvider)
            assert(conferenceViewModel.uiState.first() is ConferenceListUiState.Loading)
        }
    }
}
