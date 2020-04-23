package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import `in`.jitinsharma.asg.conf.viewmodel.ErrorState
import `in`.jitinsharma.asg.conf.viewmodel.LoadingState
import `in`.jitinsharma.asg.conf.viewmodel.SuccessState
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val conferenceViewModel: ConferenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conferenceViewModel.loadConferenceList()
        setContent {
            val conferenceDataListState =
                conferenceViewModel.conferenceListLiveData.observeAsState()
            MaterialTheme(colors = themeColors) {
                Surface(color = MaterialTheme.colors.primary) {
                    Column(modifier = Modifier.fillMaxWidth() + Modifier.padding(16.dp)) {
                        Header(modifier = Modifier.padding(bottom = 16.dp))
                        Crossfade(current = conferenceDataListState.value) { state ->
                            when (state) {
                                is LoadingState -> {
                                    LoadingView()
                                }
                                is SuccessState -> {
                                    ConferenceCardList(conferenceDataList = state.conferenceDataList)
                                }
                                is ErrorState -> {
                                    WtfView(
                                        onRetryClick = { conferenceViewModel.loadConferenceList() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

