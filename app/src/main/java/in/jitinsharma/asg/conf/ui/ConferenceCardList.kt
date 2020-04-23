package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.model.ConferenceData
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.layout.padding
import androidx.ui.unit.dp

@Composable
fun ConferenceCardList(
    conferenceDataList: List<ConferenceData>
) {
    AdapterList(data = conferenceDataList) {
        val index = conferenceDataList.indexOf(it)
        ConferenceCard(
            modifier = if (index == 0) {
                Modifier.padding(top = 16.dp)
            } else {
                Modifier.padding(top = 32.dp)
            },
            conferenceData = it
        )
    }
}