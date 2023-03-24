import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import base.mvvm.BaseView
import base.mvvm.ViewCompose
import utils.ViewModelManager
import view.composeview.ComposeToastContainer
import view.composeview.CustomScaffold
import view.composeview.SearchTextField
import view.home.HomeView
import view.login.LoginView
import vm.HomeViewModel
import vm.MainModel


class MyDesktopApp(private var application: ApplicationScope) : BaseView<MainModel>() {
    override fun createModel(): MainModel = MainModel()

    @Composable
    override fun viewCompose() {
        var searchText = remember {
            mutableStateOf("")
        }
        val windowState = rememberWindowState(
            width = 1280.dp,
            height = 720.dp,
            position = WindowPosition.Aligned(Alignment.Center),
        )
        Window(
            onCloseRequest = { application.exitApplication() },
            title = "门店日志工具",
            state = windowState,
            resizable = true,
            undecorated = true,
            transparent = true,
            icon = painterResource("images/ic_logo_round.png")
        ) {
            MaterialTheme {
                ComposeToastContainer {
                    CustomScaffold(
                        modifier = Modifier.border(1.dp, Color.Black.copy(alpha = 0.05f)),
                        application = application,
                        windowScope = this,
                        windowState = windowState,
                        title = {
                           AnimatedVisibility(model.page!=1){
                               Row (verticalAlignment = Alignment.CenterVertically){
                                   Spacer(modifier = Modifier.width(10.dp))
                                   SearchTextField(
                                       searchText.value,
                                       onValueChange = {
                                           searchText.value = it
                                           val homeViewModel = ViewModelManager.findModel<HomeViewModel>(HomeView::class.java)
                                           homeViewModel?.search(searchText.value)
                                       },
                                       hint = { Text("输入要搜索的关键字") },
                                       leadingIcon = {
                                           Icon(imageVector = Icons.Default.Search, contentDescription = null)
                                       },
                                       modifier = Modifier.size(width = 500.dp, height = 40.dp)
                                           .background(Color.Black.copy(0.06f), shape = RoundedCornerShape(8.dp))
                                           .padding(horizontal = 5.dp)
                                   )
                                   Spacer(modifier = Modifier.width(10.dp))
//                                   Button(onClick = {
//
//                                   }){
//                                       Text("搜索")
//                                   }
                               }
                           }
                        }) {
                        Card(
                            elevation = 4.dp,
                            backgroundColor = Color.White,
                        ) {
                            ViewCompose {
                                when (model.page) {
                                    1 -> LoginView(
                                        application = application,
                                        windowScope = this,
                                        windowState = windowState
                                    )
                                    2 -> HomeView()
                                    else -> HomeView()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


fun main() = application {
    ViewCompose {
        MyDesktopApp(this)
    }
}
