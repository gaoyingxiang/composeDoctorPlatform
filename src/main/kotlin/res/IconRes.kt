package res

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.Density

object IconRes {
    val windowMinimized = readIconPainter("icons/ic_window_minimized.svg")
    val windowClosed = readIconPainter("icons/ic_window_closed.svg")
    private fun readIconPainter(resourcePath: String): Painter {
        return useResource(resourcePath) {
            loadSvgPainter(it, Density(1f))
        }
    }
}