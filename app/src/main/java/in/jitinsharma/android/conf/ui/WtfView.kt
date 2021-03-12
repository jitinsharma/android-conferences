package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.utils.ThemedPreview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WtfView(
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                pushStyle(SpanStyle(color = Color(0xFFCEECFD)))
                append("{")
                pop()
                append(" WTF ")
                pushStyle(SpanStyle(color = Color(0xFFCEECFD)))
                append("}")
                pop()
            },
            color = MaterialTheme.colors.secondary,
            style = TextStyle(fontSize = 32.sp)
        )
        Button(
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
            onClick = { onRetryClick() }) {
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
        WtfView(onRetryClick = {})
    }
}
