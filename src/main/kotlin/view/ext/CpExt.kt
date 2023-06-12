package view.ext

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
const val VIEW_CLICK_INTERVAL_TIME = 800
/**
 * 防抖
 * 并默认去点点击阴影。
 */
@Composable
inline fun Modifier.click(
    time: Int = VIEW_CLICK_INTERVAL_TIME,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    crossinline onClick: () -> Unit
): Modifier {
    var lastClickTime  =  remember {
        mutableStateOf(value = 0L)
    }
    return clickable(interactionSource, indication, enabled, onClickLabel, role) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime.value) {
            onClick()
            lastClickTime.value = currentTimeMillis
        }
    }
}