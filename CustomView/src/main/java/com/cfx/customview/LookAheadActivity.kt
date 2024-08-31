package com.cfx.customview

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.cfx.customview.LookAheadActivity.Companion.TAG
import com.cfx.customview.ui.theme.SimpleComposeTheme
import kotlin.math.roundToInt

/**
 * 前瞻测量布局
 * 目的：不是为了二次测量，是为了实现一些过渡动画的效果
 */
class LookAheadActivity : ComponentActivity() {
    companion object {
        const val TAG = "LookAheadActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /**
                     * compose 布局中对子组件是不能二次测量的
                     * 会抛异常
                     * java.lang.IllegalStateException: measure() may not be called multiple times on the same Measurable.
                     * Current state InMeasureBlock. Parent state Measuring
                     */
                    // Layout({
                    //     Text("荒天帝")
                    // }) { measurables, constraints ->
                    //     val placeables = measurables.map { measurable ->
                    //         // 二次测量
                    //         measurable.measure(constraints)
                    //         measurable.measure(constraints)
                    //     }
                    //     val width = placeables.maxOf { it.width }
                    //     val height = placeables.maxOf { it.height }
                    //     layout(width, height) {
                    //         placeables.forEach { placeable ->
                    //             placeable.placeRelative(0, 0)
                    //         }
                    //     }
                    // }

                    /**
                     * 但是对于单个组件的修改，二次测量是不限制的
                     * 所以对于单个组件，直接使用 Modifier.layout {}
                     */
                    // Layout({
                    //     Text("荒天帝", Modifier.layout { measurable, constraints ->
                    //         // 单个组件测量两次
                    //         measurable.measure(constraints)
                    //         var placeable = measurable.measure(constraints)
                    //         layout(placeable.width, placeable.height) {
                    //             placeable.placeRelative(0, 0)
                    //         }
                    //
                    //     })
                    // }) { measurables, constraints ->
                    //     val placeables = measurables.map { measurable ->
                    //         measurable.measure(constraints)
                    //     }
                    //     val width = placeables.maxOf { it.width }
                    //     val height = placeables.maxOf { it.height }
                    //     layout(width, height) {
                    //         placeables.forEach { placeable ->
                    //             placeable.placeRelative(0, 0)
                    //         }
                    //     }
                    // }

                    Box(Modifier.padding(innerPadding)) {
                        // CustomLookaheadLayout1()
                        // CustomLookaheadLayout2()
                        // CustomLookaheadLayout3()
                        // CustomLookaheadLayout4()
                        // CustomLookaheadLayout5()
                        // CustomLookaheadLayout6()
                        CustomLookaheadLayout7()
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomLookaheadLayout1() {
    /**
     * 前瞻测量布局
     * 会先进行一次前瞻测量、布局
     * 再进行一轮测量、布局决定最终位置
     * 目的：
     */
    LookaheadLayout({
        Column {
            /**
             * 1.启动 Text 的 Layout.measure()
             * 2.第一次前瞻测量、布局会跳过 intermediateLayout
             * 3.在正式测量、布局时才会调用 intermediateLayout
             * 作用：可以拿到第一遍测量的结果再进行测量
             */
            Text("荒天帝", Modifier
                // .layout { measurable, constraints ->  }
                .intermediateLayout { measurable, constraints, lookaheadSize ->
                    // 将测量结果改为 2 倍
                    val placeable = measurable.measure(
                        Constraints.fixed(
                            lookaheadSize.width,
                            lookaheadSize.height * 2
                        )
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
                // .layout { measurable, constraints -> }
            )
            Text("火灵儿")
        }
    }) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val width = placeables.maxOf { it.width }
        val height = placeables.maxOf { it.height }
        layout(width, height) {
            placeables.forEach { placeable ->
                placeable.placeRelative(0, 0)
            }
        }
    }
}

/**
 * 上述的等价写法
 * 如果只是上述场景，根本不需要使用 LookaheadLayout 前瞻测量
 */
@Composable
fun CustomLookaheadLayout2() {
    Column {
        Text("荒天帝", Modifier
            .layout { measurable, constraints ->
                var placeable = measurable.measure(constraints)
                placeable =
                    measurable.measure(Constraints.fixed(placeable.width, placeable.height * 2))
                layout(placeable.width, placeable.height) {
                    placeable.placeRelative(0, 0)
                }
            }
        )
        Text("火灵儿")
    }
}

/**
 * 动画实现文字高度的过渡变化
 */
@Composable
fun CustomLookaheadLayout3() {
    var textHeight by remember {
        mutableStateOf(100.dp)
    }
    val textHeightAnim by animateDpAsState(textHeight)

    Column {
        Text(
            "荒天帝", Modifier
                .background(Color.Green)
                .height(textHeightAnim)
                .clickable {
                    textHeight = if (textHeight == 100.dp) 200.dp else 100.dp
                }

        )
        Text("火灵儿")
    }
}

/**
 * 动画实现文字高度的过渡变化
 * 需求：点击由原始尺寸高度扩大到两倍，不是指定的 100dp 200dp
 * 原始尺寸需要先测量才知道
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomLookaheadLayout4() {
    var textHeight by remember {
        mutableStateOf(100.dp)
    }
    val initTextHeightPx = with(LocalDensity.current) { 100.dp.toPx().roundToInt() }
    var textHeightPx by remember {
        mutableStateOf(initTextHeightPx)
    }
    val textHeightAnim by animateIntAsState(textHeightPx)

    Column {
        SimpleLookAheadLayout {
            Text(
                "荒天帝", Modifier
                    .background(Color.Green)
                    .intermediateLayout { measurable, constraints, lookaheadSize ->
                        // 2.重组后这里会重复进行测量、布局，且获取的 lookaheadSize.height 是相同的
                        Log.d(TAG, "htd lookaheadSize.height " + lookaheadSize.height)
                        if (textHeightPx != lookaheadSize.height) {
                            // 3.textHeightPx 的改变会引起动画的执行
                            textHeightPx = lookaheadSize.height
                        }
                        // 4.这里不断获取到动画的当前值进行测量、布局，最终设置为目标值 textHeight
                        val placeable = measurable.measure(
                            Constraints.fixed(
                                lookaheadSize.width,
                                textHeightAnim
                            )
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }
                    .height(textHeight)
                    .clickable {
                        // 1.点击时会触发 textHeight 的改变，引起重组
                        textHeight = if (textHeight == 100.dp) 200.dp else 100.dp
                    }
            )
        }
        Text("火灵儿")
    }
}

/**
 * 动画实现文字高度的过渡变化
 * 需求：点击由原始尺寸高度扩大到两倍，不是指定的 100dp 200dp
 * 原始尺寸和 200dp 间变化
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomLookaheadLayout5() {
    var isTextHeight200Dp by remember {
        mutableStateOf(false)
    }
    var textHeightPx by remember {
        mutableStateOf(0)
    }
    val textHeightAnim by animateIntAsState(textHeightPx)

    Column {
        SimpleLookAheadLayout {
            Text(
                "荒天帝", Modifier
                    .background(Color.Green)
                    /**
                     * intermediateLayout: 提供动画过程中的中间量
                     */
                    .intermediateLayout { measurable, constraints, lookaheadSize ->
                        // 2.重组后这里会重复进行测量、布局，且获取的 lookaheadSize.height 是相同的
                        Log.d(TAG, "htd lookaheadSize.height " + lookaheadSize.height)
                        if (textHeightPx != lookaheadSize.height) {
                            // 3.textHeightPx 的改变会引起动画的执行
                            textHeightPx = lookaheadSize.height
                        }
                        // 4.这里不断获取到动画的当前值进行测量、布局，最终设置为目标值 textHeight
                        val placeable = measurable.measure(
                            Constraints.fixed(
                                lookaheadSize.width,
                                textHeightAnim
                            )
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }
                    .then(if (isTextHeight200Dp) Modifier.height(200.dp) else Modifier)
                    .clickable {
                        // 1.点击时会触发 isTextHeight200Dp 的改变，引起重组
                        isTextHeight200Dp = !isTextHeight200Dp
                    }
            )
        }
        Text("火灵儿")
    }
}

/**
 * 动画实现文字高度的过渡变化
 * 需求：点击由原始尺寸高度扩大到两倍，不是指定的 100dp 200dp
 * 原始尺寸和 原始尺寸 5 倍高度间变化
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomLookaheadLayout6() {
    var isTextHeight200Dp by remember {
        mutableStateOf(false)
    }
    var textHeightPx by remember {
        mutableStateOf(0)
    }
    val textHeightAnim by animateIntAsState(textHeightPx)

    Column {
        SimpleLookAheadLayout {
            Text(
                "荒天帝", Modifier
                    .background(Color.Green)
                    /**
                     * intermediateLayout: 提供动画过程中的中间量
                     */
                    .intermediateLayout { measurable, constraints, lookaheadSize ->
                        // 2.重组后这里会重复进行测量、布局，且获取的 lookaheadSize.height 是相同的
                        Log.d(TAG, "htd lookaheadSize.height " + lookaheadSize.height)
                        // 3.textHeightPx 的改变会引起动画的执行
                        textHeightPx =
                            if (isTextHeight200Dp) lookaheadSize.height * 5 else lookaheadSize.height

                        // 4.这里不断获取到动画的当前值进行测量、布局，最终设置为目标值 textHeight
                        val placeable = measurable.measure(
                            Constraints.fixed(
                                lookaheadSize.width,
                                textHeightAnim
                            )
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }
                    .clickable {
                        // 1.点击时会触发 isTextHeight200Dp 的改变，引起重组
                        isTextHeight200Dp = !isTextHeight200Dp
                    }
            )
        }
        Text("火灵儿")
    }
}

