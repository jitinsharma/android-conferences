package `in`.jitinsharma.asg.conf.redux.reducer

import `in`.jitinsharma.asg.conf.redux.actions.DisplaySettings
import `in`.jitinsharma.asg.conf.redux.state.SettingsPageState
import org.rekotlin.Action

fun settingsPageReducer(
    action: Action,
    settingsPageState: SettingsPageState?
): SettingsPageState? {
    return when (action) {
        is DisplaySettings -> settingsPageState.get().copy(displayPage = true)
        else -> settingsPageState
    }
}

fun SettingsPageState?.get() = this ?: SettingsPageState()
