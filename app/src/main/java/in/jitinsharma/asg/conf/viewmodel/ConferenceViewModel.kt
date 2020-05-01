package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.model.*
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import androidx.collection.ArraySet
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConferenceViewModel(
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    var conferenceListLiveData: MutableLiveData<ConferenceListState> = MutableLiveData()
    var countryListLiveData: MutableLiveData<List<Country>> = MutableLiveData()

    private var originalList = mutableListOf<ConferenceData>()

    fun loadConferenceList() {
        viewModelScope.launch {
            conferenceListLiveData.postValue(LoadingState)
            var conferenceDataList: List<ConferenceData> = emptyList()
            withContext(Dispatchers.IO) {
                conferenceRepository.loadConferenceData()
                conferenceDataList = conferenceRepository.getConferenceDataList()
            }
            if (conferenceDataList.isNotEmpty()) {
                originalList.addAll(conferenceDataList)
                conferenceListLiveData.postValue(SuccessState(conferenceDataList))
            } else {
                conferenceListLiveData.postValue(ErrorState)
            }
        }
    }

    @ExperimentalStdlibApi
    fun filterList(
        filters: Set<ConferenceFilter>
    ) {
        if (filters.isEmpty()) {
            conferenceListLiveData.postValue(SuccessState(originalList))
            return
        }
        var confList = buildList { addAll(originalList) }
        filters.forEach { filter ->
            when (filter) {
                is CfpOpenFilter -> {
                    confList = confList.filter { conferenceData ->
                        conferenceData.cfpData != null && conferenceData.cfpData!!.isCfpActive
                    }
                }
                is CountryFilter -> {
                }
            }
        }
        conferenceListLiveData.postValue(SuccessState(confList))
    }

    fun loadCountryList() {
        viewModelScope.launch(Dispatchers.IO) {
            val conferenceDataList = conferenceRepository.getConferenceDataList()
            val countries = conferenceDataList.mapTo(ArraySet()) { countryName ->
                Country(name = countryName.country)
            }.toList()
            withContext(Dispatchers.Main) {
                countryListLiveData.postValue(countries)
            }
        }
    }
}

sealed class ConferenceListState
object LoadingState : ConferenceListState()
class SuccessState(val conferenceDataList: List<ConferenceData>) : ConferenceListState()
object ErrorState : ConferenceListState()