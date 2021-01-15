package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.R
import `in`.jitinsharma.android.conf.utils.ThemedPreview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
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
                indication = rememberRipple(),
                onClick = { onFilterClicked() })
        ) {
            Image(
                modifier = Modifier.padding(top = 12.dp),
                imageVector = vectorResource(
                    id = R.drawable.ic_baseline_filter_list
                )
            )
        }
        Box(
            Modifier.clickable(
                indication = rememberRipple(),
                onClick = { onAndroidIconClicked() })
        ) {
            Image(
                imageVector = vectorResource(
                    id = R.drawable.ic_android_symbol_green
                )
            )
        }
        Box(
            Modifier.clickable(
                indication = rememberRipple(),
                onClick = { onSettingsClicked() })
        ) {
            Image(
                modifier = Modifier.padding(top = 12.dp),
                imageVector = vectorResource(
                    id = R.drawable.ic_baseline_settings
                )
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
