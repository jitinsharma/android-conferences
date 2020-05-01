package `in`.jitinsharma.asg.conf.viewmodel

import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConferenceViewModelFactory(
    private val conferenceRepository: ConferenceRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ConferenceViewModel(conferenceRepository) as T
    }
}
