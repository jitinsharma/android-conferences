package `in`.jitinsharma.asg.conf.redux.reducer

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.model.Country
import `in`.jitinsharma.asg.conf.redux.actions.DisplayConferences
import `in`.jitinsharma.asg.conf.redux.actions.DisplayError
import `in`.jitinsharma.asg.conf.redux.actions.DisplayLoading
import `in`.jitinsharma.asg.conf.redux.actions.FilterConferences
import `in`.jitinsharma.asg.conf.redux.state.ConferenceListState
import org.rekotlin.Action

fun conferenceListReducer(
    action: Action,
    conferenceListState: ConferenceListState?
): ConferenceListState? {
    return when (action) {
        is DisplayConferences -> {
            println("sending display conference event")
            conferenceListState.get().copy(
                isLoading = false,
                conferenceDataList = action.conferenceDataList,
                displayError = false
            )
        }
        is DisplayLoading -> {
            conferenceListState.get().copy(isLoading = true)
        }
        is DisplayError -> {
            conferenceListState.get().copy(displayError = true)
        }
        is FilterConferences -> {
            conferenceListState.get().copy(
                filteredConferenceDataList = filterList(
                    cfpChecked = action.cfpChecked,
                    selectedCountries = action.selectedCountries,
                    originalList = conferenceListState.get().conferenceDataList
                )
            )
        }
        else -> {
            conferenceListState
        }
    }
}

private fun ConferenceListState?.get(): ConferenceListState = this ?: ConferenceListState()


@OptIn(ExperimentalStdlibApi::class)
private fun filterList(
    cfpChecked: Boolean,
    selectedCountries: List<Country>,
    originalList: List<ConferenceData>
): List<ConferenceData> {
    var confList = buildList { addAll(originalList) }
    if (cfpChecked) {
        confList = confList.filter { conferenceData ->
            conferenceData.cfpData != null && conferenceData.cfpData!!.isCfpActive
        }
    }
    if (selectedCountries.isNotEmpty()) {
        confList = confList.filter { conferenceData ->
            selectedCountries.contains(Country(conferenceData.country))
        }
    }
    return confList
}
