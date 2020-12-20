package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import `in`.jitinsharma.asg.conf.viewmodel.FilterScreenViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ConferenceApp(
    conferenceViewModel: ConferenceViewModel,
    filterScreenViewModel: FilterScreenViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.primary)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {
            val filterScreenDialogState = remember { mutableStateOf(false) }
            Header(
                modifier = Modifier.padding(bottom = 16.dp),
                onFilterClicked = {
                    filterScreenDialogState.value = filterScreenDialogState.value.not()
                },
                onSettingsClicked = {}
            )

            val conferenceUiState = conferenceViewModel.uiState.collectAsState()
            ConferencePage(
                conferenceListUiState = conferenceUiState.value,
                onRetryClick = {
                    conferenceViewModel.loadConferences()
                }
            )

            if (filterScreenDialogState.value) {
                val filterScreenUiState = filterScreenViewModel.uiState.collectAsState()
                FilterDialog(
                    filterScreenUiState = filterScreenUiState.value,
                    onDismissRequest = {
                        filterScreenDialogState.value = false
                    },
                    onFilterRequest = { cfpFilterChecked, selectedCountries ->
                        conferenceViewModel.filterConferences(cfpFilterChecked, selectedCountries)
                        filterScreenViewModel.updateUiState(cfpFilterChecked, selectedCountries)
                        filterScreenDialogState.value = false
                    }
                )
            }
        }
    }
}
