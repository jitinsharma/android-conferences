package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.R
import `in`.jitinsharma.asg.conf.redux.actions.DisplayDialog
import `in`.jitinsharma.asg.conf.redux.state.AppState
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.clickable
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import org.rekotlin.Store

@Composable
fun Header(
    store: Store<AppState>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(Modifier.clickable(
            indication = RippleIndication(),
            onClick = { store.dispatch(DisplayDialog()) }),
            children = {
                Image(
                    modifier = Modifier.padding(top = 12.dp),
                    asset = vectorResource(
                        id = R.drawable.ic_baseline_filter_list
                    )
                )
            })
        Image(
            asset = vectorResource(
                id = R.drawable.ic_android_symbol_green
            )
        )
        Box(Modifier.clickable(
            indication = RippleIndication(),
            onClick = {}),
            children = {
                Image(
                    modifier = Modifier.padding(top = 12.dp),
                    asset = vectorResource(
                        id = R.drawable.ic_baseline_notifications_none
                    )
                )
            })
    }
}

@Preview
@Composable
fun HeaderPreview() {
    ThemedPreview {
        //Header()
    }
}
