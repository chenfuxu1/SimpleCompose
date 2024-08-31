package com.cfx.customview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.cfx.customview.CustomTouchDemoActivity.Companion.TAG
import com.cfx.customview.ui.theme.SimpleComposeTheme

class CustomTouchDemoActivity : ComponentActivity() {
    companion object {
        const val TAG = "CustomTouchDemoActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        // CustomTouchDemo()
                        // CustomClick()
                        CustomClick2()
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTouchDemo() {
    Text("荒天帝", Modifier.pointerInput(Unit) {
        awaitPointerEventScope {
            var event = awaitPointerEvent()
            Log.d(TAG, "htd event.type: ${event.type}") // event.type: Press 按下事件

            event = awaitPointerEvent()
            Log.d(TAG, "htd event.type: ${event.type}") // event.type: Release 抬起事件

        }
    })
}

/**
 * 自定义点击事件
 */
@Composable
fun CustomClick() {
    Text("荒天帝", Modifier.htdClick {
        Log.d(TAG, "htd 点击了")
    })
}

/**
 * 自定义点击事件
 * 按下后移动，超过控件范围，那么不响应点击事件
 */
@Composable
fun CustomClick2() {
    Text("荒天帝", Modifier.offset {
        IntOffset(100, 100)
    }.htdClick2 {
        Log.d(TAG, "htd 点击了")
    })
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
private fun Modifier.htdClick(onClick: () -> Unit) = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            Log.d(TAG, "htd event.type: ${event.type}")
            if (PointerEventType.Release == event.type) {
                onClick()
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
private fun Modifier.htdClick2(onClick: () -> Unit) = pointerInput(Unit) {
    awaitEachGesture {
        // 第一个一定是按下事件
        val down = awaitPointerEvent()
        while (true) {
            val event = awaitPointerEvent()
            Log.d(TAG, "htd event.type: ${event.type}")
            if (PointerEventType.Move == event.type) {
                val pos = event.changes[0].position // changes[0] 表示按下的这个手指触点
                if (pos.x < 0 || pos.x > size.width || pos.y < 0 || pos.y > size.height) {
                    break
                }
            } else if (PointerEventType.Release == event.type) {
                onClick()
                break
            }

        }

        /**
         * compose 和原生 view 的区别：
         * 原生 view：触摸事件在父 view -> 是否拦截，拦截就父 view 消费，不拦截就子 view 消费
         * compose：父组件 -> 子组件，子组件 -> 父组件，再由 父组件 -> 子组件，没有拦截，只有记录消费的标记
         * 其中嵌套滑动是 nestedScroll 处理的，事件在子组件发生 -> 父组件是否消费 -> 子组件消费 -> 父组件消费子组件未消费完的事件
         *
         * 1.使用完事件后，需要消费掉
         * 2.对于一个事件，先检查有没有被消费
         *
         * enum class PointerEventPass {
         *     Initial, Main, Final
         * }
         * 对应上述三个阶段
         */
        val down1 = awaitPointerEvent(PointerEventPass.Initial)
        val down2 = awaitPointerEvent(PointerEventPass.Main)
        val down3 = awaitPointerEvent(PointerEventPass.Final)
        down2.changes[0].isConsumed // 判断是否消费了
        down2.changes[0].consume() // 表示消费
    }
}
