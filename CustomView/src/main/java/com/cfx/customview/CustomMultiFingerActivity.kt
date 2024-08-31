package com.cfx.customview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.cfx.customview.ui.theme.SimpleComposeTheme

/**
 * 多指手势
 */
class CustomMultiFingerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {

                    }
                }
            }
        }
    }
}

@Composable
fun MultiFinger() {
    Modifier.pointerInput(Unit) {
        /**
         * suspend fun PointerInputScope.detectTransformGestures(
         *     panZoomLock: Boolean = false, 如果打开，那么移动放缩，旋转不会同时识别
         *     onGesture: (centroid: Offset, pan: Offset, zoom: Float, rotation: Float) -> Unit
         * )
         *
         * centroid: 监测的几个触摸点的中心点坐标
         * pan：位移参数，这一瞬间和上一瞬间的偏移量（多个手指中心点的位移）
         * zoom：放缩倍数，这一瞬间和上一瞬间的放缩倍数（放缩中心）
         * rotation：旋转角度，这一瞬间和上一瞬间的旋转角度（旋转轴心）
         */
        detectTransformGestures { centroid, pan, zoom, rotation ->

        }
        
        detectDragGestures { change, dragAmount ->  }
    }
}