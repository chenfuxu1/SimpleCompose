package com.cfx.customanimate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme
import kotlinx.coroutines.delay

/**
 * 动画的边界限制、结束和取消
 * 1.新动画会主动打断旧动画，抛 CancellationException 异常
 */
class StopAnimateActivity : ComponentActivity() {
    companion object {
        const val TAG = "StopAnimateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BoxWithConstraints {
                        val animX = remember {
                            Animatable(0.dp, Dp.VectorConverter)
                        }
                        val animY = remember {
                            Animatable(0.dp, Dp.VectorConverter)
                        }

                        val decay = rememberSplineBasedDecay<Dp>()
                        val exponenDecay = remember {
                            exponentialDecay<Dp>(1f)
                        }

                        /**
                         * 1.新动画会主动打断旧动画
                         */
                        // LaunchedEffect(Unit) {
                        //     delay(2000)
                        //     try {
                        //         anim.animateDecay(2000.dp, exponenDecay) // 指数型衰减
                        //     } catch (e: CancellationException) {
                        //         Log.d(TAG, "htd 被打断了")
                        //     }
                        // }
                        //
                        // LaunchedEffect(Unit) {
                        //     delay(2500)
                        //     anim.animateDecay((-1000).dp, exponenDecay) // 指数型衰减
                        // }

                        // Box(modifier = Modifier.padding(innerPadding)) {
                        //     Box(
                        //         modifier = Modifier
                        //             .padding(0.dp, anim.value, 0.dp, 0.dp)
                        //             .background(Color.Green)
                        //             .size(96.dp)
                        //     )
                        // }

                        /**
                         * 2.stop 函数
                         * animateDecay 和 stop 都是挂起函数，需要放在不同的协程中执行
                         * 如果在同一个协程中，会因先后调用关系而等待，后调用的要等先调用的执行结束才会执行
                         */
                        // LaunchedEffect(Unit) {
                        //     delay(2000)
                        //     try {
                        //         anim.animateDecay(2000.dp, exponenDecay) // 指数型衰减
                        //     } catch (e: CancellationException) {
                        //         Log.d(TAG, "htd 被 stop 打断了")
                        //     }
                        // }
                        //
                        // LaunchedEffect(Unit) {
                        //     delay(2500)
                        //     anim.stop()
                        // }

                        // Box(modifier = Modifier.padding(innerPadding)) {
                        //     Box(
                        //         modifier = Modifier
                        //             .padding(0.dp, anim.value, 0.dp, 0.dp)
                        //             .background(Color.Green)
                        //             .size(96.dp)
                        //     )
                        // }

                        /**
                         * 3.动画的边界
                         * upperBound 设置的是方块的左边的最大边界是屏幕宽度 - 方块的宽度
                         * 动画撞到边界会停止，属于自然停止，不会抛异常
                         * 像前两种动画打断会抛出异常，停止协程，协程中 anim.animateDecay(2000.dp, exponenDecay) 后续的代码就不会执行了
                         */
                        // LaunchedEffect(Unit) {
                        //     delay(2000)
                        //     animX.animateDecay(2000.dp, exponenDecay)
                        // }
                        // animX.updateBounds(upperBound = maxWidth - 96.dp)
                        //
                        // Box(modifier = Modifier.padding(innerPadding)) {
                        //     Box(
                        //         modifier = Modifier
                        //             .padding(animX.value, 0.dp, 0.dp, 0.dp)
                        //             .background(Color.Green)
                        //             .size(96.dp)
                        //     )
                        // }

                        /**
                         * 4.动画的边界 - 二维
                         */
                        // LaunchedEffect(Unit) {
                        //     delay(2000)
                        //     animX.animateDecay(2000.dp, exponenDecay)
                        // }
                        // LaunchedEffect(Unit) {
                        //     delay(2000)
                        //     animY.animateDecay(2000.dp, exponenDecay)
                        // }
                        // animX.updateBounds(upperBound = maxWidth - 96.dp)
                        // animY.updateBounds(upperBound = maxHeight - 96.dp)
                        //
                        // Box(modifier = Modifier.padding(innerPadding)) {
                        //     Box(
                        //         modifier = Modifier
                        //             .padding(animX.value,animY.value, 0.dp, 0.dp)
                        //             .background(Color.Green)
                        //             .size(96.dp)
                        //     )
                        // }

                        /**
                         * 5.动画的边界 - 反弹效果
                         */
                        // LaunchedEffect(Unit) {
                        //     delay(2000)
                        //     // 动画达到边界时，拿到当前的速度
                        //     var result = animX.animateDecay(5000.dp, exponenDecay)
                        //     while (result.endReason == AnimationEndReason.BoundReached) {
                        //         // 根据当前的速度，发射一个反方向的动画
                        //         result = animX.animateDecay(-result.endState.velocity, decay)
                        //     }
                        //
                        // }
                        // LaunchedEffect(Unit) {
                        //     delay(2000)
                        //     animY.animateDecay(2500.dp, exponenDecay)
                        // }
                        // animX.updateBounds(0.dp, upperBound = maxWidth - 96.dp)
                        // animY.updateBounds(upperBound = maxHeight - 96.dp)
                        //
                        // Box(modifier = Modifier.padding(innerPadding)) {
                        //     Box(
                        //         modifier = Modifier
                        //             .padding(animX.value, animY.value, 0.dp, 0.dp)
                        //             .clip(RoundedCornerShape(20.dp))
                        //             .background(Color.Green)
                        //             .size(96.dp)
                        //     )
                        // }

                        /**
                         * 6.动画的边界 - 自己计算反弹的距离
                         * 速度不准时，可以用该种方式，自己计算反弹的距离
                         */
                        val paddingX = remember(animX.value) {
                            Log.d(TAG, "htd animX.value " + animX.value)
                            var usedValue = animX.value
                            while (usedValue >= (maxWidth - 96.dp) * 2) {
                                usedValue -= (maxWidth - 96.dp) * 2
                            }
                            if (usedValue < maxWidth - 96.dp) {
                                usedValue
                            } else {
                                (maxWidth - 96.dp) * 2 - usedValue
                            }
                        }
                        LaunchedEffect(Unit) {
                            delay(2000)
                            // 动画达到边界时，拿到当前的速度
                            var result = animX.animateDecay(5000.dp, exponenDecay)
                            while (result.endReason == AnimationEndReason.BoundReached) {
                                // 根据当前的速度，发射一个反方向的动画
                                result = animX.animateDecay(-result.endState.velocity, decay)
                            }

                        }
                        LaunchedEffect(Unit) {
                            delay(2000)
                            animY.animateDecay(2500.dp, exponenDecay)
                        }
                        animX.updateBounds(0.dp, upperBound = maxWidth - 96.dp)
                        animY.updateBounds(upperBound = maxHeight - 96.dp)

                        Box(modifier = Modifier.padding(innerPadding)) {
                            Box(
                                modifier = Modifier
                                    .padding(paddingX, animY.value, 0.dp, 0.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.Green)
                                    .size(96.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}