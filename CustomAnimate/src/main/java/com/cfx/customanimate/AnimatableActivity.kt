package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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

class AnimatableActivity : ComponentActivity() {
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

                    /**
                     * 当 key 值发生变化时，才会执行协程
                     */
                    LaunchedEffect(key1 = big) {
                        /**
                         * snapTo 可以设置初始值，瞬间设置
                         */
                        anim.snapTo(if (big) 192.dp else 0.dp)
                        // animateTo 是渐变到目标值 size
                        anim.animateTo(size)
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

