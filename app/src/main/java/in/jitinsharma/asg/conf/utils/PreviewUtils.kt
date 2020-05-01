package `in`.jitinsharma.asg.conf.utils

import `in`.jitinsharma.asg.conf.ui.themeColors
import androidx.compose.Composable
import androidx.ui.foundation.Box
import androidx.ui.material.ColorPalette
import androidx.ui.material.MaterialTheme

@Composable
internal fun ThemedPreview(
    colors: ColorPalette = themeColors,
    children: @Composable() () -> Unit
) {
    MaterialTheme(colors = colors) {
        Box(backgroundColor = MaterialTheme.colors.primary) {
            children()
        }
    }
}
