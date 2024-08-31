package com.cfx.customanimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.customanimate.ui.theme.SimpleComposeTheme

/**
 * Crossfade 两个组件切换的出场 / 入场动画
 * 只有淡入淡出效果，不能配置其他效果
 * 但是可以配置速度曲线
 */
class TransitionCrossFadeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        var show by remember {
                            mutableStateOf(true)
                        }

                        Crossfade(show, label = "", animationSpec = tween(2000)) {
                            if (it) {
                                TransitionSquare()
                            } else {
                                Box(modifier = Modifier
                                    .size(30.dp)
                                    .background(Color.Red))
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
            }
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