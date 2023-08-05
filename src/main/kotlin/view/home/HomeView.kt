package view.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import base.mvvm.BaseView
import base.mvvm.ViewCompose
import net.HttpUtil
import res.ColorRes
import res.TextStyleRes
import view.about.AboutView
import view.composeview.CustomScaffold
import view.other.OtherView
import view.qlog.DecodeLogView
import vm.HomeViewModel

@ExperimentalComposeUiApi
class HomeView(
    private val application: ApplicationScope,
    private val windowScope: WindowScope,
    private val windowState: WindowState,
) : BaseView<HomeViewModel>() {
    override fun createModel(): HomeViewModel = HomeViewModel()

    @Composable
    override fun viewCompose() {
        Row(modifier = Modifier.fillMaxSize()) {
            LeftMenu()
            RightContent()
        }
    }


    @Composable
    fun LeftMenu() {
        Card(
            elevation = 4.dp,
            modifier = Modifier.width(127.dp).padding(end = 1.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(model.leftMenu) {
                    TextButton(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(8.dp).heightIn(min = 48.dp),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (model.leftMenuSelectItem == it) ColorRes.onSurface else ColorRes.transparent,
                            backgroundColor = if (model.leftMenuSelectItem == it) ColorRes.onSurface else ColorRes.transparent,
                        ),
                        onClick = { model.leftMenuSelectItem = it },
                    ) {
                        Icon(
                            it.imageVector,
                            contentDescription = null,
                            tint = if (model.leftMenuSelectItem == it) ColorRes.primary else ColorRes.text,
                            modifier = Modifier.padding(horizontal = 12.dp).size(24.dp),
                        )
                        Text(
                            text = it.title,
                            style = if (model.leftMenuSelectItem == it) TextStyleRes.bodyMediumSurface else TextStyleRes.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

            }
        }
    }

    @Composable
    fun RightContent() {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            ViewCompose {
                when (model.leftMenuSelectItem.id) {
                    "0" -> DecodeLogView()
                    "1" -> OtherView()
                    "2" -> AboutView()
                    else -> DecodeLogView()
                }
            }
        }
    }
}

