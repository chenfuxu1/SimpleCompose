package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme

/**
 * FastOutSlowInEasing 等价于属性动画 AccelerateDecelerateInterpolator 先加速再减速
 * LinearOutSlowInEasing 等价于属性动画 DecelerateInterpolator 减速插值器，适用于元素的出现
 * FastOutLinearInEasing 等价于属性动画 AccelerateInterpolator 加速插值器，适用于元素的出场 / 消失动画
 * LinearEasing 等价于属性动画 LinearInterpolator 线性插值器，匀速
 */
class TweenSpecActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var big by remember {
                mutableStateOf(false)
            }
            SimpleComposeTheme {
                // Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                //     val size = remember(big) {
                //         if (big) 192.dp else 48.dp
                //     }
                //
                //     val anim = remember {
                //         Animatable(size, Dp.VectorConverter)
                //     }
                //
                //     LaunchedEffect(key1 = big) {
                //         // 1.FastOutSlowInEasing 先加速再减速
                //         // anim.animateTo(size, TweenSpec(easing = FastOutSlowInEasing))
                //
                //         // 2.LinearOutSlowInEasing 减速插值器
                //         // anim.animateTo(size, TweenSpec(easing = LinearOutSlowInEasing))
                //
                //         // 3.FastOutLinearInEasing 加速插值器
                //         // anim.animateTo(size, TweenSpec(easing = FastOutLinearInEasing))
                //
                //         // 4.LinearEasing 线性插值器
                //         anim.animateTo(size, TweenSpec(easing = LinearEasing))
                //     }
                //
                //     Box(
                //         modifier = Modifier
                //             .padding(innerPadding)
                //             .background(Color.Green)
                //             .size(anim.value)
                //             .clickable {
                //                 big = !big
                //             }
                //     )
                // }

                // 元素的出场 / 消失动画
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val offset = remember(big) {
                        if (big) (100).dp else 300.dp
                    }

                    val offsetAnim = remember {
                        Animatable(offset, Dp.VectorConverter)
                    }

                    LaunchedEffect(key1 = big) {
                        // FastOutLinearInEasing 加速插值器
                        // offsetAnim.animateTo(offset, TweenSpec(easing = FastOutLinearInEasing))

                        /**
                         * LinearEasing: Easing = Easing { fraction -> fraction }
                         * 动画的时间完成度和动画完成度相等，就是线性插值器
                         */
                        // offsetAnim.animateTo(offset, TweenSpec(easing = Easing { it }))

                        /**
                         * 可以通过 CubicBezierEasing 自定义贝塞尔曲线
                         * val FastOutSlowInEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                         * val LinearOutSlowInEasing: Easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
                         * val FastOutLinearInEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)
                         * val LinearEasing: Easing = Easing { fraction -> fraction }
                         * 需要提供两个点的坐标，横坐标为时间完成度，纵坐标为动画完成度
                         */
                        // offsetAnim.animateTo(offset, TweenSpec(easing = CubicBezierEasing(0f, 1.47f, 0.38f, 1.6f)))

                        // 等价写法
                        offsetAnim.animateTo(offset, tween(easing = CubicBezierEasing(0f, 1.47f, 0.38f, 1.6f)))
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .offset(offsetAnim.value, 200.dp)
                                .padding(innerPadding)
                                .background(Color.Green)
                                .size(48.dp)
                                .clickable {
                                    big = !big
                                }
                        )
                    }

                }
            }
        }
    }
}