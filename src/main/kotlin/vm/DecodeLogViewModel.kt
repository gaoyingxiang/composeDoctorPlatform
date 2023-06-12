package vm

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
import java.io.File

class DecodeLogViewModel : BaseViewModel() {
    private val helper by lazy { Helper() }

    var uiState by mutableStateOf<HomeUIState?>(null)

    /**
     * 数据
     */
    var logData by mutableStateOf(
        mutableListOf<LoganLogItem>()
    )

    /**
     * 选中的数据
     */
    var selectItem by mutableStateOf<LoganLogItem?>(null)

    /**
     * 打开选择文件
     */
    fun openChooseFile() {
        kotlin.runCatching {
            helper.showFileSelector { dir, file ->
                uiState = HomeUIState.LogoDecodeLoading
                CoroutineScope(Dispatchers.IO).launch {
                    val logLs = decodeFile(file, dir)
                    if (logLs.isNullOrEmpty()) {
                        uiState = HomeUIState.LogoDecodeFail("解析失败")
                        return@launch
                    }
                    println(logLs)
                    logData = logLs
                    uiState = HomeUIState.LogoDecodeSuccess(logData)
                }
            }
        }.onFailure {
            uiState = HomeUIState.LogoDecodeFail("解析失败,已生成Json文件，可直接查看")
        }
    }

    /**
     * 解析文件
     */
    private suspend fun decodeFile(inputFile: File, dir: String): MutableList<LoganLogItem> {
        return suspendCancellableCoroutine { cancellableContinuation ->
            kotlin.runCatching {
                CoroutineScope(Dispatchers.IO).launch {
                    val data = helper.decodeFile(inputFile, dir)
                    println(data)
                    cancellableContinuation.resumeWith(Result.success(data))
                }
            }.onFailure {
                cancellableContinuation.resumeWith(Result.success(mutableListOf()))
            }
        }
    }

    /**
     * 搜索
     */
    fun search(searchText: String, inflateStr: String = "") {
        val ls = logData.filter { f ->
            (f.c?.contains(searchText, ignoreCase = true) ?: false || f.l?.contains(
                searchText,
                ignoreCase = true
            ) ?: false) || (f.c?.contains(inflateStr, ignoreCase = true) ?: false || f.l?.contains(
                inflateStr, ignoreCase = true
            ) ?: false)
        }.toMutableList()
        uiState = if (ls.isNotEmpty()) {
            HomeUIState.LogoDecodeSuccess(ls)
        } else {
            HomeUIState.LogoDecodeSearchEmpty("未搜索到内容")
        }
    }
}