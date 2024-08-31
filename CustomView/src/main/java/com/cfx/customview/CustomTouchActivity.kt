package com.cfx.customview

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.cfx.customview.CustomTouchActivity.Companion.TAG
import com.cfx.customview.ui.theme.SimpleComposeTheme
import kotlin.math.roundToInt

/**
 * 自定义触摸
 */
class CustomTouchActivity : ComponentActivity() {
    companion object {
        const val TAG = "CustomTouchActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        // Draggable1()
                        // Draggable2()


                        // val listState = rememberLazyListState()
                        // LazyColumn(state = listState) {
                        //     items(listOf(1, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 51, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5)) {
                        //         Text("number: $it")
                        //     }
                        // }
                        //
                        // val scope = rememberCoroutineScope()
                        // Button(onClick = {
                        //     scope.launch {
                        //         listState.animateScrollToItem(2)
                        //     }
                        // }) {
                        //
                        // }

                        // Modifier.scrollable()
                    }
                }
            }
        }
    }
}

@Composable
private fun Draggable1() {
    /**
     * fun Modifier.draggable(
     *     state: DraggableState, 滑动状态
     *     orientation: Orientation, 方向
     *     enabled: Boolean = true, 判断 modifier 是否生效，可以动态调整
     *     interactionSource: MutableInteractionSource? = null, 触摸状态监控
     *     startDragImmediately: Boolean = false, 是否需要立即响应拖动，默认 false，防止轻微触摸导致列表等滑动
     *     onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit = {}, 拖动开始
     *     onDragStopped: suspend CoroutineScope.(velocity: Float) -> Unit = {}, 拖动结束
     *     reverseDirection: Boolean = false 倒转滑动方向
     * )
     */
    val interactionSource = remember { MutableInteractionSource() } // 触摸状态监控

    // rememberDraggableState 表示一维方向上的滑动
    Text("换图垫底放大是否会", Modifier.draggable(rememberDraggableState {
        Log.d(CustomTouchActivity.TAG, "htd 滑动了 $it 个像素")
    }, Orientation.Horizontal, interactionSource = interactionSource))

    /**
     *  interactionSource.collectIsFocusedAsState()
     *  interactionSource.collectIsHoveredAsState()
     *  interactionSource.collectIsPressedAsState()
     */
    val isDragged by interactionSource.collectIsDraggedAsState() // 获取是否处于拖动状态
    Text(if (isDragged) "拖动中" else "静止")
}

@Composable
private fun Draggable2() {
    val interactionSource = remember { MutableInteractionSource() } // 触摸状态监控
    var offsetX by remember { mutableStateOf(0f) }

    // rememberDraggableState 表示一维方向上的滑动
    Text("换图垫底放大是否会", Modifier
        .offset { IntOffset(offsetX.roundToInt(), 0) }
        .draggable(rememberDraggableState {
            offsetX += it
            Log.d(CustomTouchActivity.TAG, "htd 滑动了 $it 个像素")
        }, Orientation.Horizontal, interactionSource = interactionSource)
    )

    val isDragged by interactionSource.collectIsDraggedAsState() // 获取是否处于拖动状态
    Text(if (isDragged) "拖动中" else "静止")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Scrollable1() {
    /**
     * Modifier.verticalScroll()
     * Modifier.horizontalScroll()
     * 惯性滑动
     * 嵌套滑动
     * 滑动触边效果 overscroll
     * draggable 是提供基本的一维的滑动，scrollable 是提供上述特殊场景的滑动
     *
     * fun Modifier.scrollable(
     *     state: ScrollableState,
     *     orientation: Orientation,
     *     enabled: Boolean = true,
     *     reverseDirection: Boolean = false,
     *     flingBehavior: FlingBehavior? = null, 默认不填，会提供一个 ScrollableDefaults.flingBehavior() 惯性滑动实现
     *     interactionSource: MutableInteractionSource? = null
     * )
     * overscrollEffect = ScrollableDefaults.overscrollEffect() 表示触边效果
     */
    Modifier.scrollable(rememberScrollableState {
        Log.d(TAG, "htd 滚动了 $it 个像素")
        it// 返回值表示告诉父组件自己消费了多少距离
    }, Orientation.Horizontal, overscrollEffect = ScrollableDefaults.overscrollEffect())
    // SwipeToDismiss(state = , background = , dismissContent = ) // 滑动删除
}