package com.cfx.customview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.cfx.customview.ui.theme.SimpleComposeTheme

/**
 * 二维滑动监测
 */
class CustomTwoDimenScrollActivity : ComponentActivity() {
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
fun CustomTwoDimenScroll() {
   Modifier.pointerInput(Unit) {
       /**
        * suspend fun PointerInputScope.detectDragGestures(
        *     onDragStart: (Offset) -> Unit = { },
        *     onDragEnd: () -> Unit = { },
        *     onDragCancel: () -> Unit = { },
        *     onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit
        * )
        * change: 滑动中手指的触摸点信息
        * dragAmount: 移动的距离，二维的
        */
        detectDragGestures { change, dragAmount ->

        }

       // 长按之后的触摸滑动
       detectDragGesturesAfterLongPress { change, dragAmount ->  }
   }

    val draggableState = rememberDraggableState {

    }
    Modifier.draggable(draggableState, Orientation.Horizontal)
    LaunchedEffect(Unit) {
        draggableState.drag {
            dragBy(100f) // 拖动 100 像素
        }
    }
}