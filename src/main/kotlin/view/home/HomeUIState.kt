package view.home

import log.LoganLogItem

sealed class HomeUIState(){
    object LogoDecodeLoading:HomeUIState()
    class LogoDecodeSuccess(val data:MutableList<LoganLogItem>):HomeUIState()
    class LogoDecodeFail(message:String):HomeUIState()
    class LogoDecodeSearchEmpty(message:String):HomeUIState()
}
