package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme

/**
 * Transition：多属性的状态切换
 * animateDpState 的缺点是没法设置初始值，只能设置目标值
 * updateTransition 也属于状态切换动画，默认也是不需要设置初始值的，但也可以强制设置初始值
 */
class TransitionChangeStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // val size by animateDpAsState(
                    //     if (big) 192.dp else 96.dp
                    // )
                    Box(modifier = Modifier.padding(innerPadding)) {
                        TransitionSquare()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TransitionSquare() {
    var big by remember {
        mutableStateOf(false)
    }

    /**
     * 初始化时会创建 Transition 对象
     * 重组时，会更新内部的对象状态，不会重复创建对象
     * 会开启一个协程，慢慢的渐变到目标状态 false - true
     * 可以展示动画的各个状态，比较方便调试
     * fun <T> updateTransition(
     *     targetState: T,
     *     label: String? = null
     * )
     * 参数 1：targetState
     * 参数 2：label
     */
    // val bigTransition = updateTransition(big, label = "big")

    /**
     * 需求：app 启动时，就启动动画，这时可以给 transition 设置一个初始值，一个目标值
     * 让其先执行一遍动画
     * 这里传入一个 MutableTransitionState，初始值为 false，targetState 为 true
     */
    var bigState = remember {
        MutableTransitionState(big)
    }
    bigState.targetState = !big
    val bigTransition = updateTransition(bigState, label = "big")

    /**
     * inline fun <S> Transition<S>.animateDp(
     *     noinline transitionSpec: @Composable Transition.Segment<S>.() -> FiniteAnimationSpec<Dp> = {
     *         spring(visibilityThreshold = Dp.VisibilityThreshold)
     *     },
     *     label: String = "DpAnimation",
     *     targetValueByState: @Composable (state: S) -> Dp
     * )
     * 需要实时的提供一个 FiniteAnimationSpec，不是无限循环的，无限循环的单独有一个 api
     * if (true isTransitioningTo false) 表示动画是否在从 true 到 false 过渡
     */
    val size by bigTransition.animateDp({ if (true isTransitioningTo false) spring() else tween(durationMillis = 1000) }, label = "size") {
        if (it) {
            192.dp
        } else 96.dp
    }
    val corner by bigTransition.animateDp(label = "corner") {
        if (it) {
            0.dp
        } else 30.dp
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(corner))
            .background(Color.Green)
            .clickable {
                big = !big
            }
    )
}

