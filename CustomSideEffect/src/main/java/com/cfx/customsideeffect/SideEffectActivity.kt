package com.cfx.customsideeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.cfx.customsideeffect.ui.theme.SimpleComposeTheme

/**
 * 副作用：函数的内容能否被返回值所替代，能就是没有副作用，不能就是有副作用
 */
class SideEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var seasonCount = 0
                    /**
                     * 当 compose 重组时，可能内部代码执行了一半就重组了，这个时候 seasonCount 计数就是错误的
                     * 这个就是副作用带来的影响
                     */
                    Column(Modifier.padding(innerPadding)) {
                        SideEffect {
                            /**
                             * 可以使用 SideEffect，会先将 SideEffect 内部代码存起来
                             * 其内部代码只会在重组之后执行一次
                             */
                        }

                        val names = arrayOf("春天", "夏天", "秋天", "冬天")
                        for(name in names) {
                            Text(text = name)
                            seasonCount++
                            // side effect 附带效应
                        }

                        Text(text = "一共有多少个 $seasonCount 季节")
                    }
                }
            }
        }
    }

    /**
     * 该函数没有返回值，函数的内容不能被返回值代替，那么就是有副作用的
     * 返回值和函数的内容之间的差异就是副作用
     */
    fun a() {
        println("123")
    }
}