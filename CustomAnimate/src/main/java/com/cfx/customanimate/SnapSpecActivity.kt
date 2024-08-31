package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.snap
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

/**
 * 突变的效果，设置值瞬间完成，中间没有过度
 */
class SnapSpecActivity : ComponentActivity() {
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
                        if (big) 192.dp else 48.dp
                    }

                    val anim = remember {
                        Animatable(size, Dp.VectorConverter)
                    }

                    LaunchedEffect(key1 = big) {
                        // 延迟 2s 后，瞬间设置完成
                        anim.animateTo(size, snap(2000))
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