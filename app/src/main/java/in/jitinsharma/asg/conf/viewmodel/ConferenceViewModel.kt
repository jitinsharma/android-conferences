package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.model.Country
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ConferenceViewModel(
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<ConferenceListUiState> =
        MutableStateFlow(ConferenceListUiState.Loading)
    val uiState: StateFlow<ConferenceListUiState> = _uiState
    private var originalConferenceList = listOf<ConferenceData>()

    init {
        loadConferences()
    }

    fun loadConferences() {
        viewModelScope.launch(Dispatchers.IO) {
            conferenceRepository.getConferenceDataList()
                .catch {
                    _uiState.value = ConferenceListUiState.Error
                }
                .collect { conferenceDataList ->

                    if (conferenceDataList.isNotEmpty()) {
                        _uiState.value = ConferenceListUiState.Success(conferenceDataList)
                        originalConferenceList = conferenceDataList
                    }

                    try {
                        conferenceRepository.loadConferenceData()
                    } catch (e: Exception) {
                        if (_uiState.value == ConferenceListUiState.Loading) {
                            _uiState.value = ConferenceListUiState.Error
                        }
                    }
                }
        }
    }

    fun filterConferences(cfpFilterChecked: Boolean, selectedCountries: List<Country>) {
        if (!cfpFilterChecked && selectedCountries.isEmpty()) {
            _uiState.value = ConferenceListUiState.Success(originalConferenceList)
        } else {
            val filteredConferences = originalConferenceList.filter {
                if (cfpFilterChecked) {
                    it.cfpData != null && it.cfpData!!.isCfpActive
                } else {
                    true
                }
            }.filter {
                selectedCountries.contains(Country(it.country))
            }
            _uiState.value = ConferenceListUiState.Success(filteredConferences)
        }
    }
}

sealed class ConferenceListUiState {
    class Success(val conferenceList: List<ConferenceData>) : ConferenceListUiState()
    object Loading : ConferenceListUiState()
    object Error : ConferenceListUiState()
}