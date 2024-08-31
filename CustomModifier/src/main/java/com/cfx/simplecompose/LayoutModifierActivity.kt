package com.cfx.simplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class LayoutModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                // Scaffold(
                //     Modifier
                //         .background(Color.Red)
                //         .fillMaxSize()
                // ) { innerPadding ->
                    // Column(Modifier.padding(innerPadding)) {
                Modifier.layout {measurable, constraints ->
                    var placeable = measurable.measure(constraints)

                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }


                    Column(Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
                        // Box(Modifier.background(Color.Yellow)) {
                        //     Text("荒天帝", Modifier.layout { measurable, constraints ->
                        //         var paddingPx = 10.dp.roundToPx()
                        //         val placeable = measurable.measure(constraints.copy(
                        //             maxWidth = constraints.maxWidth - paddingPx * 2,
                        //             maxHeight = constraints.maxHeight - paddingPx * 2
                        //         ))
                        //         layout(placeable.width + 2 * paddingPx, placeable.height + 2 * paddingPx) {
                        //             // 接收的单位是 px，placeRelative 自适应 rtl 布局
                        //             placeable.placeRelative(paddingPx, paddingPx)
                        //         }
                        //
                        //     })
                        // }

                        /**
                         * 这种写法的结构
                         * LayoutModifier - 10.dp ModifiedLayoutNode
                         *      LayoutModifier - 20.dp ModifiedLayoutNode
                         *          实际修饰的组件 Text() innerLayoutNodeWrapper - InnerPlaceable
                         * 实际测量会先测量 Text()，测量完成后
                         * LayoutModifier - 20.dp 会给 Text() 增加 20.dp 返回
                         * 返回的结果给 LayoutModifier - 10 又增加 10.dp
                         * 所以一共增加了 30.dp
                         */
                        // Text(text = "荒天帝", Modifier.padding(10.dp).padding(20.dp))

                        /**
                         * 结果是方块外部的 padding 为 10.dp
                         * 修改 padding 大小不会影响 size 的大小
                         * 因为虽然先触达到 padding，但是会先测量 size，测量完毕后
                         * 才会给 size 增加 padding
                         */
                        // Box(
                        //     Modifier
                        //         .padding(10.dp)
                        //         .size(100.dp)
                        //         .background(Color.Red))

                        /**
                         * 结果是 40dp 的一个方块
                         * 外面设置上限为 40dp，内部测量上限为 40，结果也是 40
                         */
                        // Box(
                        //     Modifier
                        //         .size(40.dp)
                        //         .size(100.dp)
                        //         .background(Color.Red))

                        /**
                         * 结果是 40dp 的一个方块
                         * 内部还是会按照 100dp 强行测量
                         * 但是外部最终只会裁切显示 40dp 的部分
                         */
                        // Box(
                        //     Modifier
                        //         .size(40.dp)
                        //         .requiredSize(100.dp)
                        //         .background(Color.Red)
                        // )

                        /**
                         * 结果是 padding 为 30.dp 的一个 40.dp 方块
                         */
                        Box(
                            Modifier
                                .size(100.dp)
                                .requiredSize(40.dp)
                                .background(Color.Red))
                    }
                // }

            }
        }

    }
}
