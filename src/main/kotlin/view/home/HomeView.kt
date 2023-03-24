package view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.mvvm.BaseView
import log.LoganLogItem
import vm.HomeViewModel

class HomeView : BaseView<HomeViewModel>() {
    override fun createModel(): HomeViewModel = HomeViewModel()

    @Composable
    override fun viewCompose() {
        Scaffold(floatingActionButton = {
            Box(modifier = Modifier.size(50.dp).background(color = MaterialTheme.colors.primary, shape = CircleShape)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "添加解析文件",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        model.openChooseFile()
                    }.align(Alignment.Center)
                )
            }
        }, modifier = Modifier.fillMaxSize()) {
            when (model.uiState) {
                is HomeUIState.LogoDecodeLoading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                is HomeUIState.LogoDecodeSuccess -> {
                    val success = (model.uiState) as HomeUIState.LogoDecodeSuccess
                    Column(modifier = Modifier.fillMaxSize()) {
                        LogContent(data = success.data)
                    }
                }
                else -> EmptyPage()
            }
        }
    }


    @Composable
    fun LogContent(data: MutableList<LoganLogItem>) {
        LazyColumn() {
            items(data) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
                        Text(
                            it.l ?: "",
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )//时间
                        Spacer(Modifier.height(5.dp))
                        Text(it.c ?: "", style = TextStyle(
                            fontSize = 14.sp
                        ))//内容
//                        Text(it.f ?: "")
//                        Text(it.n ?: "")
//                        Text(it.i ?: "")
//                        Text(it.m ?: "")
                    }
                }
            }
        }
    }

    @Composable
    fun EmptyPage() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource("images/icon_empty_has_test.png"), null)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "点击右下角添加解析文件",
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

