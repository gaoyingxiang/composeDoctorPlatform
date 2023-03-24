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

class HomeViewModel : BaseViewModel() {
    private val helper by lazy { Helper() }

    var uiState by mutableStateOf<HomeUIState?>(null)

    /**
     * 数据
     */
    var logData by mutableStateOf(
        mutableListOf<LoganLogItem>()
    )

    /**
     * 打开选择文件
     */
    fun openChooseFile() {
        helper.showFileSelector{ dir, file ->
            uiState = HomeUIState.LogoDecodeLoading
            CoroutineScope(Dispatchers.IO).launch{
                val logLs = decodeFile(file,dir)
                println(logLs)
                logData = logLs
                uiState = HomeUIState.LogoDecodeSuccess(logData)
            }
        }
    }

    /**
     * 解析文件
     */
    private suspend fun decodeFile(inputFile: File, dir:String):MutableList<LoganLogItem>{
        return suspendCancellableCoroutine{ cancellableContinuation ->
            kotlin.runCatching {
                CoroutineScope(Dispatchers.IO).launch {
                   val data =  helper.addition_isCorrect(inputFile, dir)
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
     fun search(it:String){
        val ls = logData.filter { f->f.c?.contains(it)?:false||f.l?.contains(it)?:false }.toMutableList()
        if (ls.isNotEmpty()){
            uiState = HomeUIState.LogoDecodeSuccess(ls)
        }
    }
}