package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.model.CfpOpenFilter
import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.model.ConferenceFilter
import `in`.jitinsharma.asg.conf.model.CountryFilter
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConferenceViewModel : ViewModel() {

    //TODO Move this to constructor and create ViewModelFactory
    private val conferenceRepository: ConferenceRepository = ConferenceRepository()

    var conferenceListLiveData: MutableLiveData<ConferenceListState> = MutableLiveData()

    private var originalList = mutableListOf<ConferenceData>()

    fun loadConferenceList() {
        viewModelScope.launch {
            conferenceListLiveData.postValue(LoadingState)
            var conferenceDataList: List<ConferenceData> = emptyList()
            withContext(Dispatchers.IO) {
                conferenceDataList = conferenceRepository.getConferenceData()
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
                is CountryFilter -> {}
            }
        }
        conferenceListLiveData.postValue(SuccessState(confList))
    }

    // Unable to do retry by using this extension since there is no explicit postValue to liveData
    // instance which would cause state update.
    /*fun getConferenceList() = liveData(Dispatchers.IO) {
        emit(LoadingState)
        try {
            val conferenceDataList = conferenceRepository.getConferenceData()
            emit(SuccessState(conferenceDataList))
        } catch (e: Exception) {
            emit(ErrorState)
        }
    }*/
}

sealed class ConferenceListState
object LoadingState : ConferenceListState()
class SuccessState(val conferenceDataList: List<ConferenceData>) : ConferenceListState()
object ErrorState : ConferenceListState()
