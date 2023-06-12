package vm

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import base.mvvm.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import log.Helper
import log.LoganLogItem
import view.home.HomeUIState
import view.home.data.LeftMenuBean
import java.io.File

class HomeViewModel : BaseViewModel() {
    val leftMenu = mutableListOf(
        LeftMenuBean("日志",Icons.Default.Home, id = "0"),
        LeftMenuBean("其他",Icons.Default.Build, id = "1"),
        LeftMenuBean("系统",Icons.Default.Settings, id = "2"),
    )
    var leftMenuSelectItem by mutableStateOf(LeftMenuBean("日志解析",Icons.Default.AddCircle, id = "日志解析"))
}