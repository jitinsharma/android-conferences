package `in`.jitinsharma.asg.conf.redux.actions

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.model.Country
import org.rekotlin.Action

class LoadConferences : Action

class DisplayConferences(val conferenceDataList: List<ConferenceData>) : Action
class DisplayLoading : Action
class DisplayError : Action

class DisplayDialog : Action
class HideDialog: Action
class SetCFPFilterCheck(val checked: Boolean) : Action
class SetSelectedCountries(val selectedCountries: MutableList<Country>) : Action

class LoadCountries : Action
class DisplayCountries(val countryList: List<Country>) : Action

class FilterConferences(val cfpChecked: Boolean, val selectedCountries: List<Country>) : Action
