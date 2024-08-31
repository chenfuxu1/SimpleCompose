package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme

/**
 * AnimatedContent 两个组件的出场 / 入场动画
 * 同时又能对动画做定制
 * 相当于 Crossfade 和 AnimatedVisibility 的结合
 */
class TransitionAnimatedContentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                MyAnimatedContent()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
private fun MyAnimatedContent() {
    Column(Modifier.padding(30.dp)) {
        var show by remember {
            mutableStateOf(true)
        }

        /**
         * fun <S> AnimatedContent(
         *     targetState: S,
         *     modifier: Modifier = Modifier,
         *     transitionSpec: AnimatedContentTransitionScope<S>.() -> ContentTransform = {
         *         (fadeIn(animationSpec = tween(220, delayMillis = 90)) +
         *             scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)))
         *             .togetherWith(fadeOut(animationSpec = tween(90)))
         *     },
         *     contentAlignment: Alignment = Alignment.TopStart,
         *     label: String = "AnimatedContent",
         *     contentKey: (targetState: S) -> Any? = { it },
         *     content: @Composable() AnimatedContentScope.(targetState: S) -> Unit
         * )
         *
         * 默认动画效果:
         * 1.先是 90ms 的 fadeOut(animationSpec = tween(90)) 淡出效果
         * 2.然后是 220ms 的淡入和放缩效果叠加
         *
         * class ContentTransform(
         *     val targetContentEnter: EnterTransition,
         *     val initialContentExit: ExitTransition,
         *     targetContentZIndex: Float = 0f,
         *     sizeTransform: SizeTransform? = SizeTransform()
         * )
         * targetContentEnter：目标组件的入场动画
         * initialContentExit：初始组件的出场动画
         * targetContentZIndex：配置遮盖关系 - 入场在上还是出场在上
         * sizeTransform：配置尺寸渐变
         *
         * 默认的入场的在上面，出场的在下面
         */
        AnimatedContent(show, label = "ac", transitionSpec = {
            // (fadeIn(tween(3000)) with fadeOut(
            //     tween(3000, 3000)
            // ))

            /**
             * 当 targetState 为 true，即 绿色为入场，将绿色 targetContentZIndex 降低，保证绿色在下面
             * 当 targetState 为 false，即 红色为入场，默认入场的在上面
             * 这样绿色一直在下面
             * 适用于背景色的出入场动画，背景色都在后面显示
             * 或者小图标，都在上层显示
             */
            if (targetState == true) {
                (fadeIn(tween(3000)) with fadeOut(
                    tween(3000, 3000)
                )).apply {
                    targetContentZIndex = -1f
                }
            } else {
                fadeIn(tween(3000)) with fadeOut(
                    tween(3000, 3000)
                )

                // using SizeTransform 可以配置是否需要裁切
                // fadeIn(tween(3000)) with fadeOut(
                //     tween(3000, 3000)
                // ) using SizeTransform(false)
            }

        }) {
            if (it) {
                TransitionSquare()
            } else {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.Red)
                )
            }
        }

        Button(
            onClick = {
                show = !show
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        ) {
            Text(text = "切换", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
    }

}

@Preview
@Composable
private fun TransitionSquare() {
    var big by remember {
        mutableStateOf(false)
    }
    val bigTransition = updateTransition(big, label = "big")

    val size by bigTransition.animateDp(label = "size") {
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

