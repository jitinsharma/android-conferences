package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.model.ConferenceData
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConferenceCardList(
    conferenceDataList: List<ConferenceData>
) {
    LazyColumn {
        itemsIndexed(items = conferenceDataList, itemContent = { index, item ->
            ConferenceCard(
                modifier = if (index == 0) {
                    Modifier.padding(top = 16.dp)
                } else {
                    Modifier.padding(top = 32.dp)
                },
                conferenceData = item
            )
        })
    }
}
