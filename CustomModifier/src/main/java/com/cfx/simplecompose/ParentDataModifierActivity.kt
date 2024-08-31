package com.cfx.simplecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ParentDataModifierActivity.Companion.TAG
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class ParentDataModifierActivity : ComponentActivity() {
    companion object {
        const val TAG = "ParentDataModifierActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Row(Modifier.padding(innerPadding)) {
                        Box(
                            Modifier
                                .size(80.dp)
                                .background(Color.Red)
                                .weight(1f)
                        )
                        Box(
                            Modifier
                                .size(80.dp)
                                .background(Color.Green)
                        )
                        Box(
                            Modifier
                                .size(80.dp)
                                .background(Color.Blue)
                        )
                    }

                    // CustomLayout(Modifier.size(40.dp)) {
                    //     Text("荒天帝", Modifier.layoutId("big"))
                    //     Text("火灵儿", Modifier.layoutId("small"))
                    //     Box(
                    //         Modifier
                    //             .size(20.dp)
                    //             .background(Color.Red))
                    // }

                    /**
                     * 最终返回的是 Modifier.weightData(1f)，右边的 weightData(2f) 会被左边的覆盖掉
                     * 相当于 xml 中不可能对一个 view 设置两次 layout_weight
                     * 那么如果需要设置不同类型的数据怎么处理呢，如果链式调用相当于右边设置的都无效
                     */
                    CustomLayout2 {
                        Text(
                            text = "1",
                            Modifier
                                .weightData(1f)
                                .weightData(2f)
                        )
                    }

                    CustomLayout3 {
                        Text(
                            text = "1",
                            Modifier
                                .weightData3(1f)
                                .bigData3(true)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content, modifier) { measurables, constraints ->
        measurables.forEach {
            // when (it.layoutId) {
            //     "big" -> it.measure(constraints.xxx)
            //     "small" -> it.measure(constraints.xxx)
            //     else -> it.measure(constraints.xxx)
            // }
        }
        layout(100, 100) {

        }
    }
}

@Composable
fun CustomLayout2(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content, modifier) { measurables, constraints ->
        measurables.forEach {
            val data = it.parentData as? Float
        }
        layout(100, 100) {

        }
    }
}

fun Modifier.weightData(weight: Float) = then(object : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any? {
        return weight
    }
})

// 创建实体类
class CustomLayoutData(var weight: Float = 0f, var big: Boolean = false)

@Composable
fun CustomLayout3(
    modifier: Modifier = Modifier,
    content: @Composable CustomLayout3Scope.() -> Unit
) {
    Layout({ CustomLayout3Scope.content() }, modifier) { measurables, constraints ->
        measurables.forEach {
            val data = it.parentData as? CustomLayoutData
            var big = data?.big
            var weight = data?.weight
            Log.d(TAG, "htd big: $big weight：$weight")
        }
        layout(100, 100) {

        }
    }
}

/**
 * 为了防止 api 接口污染，需要将 Modifier.weightData3 Modifier.bigData3 限定在 CustomLayout3 内部使用
 * @LayoutScopeMarker 作用，只能在 CustomLayout3Scope 直接内部使用，不能穿透
 * 间接内部（内部的内部）和外部都不能使用
 *
 * @Immutable 作用是减少不必要的重组
 */
@LayoutScopeMarker
@Immutable
object CustomLayout3Scope {
    fun Modifier.weightData3(weight: Float) = then(object : ParentDataModifier {
        // 完成数据融合
        override fun Density.modifyParentData(parentData: Any?): Any? {
            return if (parentData == null) CustomLayoutData(weight)
            else (parentData as CustomLayoutData).apply {
                this.weight = weight
            }
        }
    })

    fun Modifier.bigData3(big: Boolean) = then(object : ParentDataModifier {
        // 完成数据融合
        override fun Density.modifyParentData(parentData: Any?): Any? {
            return ((parentData as? CustomLayoutData) ?: CustomLayoutData()).also {
                it.big = big
            }
        }
    })
}
