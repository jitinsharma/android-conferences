package `in`.jitinsharma.asg.conf.redux.reducer

import `in`.jitinsharma.asg.conf.redux.actions.*
import `in`.jitinsharma.asg.conf.redux.state.FilterState
import org.rekotlin.Action
import org.rekotlin.StateType

fun filterScreenReducer(
    action: Action,
    filterState: FilterState?
): FilterState? {
    return when (action) {
        is DisplayDialog -> {
            filterState.get().copy(displayDialog = true)
        }
        is HideDialog -> {
            filterState.get().copy(displayDialog = false)
        }
        is SetCFPFilterCheck -> {
            filterState.get().copy(cfpFilterChecked = action.checked)
        }
        is SetSelectedCountries -> {
            filterState.get().copy(selectedCountries = action.selectedCountries)
        }
        is DisplayCountries -> {
            filterState.get().copy(displayDialog = true, countryList = action.countryList)
        }
        else -> {
            filterState
        }
    }
}

fun FilterState?.get(): FilterState = this ?: FilterState()
