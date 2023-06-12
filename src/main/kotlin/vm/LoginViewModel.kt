package vm

import MyDesktopApp
import androidx.compose.ui.ExperimentalComposeUiApi
import base.mvvm.BaseViewModel
import utils.ViewModelManager
import view.composeview.ComposeToast


class LoginViewModel : BaseViewModel() {
    @ExperimentalComposeUiApi
    val mainModel = ViewModelManager.findModel<MainModel>(MyDesktopApp::class.java)

    /**
     * 登录
     */
    fun login(user: String, psw: String) {
//        mainModel?.page = if ((user == "1" && psw == "1")) {
//            2
//        } else {
//            ComposeToast.show("账号密码错误")
//            1
//        }
    }
}