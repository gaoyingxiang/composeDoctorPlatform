package view.other

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import base.mvvm.BaseView
import res.TextStyleRes
import vm.OtherViewModel

class OtherView : BaseView<OtherViewModel>() {
    override fun createModel(): OtherViewModel = OtherViewModel()

    @Composable
    override fun viewCompose() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("待开发", style = TextStyleRes.bodyLarge, modifier = Modifier.align(Alignment.Center))
        }
    }
}