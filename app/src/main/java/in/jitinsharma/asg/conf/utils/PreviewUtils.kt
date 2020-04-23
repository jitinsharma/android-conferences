package `in`.jitinsharma.asg.conf.utils

import `in`.jitinsharma.asg.conf.ui.themeColors
import androidx.compose.Composable
import androidx.ui.material.ColorPalette
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface

@Composable
internal fun ThemedPreview(
    colors: ColorPalette = themeColors,
    children: @Composable() () -> Unit
) {
    MaterialTheme(colors = colors) {
        Surface {
            children()
        }
    }
}
