package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme
import kotlinx.coroutines.delay

/**
 * 消散型动画 - 惯性滑动
 */
class AnimateDecayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val anim = remember {
                        Animatable(0.dp, Dp.VectorConverter)
                    }

                    /**
                     * 1.splineBasedDecay<Dp>(LocalDensity.current) 样条形衰减
                     * splineBasedDecay<Dp>(LocalDensity.current) 需要填入 density 像素密度，是面向像素的
                     * 不要使用 splineBasedDecay 泛型传入 Dp，因为内部有修正，但 Dp、角度等是不需要修正的，会因为像素密度高的设备修正的更厉害
                     * 像素密度低的设备修正的少一些，导致结果错误，会在像素密度高的设备摩擦力更大一些，同样的速度，惯性滑动的距离更小
                     *
                     * 2.rememberSplineBasedDecay<Dp>() 可以用来设置 dp 类型的动画
                     *
                     * 3.exponentialDecay<Dp>() 指数型衰减，面向于除了像素之外的其他场景
                     * exponentialDecay(
                     *     /*@FloatRange(
                     *         from = 0.0,
                     *         fromInclusive = false
                     *     )*/
                     *     frictionMultiplier: Float = 1f,
                     *     /*@FloatRange(
                     *         from = 0.0,
                     *         fromInclusive = false
                     *     )*/
                     *     absVelocityThreshold: Float = 0.1f
                     * )
                     * 参数 1：frictionMultiplier 摩擦力系数，值越大，摩擦力越大，滑动的距离越短
                     * 参数 2：absVelocityThreshold 速度阈值，默认值为 0.1，小于该值，动画就停止了
                     * 不会针对像素密度去进行修正，因此如果用来设置像素型的衰减动画会有问题
                     * 因此直接设置成 dp 即可，像位移、颜色等都不需要修正，都应该用 exponentialDecay
                     */
                    // splineBasedDecay<Dp>(LocalDensity.current)
                    val decay = rememberSplineBasedDecay<Dp>()
                    val exponenDecay = remember {
                        exponentialDecay<Dp>(2f)
                    }

                    LaunchedEffect(Unit) {
                        delay(2000)
                        /**
                         * animateDecay(
                         *     initialVelocity: T,
                         *     animationSpec: DecayAnimationSpec<T>,
                         *     block: (Animatable<T, V>.() -> Unit)? = null
                         * )
                         * initialVelocity 初始的速度
                         * animationSpec 衰减的 DecayAnimationSpec
                         */
                        // anim.animateDecay(2000.dp, decay)

                        anim.animateDecay(2000.dp, exponenDecay) // 指数型衰减
                    }

                    Box(modifier = Modifier.padding(innerPadding)) {
                        Box(
                            modifier = Modifier
                                .padding(0.dp, anim.value, 0.dp, 0.dp)
                                .background(Color.Green)
                                .size(96.dp)
                        )
                    }

                }
            }
        }
    }
}

