package view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import base.mvvm.BaseView
import base.mvvm.ViewCompose
import log.Helper
import view.home.HomeView
import vm.LoginViewModel

class LoginView(
    application: ApplicationScope,
    windowScope: WindowScope,
    windowState: WindowState,
) : BaseView<LoginViewModel>() {
    override fun createModel(): LoginViewModel  = LoginViewModel()
    @Composable
    override fun viewCompose() {
        Box(modifier = Modifier.fillMaxSize()){
            Row {
                Box(modifier = Modifier.fillMaxHeight().weight(9f)) {
                    Image(
                        painter = painterResource("images/login_left_icon.webp"),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight().weight(16f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("门店日志解析", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(15.dp))
                    AccountLoginPage() { user, psw ->
                        model.login(user,psw)
//                        helper.showFileSelector() { dir, file ->
//                            helper.addition_isCorrect(file, dir)
//                        }
                    }
                }
            }
        }
    }


}