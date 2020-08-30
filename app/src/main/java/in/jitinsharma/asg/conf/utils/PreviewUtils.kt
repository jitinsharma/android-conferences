package `in`.jitinsharma.asg.conf.utils

import `in`.jitinsharma.asg.conf.ui.themeColors
import androidx.compose.foundation.Box
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun ThemedPreview(
    colors: Colors = themeColors,
    children: @Composable() () -> Unit
) {
    MaterialTheme(colors = colors) {
        Box(backgroundColor = MaterialTheme.colors.primary) {
            children()
        }
    }
}
