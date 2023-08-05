import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import base.mvvm.BaseView
import base.mvvm.ViewCompose
import res.ColorRes
import res.TextStyleRes
import view.composeview.ComposeDialogContainer
import view.composeview.ComposeLoadingContainer
import view.composeview.ComposeToastContainer
import view.composeview.CustomScaffold
import view.home.HomeView
import vm.MainModel

@ExperimentalComposeUiApi
class MyDesktopApp(private var application: ApplicationScope) : BaseView<MainModel>() {
    override fun createModel(): MainModel = MainModel()

    @Composable
    override fun viewCompose() {
        val windowState = rememberWindowState(
            width = 1280.dp,
            height = 720.dp,
            position = WindowPosition.Aligned(Alignment.Center),
        )
        Window(
            onCloseRequest = { application.exitApplication() },
            title = "Qmai工具箱",
            state = windowState,
            resizable = true,
            undecorated = true,
            transparent = true,
//            icon = painterResource("images/ic_logo_round.png")
        ) {
            MaterialTheme(
                colors = MaterialTheme.colors.copy(
                    primary = ColorRes.primary,
                    onSurface = ColorRes.onSurface,
                )
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    border = BorderStroke(1.dp, Color(0xFF999999).copy(alpha = 0.3f)),
                    modifier = Modifier.padding(12.dp),
                ) {
                    ComposeToastContainer {
                        ComposeLoadingContainer(windowScope = this) {
                            ComposeDialogContainer {
                                CustomScaffold(application = application,
                                    windowScope = this,
                                    windowState = windowState,
                                    title = {
                                        Text(
                                            text = "Qmai工具箱",
                                            style = TextStyleRes.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }, content = {
                                        ViewCompose {
                                            HomeView(
                                                application = application,
                                                windowScope = this,
                                                windowState = windowState,
                                            )
                                        }
                                    })

                            }
                        }
                    }
                }
            }
        }
    }

}

@ExperimentalComposeUiApi
fun main() = application {
    ViewCompose {
        MyDesktopApp(this)
    }
}
