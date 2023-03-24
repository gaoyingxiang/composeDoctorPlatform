package view.composeview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import base.mvvm.IView
import res.IconRes

// 脚手架页
open class ScaffoldPage(
    private val application: ApplicationScope,
    private val windowScope: WindowScope,
    private val windowState: WindowState,
    private val title: @Composable () -> Unit,
    private val content: @Composable () -> Unit,
) : IView {
    @Composable
    override fun viewCompose() {
        CustomScaffold(
            application = application,
            windowScope = windowScope,
            windowState = windowState,
            title = title,
            content = content,
        )
    }
}


// 自定义脚手架组件
@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    application: ApplicationScope,
    windowScope: WindowScope,
    windowState: WindowState,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        content = {
            Column(modifier = modifier) {
                windowScope.WindowDraggableArea {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    ) {
                        title()
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                windowState.isMinimized = true
                            },
                            content = {
                                Icon(
                                    painter = IconRes.windowMinimized,
                                    contentDescription = "minimized",
                                    tint = Color(0XFF333333),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )

                        IconButton(
                            onClick = {
                                //StateManager.clearStateMaps()
                                application.exitApplication()
                            },
                            content = {
                                Icon(
                                    painter = IconRes.windowClosed,
                                    contentDescription = "closed",
                                    tint = Color(0XFF333333),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
                content()
            }
        },
    )
}