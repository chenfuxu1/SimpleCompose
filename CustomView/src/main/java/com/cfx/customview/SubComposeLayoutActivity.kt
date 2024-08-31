package com.cfx.customview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp
import com.cfx.customview.ui.theme.SimpleComposeTheme

/**
 * 将组合操作推迟到测量阶段发生
 * Scaffold LazyColumn 底层都是通过 SubComposeLayout 实现
 *
 * 缺点：将组合和测量阶段混在一起，可能会引起额外的性能损耗，所以不需要使用的场景不要使用
 * SubComposeLayout
 */

class SubComposeLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding))
                    /**
                     * compose 阶段顺序：组合 -> 测量 -> 布局 -> 绘制
                     * 正常情况下组合阶段是拿不到测量参数的
                     *
                     * 但是通过 SubComposeLayout 可以拿到测量参数
                     */
                    BoxWithConstraints() {
                        // 可以拿到测量参数 constraints
                        // constraints
                        if (minWidth >= 360.dp) {

                        }
                        Text("荒天帝", Modifier.align(Alignment.Center))
                    }

                    /**
                     * {} 内部不是 compose 环境，是测量阶段才会执行，所以不能直接调用 compose 函数
                     * 将组合操作推迟到测量阶段发生
                     */
                    SubcomposeLayout(Modifier.padding(top = 20.dp)) { constraints ->
                        // 1.需要在 subcompose 执行重组
                        val measurables = subcompose(1) {
                            Text("云曦")
                        }
                        // 2.这里只有一个控件，直接取 0 号 Measurable
                        val measurable = measurables[0]
                        // 3.测量
                        val placeable = measurable.measure(constraints)
                        // 4.摆放
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }


                }
            }
        }
    }
}