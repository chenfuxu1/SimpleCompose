package com.cfx.customview

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.customview.CustomTouchActivity.Companion.TAG
import com.cfx.customview.ui.theme.SimpleComposeTheme
import kotlin.math.roundToInt

/**
 * 嵌套滑动
 */
class CustomNestedScrollActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // LargeTopAppBar(title = { /*TODO*/ }, scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior())

                    Box(Modifier.padding(innerPadding)) {
                        // NestedScrollDemo()
                        NestedScrollDemo2()
                    }
                }
            }
        }
    }
}

@Composable
private fun NestedScrollDemo() {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.Red)
            ) {
                items(8) {
                    Text(
                        "第一部分：${it + 1}",
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp), fontSize = 26.sp, textAlign = TextAlign.Center
                    )
                }
            }
        }

        items(20) {
            Text(
                "第二部分：${it + 1}",
                Modifier
                    .background(Color.Green)
                    .fillMaxWidth()
                    .padding(8.dp),
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun NestedScrollDemo2() {
    var offsetY by remember {
        mutableStateOf(0f)
    }
    val dispatcher = remember {
        NestedScrollDispatcher()
    }
    val connection = remember {
        object: NestedScrollConnection {
            /**
             * 作为父组件，提供子组件调用的方法
             * 可以处理自己的子组件滑动之前，可以继续处理未消费的距离
             */
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return super.onPreScroll(available, source)
            }

            /**
             * 作为父组件，提供子组件调用的方法
             * 可以处理自己的子组件滑动之后，可以继续处理未消费的距离
             */
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                Log.d(TAG, "htd available.y: " + available.y)
                offsetY += available.y
                return available
            }
        }
    }
    Column(Modifier
        .offset { IntOffset(0, offsetY.roundToInt()) }
        .draggable(rememberDraggableState {
            /**
             * 滑动前
             * dispatchPreScroll(available: Offset, source: NestedScrollSource)
             * available：告诉父组件的滑动距离
             * source: 来源，是来自触摸滑动还是惯性滑动
             * consumed: 表示父组件消费的距离
             */
            val consumed =
                dispatcher.dispatchPreScroll(
                    Offset(0f, it),
                    NestedScrollSource.Drag
                ) // 会调用父组件 自身可以消费距离就等于 it - 父组件消费的距离
            val selfConsumed = it - consumed.y
            Log.d(TAG, "htd consumed.y: " + consumed.y + " selfConsumed: " + selfConsumed)

            offsetY += selfConsumed
            /**
             * 滑动后
             * fun dispatchPostScroll(
             *     consumed: Offset, 自身组件消耗的距离
             *     available: Offset, 告诉父组件的滑动距离
             *     source: NestedScrollSource
             * )
             * 这里自身组件会消耗完所有的距离，所以传给父组件的距离是 Offset(0f, 0f)
             */
            dispatcher.dispatchPostScroll(
                Offset(0f, selfConsumed),
                Offset(0f, 0f),
                NestedScrollSource.Drag
            ) // 会调用父组件
        }, Orientation.Vertical)
        /**
         * 1.滑动前，先问自己的父组件要不要先消费滑动距离
         * 2.如果父组件不消费，或者没有消费完，自己才进行消费
         * 3.如果自己没有消费完，再询问第二次父组件要不要消费
         *
         * I   作为子组件在滑动前调用父组件的回调( 滑动前、滑动后都需要调用)
         * II  同时自身也作为父组件，处理来自自己子组件的回调
         * III 作为整个链条的一环，自身也作为父组件时，在子组件回调时，还要调用下自身的父组件的回调 (nestedScroll 处理了)
         *
         * 如果本身不需要接收触摸事件，可以不填 dispatcher
         */
        .nestedScroll(connection, dispatcher)
    ) {
        for (i in 1..10) {
            Text(
                "第一部分：$i",
                Modifier
                    .background(Color.Green)
                    .fillMaxWidth()
                    .padding(8.dp),
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
        }

        // 子组件
        LazyColumn(Modifier.height(500.dp)) {
            items(10) {
                Text(
                    "第二部分：$it",
                    Modifier
                        .background(Color.Red)
                        .fillMaxWidth()
                        .padding(8.dp),
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}