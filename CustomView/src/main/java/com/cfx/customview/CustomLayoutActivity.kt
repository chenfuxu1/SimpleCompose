package com.cfx.customview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.cfx.customview.ui.theme.SimpleComposeTheme
import kotlin.math.max

/**
 * 自定义布局
 */
class CustomLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        Text("张十八")
                        CustomLayout {
                            Box(
                                Modifier
                                    .size(80.dp)
                                    .background(Color.Red))
                            Box(
                                Modifier
                                    .size(100.dp)
                                    .background(Color.Green))
                            Box(
                                Modifier
                                    .size(120.dp)
                                    .background(Color.Blue))
                        }
                    }
                }
            }
        }
    }
}

/**
 * 自定义纵向的 ViewGroup
 */
@Composable
fun CustomLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    // 最大宽度
    var maxWidth = 0
    // 总高度
    var totalHeight = 0
    Layout(content) { measurables, constraints ->
        /**
         *  1.每个控件依次进行测量
         *  记录控件中的最大宽度，以及所有控件的总高度
         */
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints).also { placeable ->
                maxWidth = max(maxWidth, placeable.width)
                totalHeight += placeable.height
            }
        }
        /**
         * 2.将所有控件中的最大宽度作为 layout 的宽度
         * 所有的控件高度相加作为 layout 的高度
         */
        layout(maxWidth, totalHeight) {
            var moveY = 0
            // 3.依次进行从上到下摆放
            placeables.forEach { placeable ->
                placeable.placeRelative(0, moveY)
                moveY += placeable.height
            }
        }
    }
}
