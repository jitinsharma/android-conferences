package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.redux.actions.DisplayLoading
import `in`.jitinsharma.asg.conf.redux.actions.LoadConferences
import `in`.jitinsharma.asg.conf.redux.observeAsState
import `in`.jitinsharma.asg.conf.redux.store
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = themeColors) {
                Box(backgroundColor = MaterialTheme.colors.primary) {
                    store.dispatch(DisplayLoading())
                    store.dispatch(LoadConferences())
                    val appState = store.observeAsState()
                    Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {
                        Header(modifier = Modifier.padding(bottom = 16.dp))
                        Crossfade(current = appState.value?.conferenceListState) { state ->
                            state?.run {
                                when {
                                    isLoading -> {
                                        LoadingView()
                                    }
                                    conferenceDataList.isNotEmpty() -> {
                                        val list = if (filteredConferenceDataList.isNotEmpty()) {
                                            filteredConferenceDataList
                                        } else {
                                            conferenceDataList
                                        }
                                        ConferenceCardList(conferenceDataList = list)
                                        appState.value?.filterState?.let { filterState ->
                                            FilterDialog(
                                                filterState = filterState
                                            )
                                        }
                                    }
                                    displayError -> {
                                        WtfView()
                                    }
                                    else -> {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
