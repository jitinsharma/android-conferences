package `in`.jitinsharma.android.conf.utils

import `in`.jitinsharma.android.conf.ui.themeColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ThemedPreview(
    colors: Colors = themeColors,
    content: @Composable () -> Unit
) {
    MaterialTheme(colors = colors) {
        Box(modifier = Modifier.background(color = MaterialTheme.colors.primary)) {
            content()
        }
    }
}
