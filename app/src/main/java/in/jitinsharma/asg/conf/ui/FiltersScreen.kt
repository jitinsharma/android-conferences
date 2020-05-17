package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.model.Country
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.Card
import androidx.ui.material.Checkbox
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

val appliedFilter = AppliedFilter()

data class AppliedFilter(
    var cfpFilterSelected: Boolean = false,
    var selectedCountries: MutableList<Country> = mutableListOf()
)

@Model
data class FiltersScreenState(
    var shouldDisplay: Boolean = false,
    var cfpFilterChecked: Boolean = appliedFilter.cfpFilterSelected
)

@Composable
fun FilterDialog(
    filtersScreenState: FiltersScreenState = FiltersScreenState(),
    onApplyClick: (filter: AppliedFilter) -> Unit,
    conferenceViewModel: ConferenceViewModel
) {
    if (filtersScreenState.shouldDisplay) {
        conferenceViewModel.loadCountryList()
        val countryListState =
            conferenceViewModel.countryListLiveData.observeAsState()
        Dialog(onCloseRequest = { filtersScreenState.shouldDisplay = false }) {
            FiltersScreen(
                filtersScreenState = filtersScreenState,
                onApplyClick = onApplyClick,
                countyList = countryListState.value
            )
        }
    }
}

@Composable
fun FiltersScreen(
    filtersScreenState: FiltersScreenState = FiltersScreenState(),
    onApplyClick: (filter: AppliedFilter) -> Unit,
    countyList: List<Country>?
) {
    Card(
        color = themeColors.secondary
    ) {
        Column(modifier = Modifier.wrapContentSize()) {
            Box(
                backgroundColor = themeColors.primary,
                gravity = ContentGravity.Center,
                padding = 8.dp,
                modifier = Modifier.fillMaxWidth().gravity(align = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "FILTERS",
                    color = themeColors.secondary,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.preferredHeight(4.dp))

            Text(
                text = "Cfp Status",
                modifier = Modifier.padding(start = 8.dp),
                color = themeColors.primary,
                style = MaterialTheme.typography.body1.merge(
                    other = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            )

            Spacer(modifier = Modifier.preferredHeight(4.dp))

            Clickable(
                modifier = Modifier.ripple().padding(start = 8.dp),
                onClick = {
                    filtersScreenState.cfpFilterChecked =
                        filtersScreenState.cfpFilterChecked.not()
                    appliedFilter.cfpFilterSelected = filtersScreenState.cfpFilterChecked
                }) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Checkbox(
                        checked = filtersScreenState.cfpFilterChecked,
                        onCheckedChange = {
                            filtersScreenState.cfpFilterChecked =
                                filtersScreenState.cfpFilterChecked.not()
                            appliedFilter.cfpFilterSelected = filtersScreenState.cfpFilterChecked
                        },
                        color = themeColors.primary
                    )
                    Text(
                        text = "Cfp Open",
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                        color = themeColors.primary,
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Spacer(modifier = Modifier.preferredHeight(8.dp))

            countyList?.let {
                CountryList(countyList = it)
            }

            Box(
                backgroundColor = themeColors.primary,
                padding = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Clickable(
                        modifier = Modifier.ripple(),
                        onClick = { filtersScreenState.shouldDisplay = false }) {
                        Text(
                            text = "CANCEL",
                            modifier = Modifier.padding(start = 8.dp),
                            color = themeColors.secondary,
                            style = MaterialTheme.typography.button.merge(
                                other = TextStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        )
                    }
                    Clickable(modifier = Modifier.ripple(), onClick = {
                        onApplyClick(appliedFilter)
                        filtersScreenState.shouldDisplay = false
                    }) {
                        Text(
                            text = "APPLY",
                            modifier = Modifier.padding(start = 8.dp),
                            color = themeColors.secondary,
                            style = MaterialTheme.typography.button.merge(
                                other = TextStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun CountryList(
    countyList: List<Country>
) {
    Column(
        modifier = Modifier.preferredHeight(360.dp)
    ) {
        Text(
            text = "Countries",
            color = themeColors.primary,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.body1.merge(
                other = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        )
        Spacer(modifier = Modifier.preferredHeight(8.dp))
        AdapterList(
            data = countyList,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        ) { country ->
            val countryChecked = state { appliedFilter.selectedCountries.contains(country) }
            Clickable(
                modifier = Modifier.ripple(),
                onClick = {
                    countryChecked.value = countryChecked.value.not()
                    if (countryChecked.value) {
                        appliedFilter.selectedCountries.add(country)
                    } else {
                        appliedFilter.selectedCountries.remove(country)
                    }
                }
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 4.dp)) {
                    Checkbox(
                        checked = countryChecked.value,
                        onCheckedChange = {
                            countryChecked.value = countryChecked.value.not()
                            if (countryChecked.value) {
                                appliedFilter.selectedCountries.add(country)
                            } else {
                                appliedFilter.selectedCountries.remove(country)
                            }
                        },
                        color = themeColors.primary
                    )
                    Text(
                        text = country.name,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                        color = themeColors.primary,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    ThemedPreview {
        FiltersScreen(
            filtersScreenState = FiltersScreenState(shouldDisplay = true),
            onApplyClick = {},
            countyList = listOf(
                Country("US"),
                Country("UK"),
                Country("India"),
                Country("Germany"),
                Country("Japan"),
                Country("Poland")
            )
        )
    }
}
