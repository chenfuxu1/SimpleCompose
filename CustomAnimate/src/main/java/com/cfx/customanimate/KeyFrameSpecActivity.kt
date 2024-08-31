package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
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
 * 关键帧
 * 类似于多段 TweenSpec
 * 复用性较差，可以使用多段的 animateTo 代替
 */
class KeyFrameSpecActivity : ComponentActivity() {
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
                        anim.animateTo(size, keyframes {
                            /**
                             * 设置关键帧
                             * 动画的默认时长是 300ms
                             * 在 150ms 设置一帧大小为 192dp
                             */
                            48.dp at 0 with FastOutLinearInEasing // 表示从 0 开始到 150ms 这一段速度曲线 FastOutLinearInEasing
                            192.dp at 150 with FastOutSlowInEasing // 表示从 150ms 到 300ms 这一段速度曲线，如果 with 不填，默认是匀速曲线 LinearEasing
                            20.dp at 300 // 动画结束前设置了一帧
                            durationMillis = 500 // 设置动画的时长为 500ms
                            delayMillis = 1000 // 延时 1000ms 后才执行动画
                        })
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