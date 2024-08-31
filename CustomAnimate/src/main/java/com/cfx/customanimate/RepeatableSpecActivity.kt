package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme

/**
 * 重复执行动画
 */
class RepeatableSpecActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                var big by remember {
                    mutableStateOf(false)
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val size = remember(big) {
                        if (big) 96.dp else 48.dp
                    }

                    val anim = remember {
                        Animatable(size, Dp.VectorConverter)
                    }

                    LaunchedEffect(key1 = big) {
                        /**
                         * 重复执行
                         * repeatable(
                         *     iterations: Int,
                         *     animation: DurationBasedAnimationSpec<T>,
                         *     repeatMode: RepeatMode = RepeatMode.Restart,
                         *     initialStartOffset: StartOffset = StartOffset(0)
                         * )
                         * iterations 重复执行次数
                         * animation 重复执行的动画，只能执行 KeyframesSpec、SnapSpec、TweenSpec 这三种，其他的容易递归
                         * repeatMode 重复的模式，默认是重新开始，如果设置为 RepeatMode.Reverse，且 iterations 设置为偶数，动画会有问题，回到初始位置
                         * initialStartOffset 时间的偏移量, offsetMillis 表示延时 500ms 后，执行，如果加了 StartOffsetType，表示直接快进到 500ms 后执行
                         */
                        // anim.animateTo(size, repeatable(3, tween(), RepeatMode.Reverse, StartOffset(500, StartOffsetType.FastForward)))

                        /**
                         * infiniteRepeatable 无限循环
                         * 和 repeatable 区别是不需要设置次数
                         */
                        anim.animateTo(size, infiniteRepeatable(tween(), RepeatMode.Reverse))
                    }

                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.Green)
                            .size(anim.value)
                            .clickable {
                                big = !big
                            }
                    )
                }
            }
        }
    }
}

