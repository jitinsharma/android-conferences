package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.redux.actions.DisplayLoading
import `in`.jitinsharma.asg.conf.redux.actions.LoadConferences
import `in`.jitinsharma.asg.conf.redux.state.AppState
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import org.koin.java.KoinJavaComponent.getKoin
import org.rekotlin.Store

@Composable
fun WtfView() {
    val store = remember { getKoin().get<Store<AppState>>() }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalGravity = Alignment.CenterHorizontally
    ) {
        Text(
            text = annotatedString {
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
        WtfView()
    }
}
