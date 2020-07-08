package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.redux.actions.DisplayLoading
import `in`.jitinsharma.asg.conf.redux.actions.LoadConferences
import `in`.jitinsharma.asg.conf.redux.state.AppState
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.text.AnnotatedString
import androidx.ui.text.SpanStyle
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import org.rekotlin.Store

@Composable
fun WtfView(
    store: Store<AppState>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalGravity = Alignment.CenterHorizontally
    ) {
        Text(
            text = AnnotatedString {
                pushStyle(SpanStyle(color = Color(0xFFCEECFD)))
                append("{")
                pop()
                append(" WTF ")
                pushStyle(SpanStyle(color = Color(0xFFCEECFD)))
                append("}")
                pop()
            },
            color = MaterialTheme.colors.secondary,
            style = TextStyle(fontSize = TextUnit.Sp(32))
        )
        Button(
            modifier = Modifier.padding(top = 8.dp),
            backgroundColor = MaterialTheme.colors.secondary,
            onClick = {
                store.dispatch(DisplayLoading())
                store.dispatch(LoadConferences())
            }) {
            Text(
                text = "Retry",
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Preview
@Composable
fun WtfViewPreview() {
    ThemedPreview {
        //WtfView()
    }
}
