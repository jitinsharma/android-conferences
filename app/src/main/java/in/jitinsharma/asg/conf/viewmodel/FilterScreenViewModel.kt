package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.model.Country
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import androidx.collection.ArraySet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilterScreenViewModel(
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private var _uiState =
        MutableStateFlow(FilterScreenUiState.Success())
    val uiState: StateFlow<FilterScreenUiState> = _uiState

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            conferenceRepository.getConferenceDataList().collect { conferenceDataList ->
                val countries = conferenceDataList.mapTo(ArraySet()) { countryName ->
                    Country(name = countryName.country)
                }.toList()
                _uiState.value = FilterScreenUiState.Success(countryList = countries)
            }
        }
    }

    fun updateUiState(cfpFilterChecked: Boolean, selectedCountries: List<Country>) {
        val countries = _uiState.value.countryList
        _uiState.value = FilterScreenUiState.Success(cfpFilterChecked, selectedCountries, countries)
    }
}

sealed class FilterScreenUiState {
    class Success(
        val cfpFilterChecked: Boolean = false,
        val selectedCountries: List<Country> = emptyList(),
        val countryList: List<Country> = emptyList()
    ) : FilterScreenUiState()
}