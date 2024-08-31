package com.cfx.simplecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

/**
 * 1.和 DrawModifier 一样，PointInputModifier 是保存在离其右边最近的 LayoutModifier 中
 * 所以触摸范围也是 其右边最近的 LayoutModifier 的范围
 * 2.如果有多个 PointInputModifier 链接在一起，那么左边包裹着右边，左边是右边的父亲
 */
class PointInputModifierActivity : ComponentActivity() {
    companion object {
        const val TAG = "PointInputModifierActivity"
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Box(
                            Modifier
                                .padding(5.dp, top = 5.dp)
                                .size(100.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(Color.Red)
                                /**
                                 * 一般使用 combinedClickable
                                 * 底层也是调用了 detectTapGestures
                                 */
                                .combinedClickable(onClick = {
                                    Log.d(TAG, "htd onClick")
                                }, onLongClick = {
                                    Log.d(TAG, "htd onLongClick")
                                }, onDoubleClick = {
                                    Log.d(TAG, "htd onDoubleClick")
                                })
                        )

                        Box(
                            Modifier
                                .padding(5.dp, top = 20.dp)
                                .size(100.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(Color.Green)
                                .pointerInput(Unit) {
                                    /**
                                     * 更底层的方法，一般不用
                                     */
                                    detectTapGestures(onTap = {
                                        Log.d(TAG, "htd onTap")
                                    }, onLongPress = {
                                        Log.d(TAG, "htd onLongPress")
                                    }, onDoubleTap = {
                                        Log.d(TAG, "htd onDoubleTap")
                                    }, onPress = {
                                        // 按下操作监听
                                        Log.d(TAG, "htd onPress")
                                    })
                                }
                        )

                        Box(
                            Modifier
                                .padding(5.dp, top = 20.dp)
                                .size(100.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(Color.Blue)
                                .pointerInput(Unit) {
                                    /**
                                     * 循环监听手势 awaitPointerEventScope
                                     * 比 detectTapGestures 更底层的方法
                                     */
                                    forEachGesture {
                                        awaitPointerEventScope {
                                            // 获取按下事件
                                            val down = awaitFirstDown()
                                            Log.d(TAG, "htd down: $down")
                                        }
                                    }

                                }
                        )
                    }
                }
            }
        }
    }
}

