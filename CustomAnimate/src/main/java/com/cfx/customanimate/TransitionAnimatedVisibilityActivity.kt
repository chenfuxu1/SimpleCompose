package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme

/**
 * Transition - 延伸：AnimatedVisibility()
 */

class TransitionAnimatedVisibilityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                MyAnimatedVisibility()
            }
        }
    }
}

@Preview
@Composable
private fun MyAnimatedVisibility() {
    Column(modifier = Modifier.padding(30.dp)) {
        var show by remember {
            mutableStateOf(true)
        }
        /**
         * fun ColumnScope.AnimatedVisibility(
         *     visible: Boolean,
         *     modifier: Modifier = Modifier,
         *     enter: EnterTransition = fadeIn() + expandVertically(),
         *     exit: ExitTransition = fadeOut() + shrinkVertically(),
         *     label: String = "AnimatedVisibility",
         *     content: @Composable AnimatedVisibilityScope.() -> Unit
         * )
         * enter: 入场动画
         * exit: 出场动画
         *
         * internal data class TransitionData(
         *     val fade: Fade? = null,
         *     val slide: Slide? = null,
         *     val changeSize: ChangeSize? = null,
         *     val scale: Scale? = null
         * )
         */
        /**
         * 淡入 入场 Fade
         * fadeIn
         */
        AnimatedVisibility(visible = show, enter = fadeIn(initialAlpha = 0.2f, animationSpec = tween(2000))) {
            TransitionSquare()
        }

        /**
         * 侧滑偏移入场 Slide
         */
        // AnimatedVisibility(visible = show, enter = slideIn {
        //     IntOffset(-it.width, -it.height)
        // }) {
        //     TransitionSquare()
        // }

        // 横向偏移 it 表示 initialOffsetX，表示组件的宽度
        // AnimatedVisibility(visible = show, enter = slideInHorizontally {
        //     -it
        // }) {
        //     TransitionSquare()
        // }

        // 纵向偏移 it 表示 initialOffsetY，表示组件的高度
        // AnimatedVisibility(visible = show, enter = slideInVertically {
        //     -it
        // }) {
        //     TransitionSquare()
        // }

        // AnimatedVisibility(visible = show, enter = slideIn(tween(2000)) {
        //     IntOffset(-it.width, -it.height)
        // }) {
        //     TransitionSquare()
        // }

        /**
         * 裁切入场 ChangeSize
         * expandIn
         * fun expandIn(
         *     animationSpec: FiniteAnimationSpec<IntSize> =
         *         spring(
         *             stiffness = Spring.StiffnessMediumLow,
         *             visibilityThreshold = IntSize.VisibilityThreshold
         *         ),
         *     expandFrom: Alignment = Alignment.BottomEnd,
         *     clip: Boolean = true,
         *     initialSize: (fullSize: IntSize) -> IntSize = { IntSize(0, 0) },
         * )
         * expandFrom = Alignment.TopStart 从左上角开始裁切入场
         * initialSize 开始时的尺寸大小
         * clip = false 表示不裁切，只进行位移
         *
         * expandHorizontally {  } 横向裁切
         * expandVertically {  } 纵向裁切
         */
        // AnimatedVisibility(visible = show, enter = expandIn (tween(2000),
        //     expandFrom = Alignment.TopStart,
        //     initialSize = {
        //         IntSize(it.width / 2, it.height / 2)
        //     },
        //     clip = false)
        //      ) {
        //     TransitionSquare()
        // }

        /**
         * 缩放入场 Scale
         * scaleIn
         * fun scaleIn(
         *     animationSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessMediumLow),
         *     initialScale: Float = 0f,
         *     transformOrigin: TransformOrigin = TransformOrigin.Center,
         * )
         * transformOrigin = TransformOrigin(1f, 1f) 从右下角开始缩放
         */
        // AnimatedVisibility(visible = show, enter = scaleIn (tween(2000), transformOrigin = TransformOrigin(1f, 1f))
        // ) {
        //     TransitionSquare()
        // }

        /**
         * enter: EnterTransition = fadeIn() + expandVertically()
         * 其中 + 号表示如下：
         * operator fun plus(enter: EnterTransition): EnterTransition {
         *     return EnterTransitionImpl(
         *         TransitionData(
         *             fade = data.fade ?: enter.data.fade,
         *             slide = data.slide ?: enter.data.slide,
         *             changeSize = data.changeSize ?: enter.data.changeSize,
         *             scale = data.scale ?: enter.data.scale
         *         )
         *     )
         * }
         * 表示只要左边的数据不为空，就用左边的数据，为空就用右边的数据
         * 因此 + 号可以实现复杂的叠加的动画效果
         * scaleIn (tween(2000)) + expandIn() 表示缩放 + 裁切的效果
         *
         * 注意：expandIn 是入场，对应的出场是 shrinkOut
         */
        // AnimatedVisibility(
        //     visible = show, enter = scaleIn(tween(2000)) + expandIn()
        // ) {
        //     TransitionSquare()
        // }

        Button(
            onClick = {
                show = !show
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
            modifier = Modifier
                .padding(5.dp, 20.dp, 0.dp, 0.dp)
                .alpha(0.5f)
        ) {
            Text(text = "切换", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
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

    bigTransition.AnimatedVisibility({
        it
    }, enter = scaleIn(tween(2000))) {

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