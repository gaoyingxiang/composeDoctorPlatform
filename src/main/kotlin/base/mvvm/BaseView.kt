package base.mvvm

import androidx.compose.runtime.Composable
import utils.ViewModelManager

/**
 * 基础视图
 * 通过 ViewCompose 返回 Compose 函数
 */
abstract class BaseView<M:IModel>:IView {
    //添加 model
    val model:M = initState()
    private fun initState():M{
        val m = createModel()
        ViewModelManager.addModel(this::class.java,m)
        return m
    }
    abstract fun createModel():M
}

interface IView{
    @Composable
    fun viewCompose()
}

/**
 * 通过 ViewCompose 包裹，调用 viewCompose 返回 compose
 * 参数：返回的是一个基础自 IView 的 View,然后调用 viewCompose 返回 Compose 函数。
 */
@Composable
fun ViewCompose(view:()->IView){
    view().viewCompose()
}