package `in`.jitinsharma.asg.conf.redux.state

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.model.Country
import org.rekotlin.StateType

data class AppState(
    val conferenceListState: ConferenceListState? = null,
    val filterState: FilterState? = null,
    val settingsPageState: SettingsPageState? = null
) : StateType

data class ConferenceListState(
    val isLoading: Boolean = true,
    val conferenceDataList: List<ConferenceData> = listOf(),
    val filteredConferenceDataList: List<ConferenceData> = listOf(),
    val displayError: Boolean = false
) : StateType

data class FilterState(
    val displayDialog: Boolean = false,
    val countryList: List<Country>? = null,
    val cfpFilterChecked: Boolean = false,
    val selectedCountries: MutableList<Country> = mutableListOf()
) : StateType

data class SettingsPageState(
    val displayPage: Boolean = false
)

