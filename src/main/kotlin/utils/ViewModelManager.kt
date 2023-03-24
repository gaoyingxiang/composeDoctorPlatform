package utils

import base.mvvm.IModel

/**
 * ViewModel 管理类
 */
object ViewModelManager {
    private val viewModelMaps = mutableMapOf<Class<*>, IModel>()

    /**
     * 添加Model
     */
    fun <M:IModel> addModel(view:Class<*>,model:M){
        if (viewModelMaps.containsKey(view)) {
            viewModelMaps[view] = viewModelMaps[view] ?: model
        } else {
            viewModelMaps[view] = model
        }
    }

    /**
     * 查找Model
     */
    fun <M : IModel> findModel(view: Class<*>): M? {
        if (!viewModelMaps.containsKey(view)) return null

        return viewModelMaps[view] as M
    }

    /**
     * 获取Model集合
     */
    fun getModelMaps(): Map<Class<*>, IModel> {
        return viewModelMaps
    }

    /**
     * 清除Model
     */
    fun clearModelMaps() {
        viewModelMaps.clear()
    }

}