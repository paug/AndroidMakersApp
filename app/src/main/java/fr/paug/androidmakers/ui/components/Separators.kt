import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.ui.theme.AMAlpha
import fr.paug.androidmakers.ui.theme.AMColor

@Composable
fun separatorColor() = MaterialTheme.colors.onSurface.copy(alpha = AMAlpha.big)

/**
 * A surface that's a bit darker or lighter to distinguish from the background
 */
@Composable
fun surfaceColor2() = MaterialTheme.colors.onSurface.copy(alpha = AMAlpha.small)

val separatorHeight = 0.5.dp