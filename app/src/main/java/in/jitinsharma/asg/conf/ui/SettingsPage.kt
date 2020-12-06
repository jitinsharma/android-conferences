package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.preferences.AppPreferences
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun SettingsPage() {
    val appPreferences = remember { getKoin().get<AppPreferences>() }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val conferenceUpdateNotificationState =
            appPreferences.getUpdatePreference().collectAsState(initial = false)
        val scope = rememberCoroutineScope()
        Text(
            text = "Notification Preferences",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary
        )
        Divider(color = MaterialTheme.colors.secondary)
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = RippleIndication(),
                    onClick = {
                        scope.launch {
                            appPreferences.setUpdatePreference(conferenceUpdateNotificationState.value.not())
                        }
                    })
        ) {
            Text(
                text = "Conference Update Notification",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
            Checkbox(
                checked = conferenceUpdateNotificationState.value,
                onCheckedChange = {
                    scope.launch {
                        appPreferences.setUpdatePreference(
                            conferenceUpdateNotificationState.value.not()
                        )
                    }
                })
        }
    }
}

@Preview
@Composable
fun NotificationPagePreview() {
    ThemedPreview {
        SettingsPage()
    }
}