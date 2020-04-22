package `in`.jitinsharma.asg.conf.ui

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
import androidx.ui.text.AnnotatedString
import androidx.ui.text.SpanStyle
import androidx.ui.text.TextStyle
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp

@Composable
fun WtfView(
    onRetryClick: () -> Unit
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
            color = Color(0xFF3DDB85),
            style = TextStyle(fontSize = TextUnit.Sp(32))
        )
        Button(
            modifier = Modifier.padding(top = 8.dp),
            backgroundColor = Color(0xFF3DDB85),
            onClick = { onRetryClick() }) {
            Text(
                text = "Retry",
                color = Color(0xFF092432)
            )
        }
    }
}