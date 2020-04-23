package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.model.ConferenceData
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

    var conferenceListLiveData : MutableLiveData<ConferenceListState> = MutableLiveData()

    fun loadConferenceList() {
        viewModelScope.launch {
            conferenceListLiveData.postValue(LoadingState)
            var conferenceDataList: List<ConferenceData> = emptyList()
            withContext(Dispatchers.IO) {
                //TODO Filter out past conferences?
                conferenceDataList = conferenceRepository.getConferenceData()
            }
            if (conferenceDataList.isNotEmpty()) {
                conferenceListLiveData.postValue(SuccessState(conferenceDataList))
            } else {
                conferenceListLiveData.postValue(ErrorState)
            }
        }
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
