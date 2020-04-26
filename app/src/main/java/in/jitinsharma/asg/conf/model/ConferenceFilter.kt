package `in`.jitinsharma.asg.conf.model

sealed class ConferenceFilter
object CfpOpenFilter : ConferenceFilter()
class CountryFilter(val countryList: List<String>) : ConferenceFilter()