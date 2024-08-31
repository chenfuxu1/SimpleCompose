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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class ModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        /**
                         * 1.Modifier 和 Modifier.Companion 的写法是等价的，拿到的是伴生对象
                         * companion object : Modifier {
                         *     override fun <R> foldIn(initial: R, operation: (R, Element) -> R): R = initial
                         *     override fun <R> foldOut(initial: R, operation: (Element, R) -> R): R = initial
                         *     override fun any(predicate: (Element) -> Boolean): Boolean = false
                         *     override fun all(predicate: (Element) -> Boolean): Boolean = true
                         *     override infix fun then(other: Modifier): Modifier = other
                         *     override fun toString() = "Modifier"
                         * }
                         */
                        // Modifier
                        Custom(Modifier) // 传入 Modifier 伴生对象作为入参
                        Custom() // 也可不传

                        /**
                         * Modifier.padding 不是 Modifier 的静态函数，是扩展函数
                         */
                        Modifier.padding(innerPadding).background(Color.Green)
                    }
                }
            }
        }
    }
}

/**
 * modifier:  Modifier =        Modifier
 * 形参       Modifier 接口     Modifier 伴生对象
 * Compose 中规定，将 modifier 作为参数时需要将其作为第一个参数
 * 第一个参数有一个特权，可以不填，也可以省略 modifier = Modifier
 */
@Composable
private fun Custom(modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(100.dp)
            .background(Color.Red)
    )
}