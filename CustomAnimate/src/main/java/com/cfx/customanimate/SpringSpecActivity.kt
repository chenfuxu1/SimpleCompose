package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
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
 * 弹簧效果
 */
class SpringSpecActivity : ComponentActivity() {
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
                         * 弹簧效果
                         * spring(
                         *     dampingRatio: Float = Spring.DampingRatioNoBouncy,
                         *     stiffness: Float = Spring.StiffnessMedium,
                         *     visibilityThreshold: T? = null
                         * )
                         * dampingRatio 阻尼比，设置弹簧有多弹，值越小，弹动的越久，如果是 0，会一直弹，但不能设置 0，设置 0 直接不弹，默认值是 1，也是不弹
                         * stiffness    刚度，弹簧按下去有多想形变回来的强度, 刚度越大，回弹的越快
                         * visibilityThreshold 可视的阈值，当弹簧低于这个阈值，就停下来，默认是 0.01f
                         * */
                        // anim.animateTo(size, spring(Spring.DampingRatioHighBouncy, Spring.StiffnessHigh, 5.dp))

                        // 只在原地震动效果
                        anim.animateTo(96.dp, spring(0.1f, Spring.StiffnessHigh), 2000.dp)
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
