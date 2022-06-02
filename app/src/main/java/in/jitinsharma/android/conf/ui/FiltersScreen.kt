package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.model.Country
import `in`.jitinsharma.android.conf.utils.ThemedPreview
import `in`.jitinsharma.android.conf.viewmodel.FilterScreenUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun FilterDialog(
    filterScreenUiState: FilterScreenUiState,
    onDismissRequest: () -> Unit,
    onFilterRequest: (cfpFilterChecked: Boolean, selectedCountries: List<Country>) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        when (filterScreenUiState) {
            is FilterScreenUiState.Success -> {
                FiltersScreen(
                    cfpFilterChecked = filterScreenUiState.cfpFilterChecked,
                    selectedCountries = filterScreenUiState.selectedCountries.toMutableList(),
                    countyList = filterScreenUiState.countryList,
                    onDismiss = onDismissRequest,
                    onApply = onFilterRequest
                )
            }
        }
    }
}

@Composable
fun FiltersScreen(
    cfpFilterChecked: Boolean = false,
    selectedCountries: MutableList<Country>,
    countyList: List<Country>?,
    onDismiss: () -> Unit,
    onApply: (cfpFilterChecked: Boolean, selectedCountries: List<Country>) -> Unit
) {
    Card(backgroundColor = themeColors.secondary) {
        Column(modifier = Modifier.wrapContentSize()) {
            Box(
                modifier = Modifier
                    .background(color = themeColors.primary)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Text(
                    text = "FILTERS",
                    color = themeColors.secondary,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

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

            Spacer(modifier = Modifier.height(4.dp))

            val cfpFilterCheckState = remember { mutableStateOf(cfpFilterChecked) }
            Box(
                Modifier
                    .padding(start = 8.dp)
                    .clickable(
                        onClick = {
                            cfpFilterCheckState.value = cfpFilterCheckState.value.not()
                        }, enabled = true
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = cfpFilterCheckState.value,
                        onCheckedChange = {
                            cfpFilterCheckState.value = cfpFilterCheckState.value.not()
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary,
                            uncheckedColor = MaterialTheme.colors.primary
                        )
                    )
                    Text(
                        text = "Cfp Open",
                        color = themeColors.primary,
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            countyList?.let {
                CountryList(
                    countyList = it,
                    selectedCountries = selectedCountries
                )
            }

            Box(
                modifier = Modifier
                    .background(color = themeColors.primary)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        Modifier.clickable(
                            onClick = { onDismiss() })
                    ) {
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
                    Box(
                        Modifier.clickable(
                            onClick = { onApply(cfpFilterCheckState.value, selectedCountries) })
                    ) {
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
    countyList: List<Country>,
    selectedCountries: MutableList<Country>
) {
    Column(
        modifier = Modifier.height(360.dp)
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
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
            items(items = countyList,
                itemContent = { country ->
                    val countryChecked =
                        remember { mutableStateOf(selectedCountries.contains(country)) }
                    Box(Modifier.clickable(
                        onClick = {
                            countryChecked.value = countryChecked.value.not()
                            if (countryChecked.value) {
                                selectedCountries.add(country)
                            } else {
                                selectedCountries.remove(country)
                            }
                        }
                    )) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
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
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colors.primary,
                                    uncheckedColor = MaterialTheme.colors.primary
                                )
                            )
                            Text(
                                text = country.name,
                                color = themeColors.primary,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            )
        }
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
            selectedCountries = mutableListOf(),
            onDismiss = {},
            onApply = { _, _ -> }
        )
    }
}
