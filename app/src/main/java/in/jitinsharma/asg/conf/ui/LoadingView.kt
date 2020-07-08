package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalGravity = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.secondary
        )
    }
}

@Preview
@Composable
fun LoadingViewPreview() {
    ThemedPreview {
        LoadingView()
    }
}
