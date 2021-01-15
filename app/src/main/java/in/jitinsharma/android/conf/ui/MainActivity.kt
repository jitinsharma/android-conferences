package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.viewmodel.ConferenceViewModel
import `in`.jitinsharma.android.conf.viewmodel.FilterScreenViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val conferenceViewModel by viewModel<ConferenceViewModel>()
    private val filterScreenViewModel by viewModel<FilterScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = themeColors) {
                ConferenceApp(
                    conferenceViewModel = conferenceViewModel,
                    filterScreenViewModel = filterScreenViewModel
                )
            }
        }
    }
}
