package view.composeview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import res.ColorRes
import res.TextStyleRes


/// Loading控制器
class ComposeLoading : ComposeController {
    private var status = mutableStateOf(false)
    private val messageText = mutableStateOf("请稍后..")

    companion object {
        private val instance = ComposeLoading()

        fun show(message: String) {
            instance.show(message)
        }

        fun hide() {
            instance.hide()
        }

        fun defaultLoadingController() = instance
    }

    fun show(message: String) {
        messageText.value = message
        show()
    }

    override fun show() {
        if (!status.value) status.value = true
    }

    override fun hide() {
        status.value = false
    }

    override val isShowing: Boolean
        get() = status.value

    val message: String
        get() = messageText.value
}

@Composable
fun ComposeLoadingContainer(
    windowScope: WindowScope,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val controller = ComposeLoading.defaultLoadingController()

    Box(
        modifier = modifier,
        content = {
            content()

            if (controller.isShowing) {
                windowScope.WindowDraggableArea {
                    Card(
                        elevation = 0.dp,
                        backgroundColor = Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier,
                                content = {
                                    CircularProgressIndicator()
                                    Spacer(Modifier.padding(vertical = 6.dp))
                                    Text(
                                        text = controller.message,
                                        style = TextStyleRes.bodyMedium.copy(color = ColorRes.white)
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    )
}

