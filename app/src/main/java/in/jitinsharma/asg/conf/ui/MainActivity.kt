package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import `in`.jitinsharma.asg.conf.viewmodel.LoadingState
import `in`.jitinsharma.asg.conf.viewmodel.SuccessState
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.graphics.Color
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
        setContent {
            val conferenceDataListState = conferenceViewModel.getConferenceList().observeAsState()
            // TODO Create custom theme
            MaterialTheme {
                Surface(color = Color(0xFF092432)) {
                    Column(
                        modifier = Modifier.fillMaxWidth() + Modifier.padding(16.dp)
                    ) {
                        Header(modifier = Modifier.padding(bottom = 16.dp))
                        when (val state = conferenceDataListState.value) {
                            is LoadingState -> {
                                LoadingView()
                            }
                            is SuccessState -> {
                                ConferenceCardList(
                                    conferenceDataList = state.conferenceDataList,
                                    onTitleClicked = { url -> loadUrl(url = url) },
                                    onCfpClicked = { url -> loadUrl(url = url) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }
}
