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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class CombinedModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        /**
                         * fun Modifier.background(
                         *     color: Color,
                         *     shape: Shape = RectangleShape
                         * ): Modifier {
                         *     val alpha = 1.0f // for solid colors
                         *     return this.then( // this 表示调用 background 函数的 Modifier
                         *         BackgroundElement( // 会创建一个 Modifier 出来
                         *             color = color,
                         *             shape = shape,
                         *             alpha = alpha,
                         *             inspectorInfo = debugInspectorInfo {
                         *                 name = "background"
                         *                 value = color
                         *                 properties["color"] = color
                         *                 properties["shape"] = shape
                         *             }
                         *         )
                         *     )
                         * }
                         *
                         * infix fun then(other: Modifier): Modifier =
                         *         if (other === Modifier) this else CombinedModifier(this, other)
                         *
                         * 在 Modifier 伴生对象中，重写了 then 方法，该方法中，传入的 Modifier 接口会直接返回
                         * companion object : Modifier {
                         *     override fun <R> foldIn(initial: R, operation: (R, Element) -> R): R = initial
                         *     override fun <R> foldOut(initial: R, operation: (Element, R) -> R): R = initial
                         *     override fun any(predicate: (Element) -> Boolean): Boolean = false
                         *     override fun all(predicate: (Element) -> Boolean): Boolean = true
                         *     override infix fun then(other: Modifier): Modifier = other
                         *     override fun toString() = "Modifier"
                         * }
                         * Modifier.then(Modifier) // 会直接返回括号中的 Modifier
                         *
                         *
                         * Modifier.background(Color.Red) 表示 Modifier 伴生对象调用了 background 方法，是一个扩展函数
                         */
                        Modifier.background(Color.Red) // 返回参数 BackgroundElement 对象

                        Modifier.then(Modifier) // 会直接返回括号中的 Modifier
                        /**
                         * 返回  Modifier.background(Color.Red)
                         * infix fun then(other: Modifier): Modifier =
                         *          if (other === Modifier) this else CombinedModifier(this, other)
                         * 因为传入的是 Modifier，other === Modifier 所以直接将 Modifier.background(Color.Red) 返回
                         * Modifier.background(Color.Red).then(Modifier) 等价于 Modifier.background(Color.Red)
                         */
                        Modifier
                            .background(Color.Red)
                            .then(Modifier)

                        /**
                         * 因为传入的是 Modifier.padding(10.dp) 所以 then 返回的是 CombinedModifier(this, other)
                         * 等价于 CombinedModifier(Modifier.background(Color.Red), Modifier.padding(10.dp)) 两者结合的效果
                         */
                        Modifier
                            .background(Color.Red)
                            .then(Modifier.padding(10.dp))
                        // CombinedModifier(Modifier.background(Color.Red), Modifier.padding(10.dp))

                        Modifier
                            .background(Color.Red)
                            .then(Modifier.padding(10.dp))
                            .then(Modifier.size(100.dp))
                        // 上面写法等价于
                        CombinedModifier(
                            CombinedModifier( Modifier.background(Color.Red), Modifier.padding(10.dp)),
                            Modifier.size(100.dp)
                        )


                        Custom()
                    }
                }
            }
        }
    }
}

@Composable
private fun Custom(modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(100.dp)
            .background(Color.Red)
    )
}