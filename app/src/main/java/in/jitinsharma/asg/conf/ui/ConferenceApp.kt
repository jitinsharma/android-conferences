package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ConferenceApp(
    conferenceViewModel: ConferenceViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.primary)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {
            Header(
                modifier = Modifier.padding(bottom = 16.dp),
                onFilterClicked = {},
                onSettingsClicked = {}
            )
            val conferenceUiState = conferenceViewModel.uiState.collectAsState()
            ConferencePage(
                conferenceListUiState = conferenceUiState.value,
                onRetryClick = {
                    conferenceViewModel.loadConferences()
                }
            )
        }
    }
}
