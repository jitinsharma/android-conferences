package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.viewmodel.ConferenceListUiState
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable

@Composable
fun ConferencePage(
    conferenceListUiState: ConferenceListUiState,
    onRetryClick: () -> Unit
) {
    Crossfade(current = conferenceListUiState) { uiState ->
        when (uiState) {
            is ConferenceListUiState.Success -> {
                ConferenceCardList(conferenceDataList = uiState.conferenceList)
            }
            is ConferenceListUiState.Loading -> {
                LoadingView()
            }
            is ConferenceListUiState.Error -> {
                WtfView(onRetryClick = onRetryClick)
            }
        }
    }
}
