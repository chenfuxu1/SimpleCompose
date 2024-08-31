package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme

class AnimateDpAsStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var big by remember {
                mutableStateOf(false)
            }
            SimpleComposeTheme {
                /**
                 * animateDpAsState 返回的是 State 对象，State 内部对象是不能手动修改的
                 * 所以，不能 setValue，因此不能用 var 修饰，要用 val
                 * 而 MutableState 对象的 value 是可以手动修改的
                 * animateDpAsState 启动协程会由当前值渐变到目标值，并不断刷新界面
                 *
                 * 缺点：不能设置初始值，只能设置最终的目标值
                 */
                val size by animateDpAsState(if (big) 96.dp else 48.dp) // State

                Box(modifier = Modifier
                    .padding(10.dp, 50.dp)
                    .size(size)
                    .background(Color.Green)
                    .clickable {
                        big = !big
                    })
            }
        }
    }
}