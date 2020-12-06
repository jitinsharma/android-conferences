package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.R
import `in`.jitinsharma.asg.conf.redux.actions.DisplayDialog
import `in`.jitinsharma.asg.conf.redux.actions.DisplaySettings
import `in`.jitinsharma.asg.conf.redux.state.AppState
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.material.ripple.rememberRippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.java.KoinJavaComponent.getKoin
import org.rekotlin.Store

@Composable
fun Header(
    modifier: Modifier = Modifier,
) {
    val store = remember { getKoin().get<Store<AppState>>() }
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(
            Modifier.clickable(
                indication = rememberRippleIndication(),
                onClick = { store.dispatch(DisplayDialog()) })
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
                indication = rememberRippleIndication(),
                onClick = {})
        ) {
            Image(
                imageVector = vectorResource(
                    id = R.drawable.ic_android_symbol_green
                )
            )
        }
        Box(
            Modifier.clickable(
                indication = RippleIndication(),
                onClick = { store.dispatch(DisplaySettings()) })
        ) {
            Image(
                modifier = Modifier.padding(top = 12.dp),
                imageVector = vectorResource(
                    id = R.drawable.ic_baseline_notifications_none
                )
            )
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    ThemedPreview {
        Header()
    }
}
