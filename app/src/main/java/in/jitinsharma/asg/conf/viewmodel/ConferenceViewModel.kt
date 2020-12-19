package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConferenceViewModel(
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<ConferenceListUiState> =
        MutableStateFlow(ConferenceListUiState.Loading)
    val uiState: StateFlow<ConferenceListUiState> = _uiState

    fun loadConferences() {
        viewModelScope.launch(Dispatchers.IO) {
            conferenceRepository.loadConferenceData()
            val conferenceDataList = conferenceRepository.getConferenceDataList()
            if (conferenceDataList.isNotEmpty()) {
                _uiState.value = ConferenceListUiState.Success(conferenceDataList)
            } else {
                _uiState.value = ConferenceListUiState.Error
            }
        }
    }
}

sealed class ConferenceListUiState {
    class Success(val conferenceList: List<ConferenceData>) : ConferenceListUiState()
    object Loading : ConferenceListUiState()
    object Error : ConferenceListUiState()
}