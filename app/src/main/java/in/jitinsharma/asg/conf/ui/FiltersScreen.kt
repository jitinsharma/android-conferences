package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.model.CfpOpenFilter
import `in`.jitinsharma.asg.conf.model.ConferenceFilter
import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Dialog
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

private val appliedFilters = mutableSetOf<ConferenceFilter>()

@Model
data class FiltersScreenState(
    var shouldDisplay: Boolean = false,
    var cfpFilterChecked: Boolean = appliedFilters.contains(CfpOpenFilter)
)

@Composable
fun FiltersScreen(
    filtersScreenState: FiltersScreenState = FiltersScreenState(),
    onApplyClick: (filters: Set<ConferenceFilter>) -> Unit
) {
    if (filtersScreenState.shouldDisplay) {
        Dialog(onCloseRequest = { filtersScreenState.shouldDisplay = false }) {
            Card(
                color = themeColors.secondary
            ) {
                Column(modifier = Modifier.wrapContentSize().padding(8.dp)) {
                    Text(
                        modifier = Modifier.gravity(align = Alignment.CenterHorizontally),
                        text = "FILTERS",
                        color = themeColors.primary,
                        style = MaterialTheme.typography.h6
                    )
                    Divider(
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                        color = themeColors.primary
                    )
                    
                    Clickable(
                        modifier = Modifier.ripple(),
                        onClick = {
                            filtersScreenState.cfpFilterChecked = filtersScreenState.cfpFilterChecked.not()
                            if (filtersScreenState.cfpFilterChecked) {
                                appliedFilters.add(CfpOpenFilter)
                            } else {
                                appliedFilters.remove(CfpOpenFilter)
                            }
                        }) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Checkbox(
                                checked = filtersScreenState.cfpFilterChecked,
                                onCheckedChange = {
                                    filtersScreenState.cfpFilterChecked = filtersScreenState.cfpFilterChecked.not()
                                    if (filtersScreenState.cfpFilterChecked) {
                                        appliedFilters.add(CfpOpenFilter)
                                    } else {
                                        appliedFilters.remove(CfpOpenFilter)
                                    }
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

                    Divider(
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                        color = themeColors.primary
                    )

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
                                color = themeColors.primary,
                                style = MaterialTheme.typography.button.merge(
                                    other = TextStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            )
                        }
                        Clickable(modifier = Modifier.ripple(), onClick = {
                            onApplyClick(appliedFilters)
                            filtersScreenState.shouldDisplay = false
                        }) {
                            Text(
                                text = "APPLY",
                                modifier = Modifier.padding(start = 8.dp),
                                color = themeColors.primary,
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
}

@Preview
@Composable
fun FilterScreenPreview() {
    MaterialTheme {
        Surface {
            FiltersScreen(
                onApplyClick = {}
            )
        }
    }
}