package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import base.mvvm.BaseViewModel
import utils.ViewModelManager
import view.home.HomeUIState
import view.home.HomeView

class MainModel:BaseViewModel() {

//    val homeViewModel = ViewModelManager.findModel<HomeViewModel>(HomeView::class.java)
//    companion object{
//        const val LOGIN_PAGE = 1
//        const val MAIN_PAGE = 2
//    }
//    var page by mutableStateOf(LOGIN_PAGE)
//
//
//    fun search(it: String) {
//        val ls = homeViewModel?.logData?.filter { f->f.c?.contains(it)?:false||f.l?.contains(it)?:false }?: mutableListOf()
//        if (ls.isNotEmpty()){
//            homeViewModel?.uiState = HomeUIState.LogoDecodeSuccess(ls.toMutableList())
//        }
//    }
}