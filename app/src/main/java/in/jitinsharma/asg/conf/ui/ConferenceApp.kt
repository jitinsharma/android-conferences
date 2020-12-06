package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.redux.actions.DisplayLoading
import `in`.jitinsharma.asg.conf.redux.observeAsState
import `in`.jitinsharma.asg.conf.redux.state.AppState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.rekotlin.Store

@Composable
fun ConferenceApp(
    store: Store<AppState>
) {
    store.dispatch(DisplayLoading())
    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.primary)
    ) {
        val appState = store.observeAsState()
        Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {
            Header(modifier = Modifier.padding(bottom = 16.dp))
            appState.value?.run {
                if (conferenceListState != null) {
                    ConferencePage(
                        conferenceListState = conferenceListState,
                        filterState = filterState
                    )
                }
                if (settingsPageState != null && settingsPageState.displayPage) {
                    SettingsPage()
                }
            }
        }
    }
}
