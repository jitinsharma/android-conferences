package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.R
import `in`.jitinsharma.android.conf.utils.ThemedPreview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onFilterClicked: () -> Unit,
    onAndroidIconClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(
            Modifier.clickable(
                onClick = { onFilterClicked() })
        ) {
            Image(
                modifier = Modifier.padding(top = 12.dp),
                painter = painterResource(
                    id = R.drawable.ic_baseline_filter_list
                ),
                contentDescription = "Open Filter conference dialog"
            )
        }
        Box(
            Modifier.clickable(
                onClick = { onAndroidIconClicked() })
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_android_symbol_green
                ),
                contentDescription = "Open conference list page"
            )
        }
        Box(
            Modifier.clickable(
                onClick = { onSettingsClicked() })
        ) {
            Image(
                modifier = Modifier.padding(top = 12.dp),
                painter = painterResource(
                    id = R.drawable.ic_baseline_settings
                ),
                contentDescription = "Open settings page"
            )
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    ThemedPreview {
        Header(
            onFilterClicked = {},
            onAndroidIconClicked = {},
            onSettingsClicked = {}
        )
    }
}
