package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class ConferenceViewModel : ViewModel() {

    //TODO Move this to constructor and create ViewModelFactory
    private val conferenceRepository: ConferenceRepository = ConferenceRepository()

    fun getConferenceList() = liveData(Dispatchers.IO) {
        emit(LoadingState)
        val conferenceDataList = conferenceRepository.getConferenceData()
        //TODO Filter out past conferences?
        emit(SuccessState(conferenceDataList))
    }
}

sealed class ConferenceListState
object LoadingState : ConferenceListState()
class SuccessState(val conferenceDataList: List<ConferenceData>) : ConferenceListState()
//TODO Add error state
