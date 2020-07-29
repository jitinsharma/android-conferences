package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.model.Country
import `in`.jitinsharma.asg.conf.redux.actions.*
import `in`.jitinsharma.asg.conf.redux.state.AppState
import `in`.jitinsharma.asg.conf.redux.state.FilterState
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import androidx.compose.Composable
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.Checkbox
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import org.koin.java.KoinJavaComponent.getKoin
import org.rekotlin.Store

@Composable
fun FilterDialog(
    filterState: FilterState
) {
    if (filterState.displayDialog) {
        val store = remember { getKoin().get<Store<AppState>>() }
        store.dispatch(LoadCountries())
        Dialog(onCloseRequest = { store.dispatch(HideDialog()) }) {
            FiltersScreen(
                cfpFilterChecked = filterState.cfpFilterChecked,
                selectedCountries = filterState.selectedCountries,
                countyList = filterState.countryList
            )
        }
    }
}

@Composable
fun FiltersScreen(
    cfpFilterChecked: Boolean = false,
    selectedCountries: MutableList<Country>,
    countyList: List<Country>?
) {
    val store = remember { getKoin().get<Store<AppState>>() }
    Card(color = themeColors.secondary) {
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

            val cfpFilterCheckState = state { cfpFilterChecked }
            Box(Modifier.padding(start = 8.dp).clickable(
                indication = RippleIndication(),
                onClick = {
                    cfpFilterCheckState.value = cfpFilterCheckState.value.not()
                }, enabled = true
            ), children = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Checkbox(
                        checked = cfpFilterCheckState.value,
                        onCheckedChange = {
                            cfpFilterCheckState.value = cfpFilterCheckState.value.not()
                        },
                        checkedColor = themeColors.primary
                    )
                    Text(
                        text = "Cfp Open",
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                        color = themeColors.primary,
                        style = MaterialTheme.typography.body2
                    )
                }
            })

            Spacer(modifier = Modifier.preferredHeight(8.dp))

            countyList?.let {
                CountryList(
                    countyList = it,
                    selectedCountries = selectedCountries
                )
            }

            Box(
                backgroundColor = themeColors.primary,
                padding = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(Modifier.clickable(
                        indication = RippleIndication(),
                        onClick = { store.dispatch(HideDialog()) }),
                        children = {
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
                        })
                    Box(Modifier.clickable(
                        indication = RippleIndication(),
                        onClick = {
                            store.dispatch(SetCFPFilterCheck(cfpFilterCheckState.value))
                            store.dispatch(SetSelectedCountries(selectedCountries))
                            store.dispatch(HideDialog())
                            store.dispatch(
                                FilterConferences(
                                    cfpFilterCheckState.value,
                                    selectedCountries
                                )
                            )
                        }), children = {
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
                    })
                }
            }

        }
    }
}

@Composable
fun CountryList(
    countyList: List<Country>,
    selectedCountries: MutableList<Country>
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
        LazyColumnItems(items = countyList,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
            itemContent = { country ->
                val countryChecked = state { selectedCountries.contains(country) }
                Box(Modifier.clickable(
                    indication = RippleIndication(),
                    onClick = {
                        countryChecked.value = countryChecked.value.not()
                        if (countryChecked.value) {
                            selectedCountries.add(country)
                        } else {
                            selectedCountries.remove(country)
                        }
                    }
                ), children = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 4.dp)
                    ) {
                        Checkbox(
                            checked = countryChecked.value,
                            onCheckedChange = {
                                countryChecked.value = countryChecked.value.not()
                                if (countryChecked.value) {
                                    selectedCountries.add(country)
                                } else {
                                    selectedCountries.remove(country)
                                }
                            },
                            checkedColor = themeColors.primary
                        )
                        Text(
                            text = country.name,
                            modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                            color = themeColors.primary,
                            style = MaterialTheme.typography.body2
                        )
                    }
                })
            })
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    ThemedPreview {
        FiltersScreen(
            countyList = listOf(
                Country("US"),
                Country("UK"),
                Country("India"),
                Country("Germany"),
                Country("Japan"),
                Country("Poland")
            ),
            selectedCountries = mutableListOf()
        )
    }
}
