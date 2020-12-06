package `in`.jitinsharma.asg.conf.redux.reducer

import `in`.jitinsharma.asg.conf.redux.state.AppState
import org.rekotlin.Action

fun appReducer(action: Action, appState: AppState?): AppState =
    AppState(
        conferenceListState = conferenceListReducer(action, appState?.conferenceListState),
        filterState = filterScreenReducer(action, appState?.filterState),
        settingsPageState = settingsPageReducer(action, appState?.settingsPageState)
    )
