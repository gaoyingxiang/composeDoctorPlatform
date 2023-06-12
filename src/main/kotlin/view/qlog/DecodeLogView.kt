package view.qlog

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.mvvm.BaseView
import log.LoganLogItem
import res.ColorRes
import view.ext.click
import view.home.HomeUIState
import vm.DecodeLogViewModel

@ExperimentalComposeUiApi
class DecodeLogView : BaseView<DecodeLogViewModel>() {
    override fun createModel(): DecodeLogViewModel = DecodeLogViewModel()

    @Composable
    override fun viewCompose() {
        var textValue = remember { mutableStateOf("") }
        var showDropdownMenu = remember {
            mutableStateOf(false)
        }
        var inflateStr = remember { mutableStateOf("全部日志") }
        Scaffold(floatingActionButton = {
            Box(
                modifier = Modifier.size(50.dp).background(color = MaterialTheme.colors.primary, shape = CircleShape)
                    .clickable {
                        model.openChooseFile()
                    }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "添加解析文件",
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }, modifier = Modifier.fillMaxSize()) {
            Column {
                //筛选条件
                if (model.uiState is HomeUIState.LogoDecodeSuccess || model.uiState is HomeUIState.LogoDecodeSearchEmpty) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(20.dp))
                        OutlinedTextField(value = textValue.value, onValueChange = {
                            textValue.value = it
                            model.search(it, inflateStr.value)
                        }, label = { Text("请输入需要搜索的内容") }, modifier = Modifier.height(60.dp).height(280.dp))
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(text = inflateStr.value, style = TextStyle(
                            color = ColorRes.text,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ), modifier = Modifier.click { showDropdownMenu.value = true })
                        //筛选
                        DropdownMenu(
                            expanded = showDropdownMenu.value,
                            onDismissRequest = {
                                showDropdownMenu.value = !showDropdownMenu.value
                            },
                            offset = DpOffset(310.dp, 0.dp),
                            modifier = Modifier.width(150.dp).clip(RoundedCornerShape(10.dp)).background(
                                Color.White
                            )
                        ) {
                            DropdownMenuItem(content = {
                                Text("全部日志")//printerRepeatProcess
                            }, onClick = {
                                inflateStr.value = "全部日志"
                                model.search(textValue.value, "")
                            })
                            DropdownMenuItem(content = {
                                Text("打印机补打")//printerRepeatProcess
                            }, onClick = {
                                inflateStr.value = "打印机补打流程"
                                model.search(textValue.value, "printerRepeatProcess")
                            })
                            DropdownMenuItem(content = {
                                Text("打印机ACK流程")//printerAckProcess
                            }, onClick = {
                                inflateStr.value = "打印机ACK流程"
                                model.search(textValue.value, "printerAckProcess")
                            })
                            DropdownMenuItem(content = {
                                Text("打印机转换流程")//printerConvertProcess
                            }, onClick = {
                                inflateStr.value = "打印机转换流程"
                                model.search(textValue.value, "printerConvertProcess")
                            })
                            DropdownMenuItem(content = {
                                Text("打印机打印流程")//printerPrintProcess
                            }, onClick = {
                                inflateStr.value = "打印机打印流程"
                                model.search(textValue.value, "printerPrintProcess")
                            })
                        }
                    }
                }
                //内容部分
                when (model.uiState) {
                    is HomeUIState.LogoDecodeLoading -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    is HomeUIState.LogoDecodeSuccess -> {
                        val success = (model.uiState) as HomeUIState.LogoDecodeSuccess

                        Row(modifier = Modifier.fillMaxSize()) {
                            //日志列表
                            LogLsView(data = success.data, modifier = Modifier.fillMaxWidth().weight(3f))
                            //日志详情
                            LogInfo(modifier = Modifier.weight(2f))
                        }

                    }
                    is HomeUIState.LogoDecodeFail -> {
                        DecodeErrorPage()
                    }
                    else -> EmptyPage()
                }
            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    fun LogLsView(modifier: Modifier = Modifier, data: MutableList<LoganLogItem>) {
        LazyColumn(modifier = Modifier.then(modifier)) {
            items(data) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {
                        model.selectItem = it
                    },
                    elevation = 4.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (model.selectItem == it) ColorRes.primary else ColorRes.transparent
                    ),
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                        //日志时间
                        Text(
                            it.l ?: "",
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(Modifier.height(5.dp))
                        //日志内容
                        Text(
                            it.c ?: "", style = TextStyle(
                                fontSize = 16.sp, color = ColorRes.text
                            ), maxLines = 3, overflow = TextOverflow.Ellipsis, modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun LogInfo(modifier: Modifier = Modifier) {
        Card(
            modifier = modifier.fillMaxWidth().fillMaxHeight().padding(5.dp),
            elevation = 4.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(8.dp),
        ) {
            if (model.selectItem == null) {
                EmptyPage()
            } else {
                LazyColumn(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
                    item {
                        Text(
                            "日志时间",
                            style = TextStyle(fontSize = 20.sp, color = ColorRes.text, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        Text(
                            model.selectItem?.l ?: "",
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                    }
                    item {
                        Spacer(Modifier.height(10.dp))
                    }
                    item {
                        Text(
                            "日志类型",
                            style = TextStyle(fontSize = 20.sp, color = ColorRes.text, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        //日志类型
                        Text(
                            getLogType(model.selectItem?.f), style = TextStyle(
                                fontSize = 16.sp, color = ColorRes.text
                            )
                        )
                    }
                    item {
                        Spacer(Modifier.height(10.dp))
                    }
                    item {
                        Text(
                            "线程名称",
                            style = TextStyle(fontSize = 20.sp, color = ColorRes.text, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        //线程名称
                        Text(
                            model.selectItem?.n ?: "", style = TextStyle(
                                fontSize = 16.sp, color = ColorRes.text
                            )
                        )
                    }
                    item {
                        Spacer(Modifier.height(10.dp))
                    }
                    item {
                        Text(
                            "线程ID",
                            style = TextStyle(fontSize = 20.sp, color = ColorRes.text, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        //线程id
                        Text(
                            model.selectItem?.i ?: "", style = TextStyle(
                                fontSize = 16.sp, color = ColorRes.text
                            )
                        )
                    }
                    item {
                        Spacer(Modifier.height(10.dp))
                    }
                    item {
                        Text(
                            "是否是主线程",
                            style = TextStyle(fontSize = 20.sp, color = ColorRes.text, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        //是否是主线程
                        Text(
                            model.selectItem?.m ?: "", style = TextStyle(
                                fontSize = 16.sp, color = ColorRes.text
                            )
                        )
                    }
                    item {
                        Text(
                            "日志内容",
                            style = TextStyle(fontSize = 20.sp, color = ColorRes.text, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        Text(
                            model.selectItem?.c ?: "", style = TextStyle(
                                fontSize = 16.sp, color = ColorRes.text
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getLogType(f: String?): String {
        return when (f) {
            "2" -> "调试信息"
            "3" -> "提示信息"
            "4" -> "错误信息"
            "5" -> "警告信息"
            else -> "其他"
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
                Image(
                    painter = painterResource("images/icon_empty_has_test.png"),
                    null,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }

    @Composable
    fun DecodeErrorPage() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Image(painter = painterResource("images/ic_error_pic.png"), null)
            }
        }
    }
}