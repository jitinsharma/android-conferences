package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.redux.state.ConferenceListState
import `in`.jitinsharma.asg.conf.redux.state.FilterState
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable

@Composable
fun ConferencePage(
    conferenceListState: ConferenceListState?,
    filterState: FilterState?
) {
    Crossfade(current = conferenceListState) { state ->
        state?.run {
            println(state)
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
                    filterState?.let { filterState ->
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