/**
 * 动画实现文字高度的过渡变化
 * 需求：点击由原始尺寸高度扩大到两倍，不是指定的 100dp 200dp
 * 原始尺寸和 原始尺寸 5 倍高度间变化
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomLookaheadLayout7() {
    var isTextHeight200Dp by remember {
        mutableStateOf(false)
    }
    var textHeightPx by remember {
        mutableStateOf(0)
    }
    val textHeightAnim by animateIntAsState(textHeightPx)
    var lookAheadOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    val lookAheadOffAnim by animateOffsetAsState(lookAheadOffset)
    val shardText = remember {
        movableContentWithReceiverOf<LookaheadLayoutScope> {
            Text("火灵儿")
        }
    }
    Column {

        SimpleLookAheadLayout {
            Text(
                "荒天帝", Modifier
                    .then(if (isTextHeight200Dp) Modifier.padding(100.dp) else Modifier)
                    .background(Color.Green)
                    .onPlaced { layoutCoordinates ->
                        lookAheadOffset =
                            layoutCoordinates.localLookaheadPositionOf(layoutCoordinates)
                    }

                    // .onPlaced { lookaheadScopeCoordinates, layoutCoordinates ->
                    //     // 前瞻阶段的相对位置
                    //     var rel =
                    //         lookaheadScopeCoordinates.localLookaheadPositionOf(layoutCoordinates)
                    //     // 正式阶段的相对位置
                    //     var rel2 = lookaheadScopeCoordinates.localPositionOf(
                    //         layoutCoordinates,
                    //         Offset.Zero
                    //     )
                    // }
                    /**
                     * intermediateLayout: 提供动画过程中的中间量
                     */
                    .intermediateLayout { measurable, constraints, lookaheadSize ->
                        // 2.重组后这里会重复进行测量、布局，且获取的 lookaheadSize.height 是相同的
                        Log.d(TAG, "htd lookaheadSize.height " + lookaheadSize.height)
                        // 3.textHeightPx 的改变会引起动画的执行
                        textHeightPx =
                            if (isTextHeight200Dp) lookaheadSize.height * 10 else lookaheadSize.height

                        // 4.这里不断获取到动画的当前值进行测量、布局，最终设置为目标值 textHeight
                        val placeable = measurable.measure(
                            Constraints.fixed(
                                lookaheadSize.width,
                                textHeightAnim
                            )
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(
                                (lookAheadOffAnim - lookAheadOffset).x.roundToInt(),
                                (lookAheadOffAnim - lookAheadOffset).y.roundToInt()
                            )
                        }
                    }
                    .clickable {
                        // 1.点击时会触发 isTextHeight200Dp 的改变，引起重组
                        isTextHeight200Dp = !isTextHeight200Dp
                    }
            )
        }

    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleLookAheadLayout(content: @Composable LookaheadScope.() -> Unit) {
    LookaheadLayout(content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val width = placeables.maxOf { it.width }
        val height = placeables.maxOf { it.height }
        layout(width, height) {
            placeables.forEach { placeable ->
                placeable.placeRelative(0, 0)
            }
        }
    }
}