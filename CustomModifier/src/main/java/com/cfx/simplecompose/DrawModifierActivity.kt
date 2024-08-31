package com.cfx.simplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class DrawModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Box(
                            Modifier
                                .size(100.dp)
                                .drawWithContent {
                                    drawContent()
                                    drawCircle(Color.Yellow)
                                }
                                .background(Color.Red))

                        /**
                         * 绘制上左边包着右边
                         * 因为是从右往左插入的，所以左边的绘制是头结点，先绘制
                         * 所以最终绘制的颜色是 YELLOW
                         */
                        Box(
                            Modifier
                                .size(100.dp)
                                .background(Color.Red)
                                .background(Color.Yellow))

                        /**
                         * 每个 DrawModifier 会和右边的最近的 LayoutModifier 放在一起
                         * 所以这里 Color.Blue 是放在右边 50dp 中的
                         */
                        Box(Modifier.requiredSize(100.dp).background(Color.Blue).requiredSize(50.dp))

                        /**
                         * Modifier.background(Color.Blue).background(Color.Red) 跟随右边的 100.dp
                         * .background(Color.Yellow).background(Color.Magenta) 跟随右边的 60.dp
                         * .background(Color.Green).background(Color.LightGray) 跟随 Box 的 InnerPlaceable
                         */
                        Box(Modifier.background(Color.Blue).background(Color.Red).requiredSize(100.dp)
                            .background(Color.Yellow).background(Color.Magenta).requiredSize(60.dp)
                            .background(Color.Green).background(Color.LightGray))
                    }

                }
            }
        }
    }
}