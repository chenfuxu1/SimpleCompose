package com.cfx.custommutablestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.cfx.custommutablestate.ui.theme.SimpleComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * remember 函数
 */
class RememberActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /**
         * 界面不会更新
         */
        // setContent {
        //     var name by mutableStateOf("荒天帝")
        //
        //     Text(text = name, Modifier.padding(40.dp), color = Color.Red, fontSize = 26.sp)
        //
        //     lifecycleScope.launch {
        //         delay(3000)
        //         name = "云溪"
        //     }
        // }

        /**
         * 修改 name 的重组作用域
         * 可以发现这时界面会更新
         * 因为这次 name 改变时影响的范围只在 Button 内部，不会导致
         * var name by mutableStateOf("荒天帝") 执行
         */
        // setContent {
        //     var name by mutableStateOf("荒天帝")
        //
        //     Button(onClick = {}) {
        //         Text(text = name, Modifier.padding(40.dp), color = Color.Red, fontSize = 26.sp)
        //     }
        //
        //     lifecycleScope.launch {
        //         delay(3000)
        //         name = "云溪"
        //     }
        // }

        /**
         * remember 函数
         * remember 在第一次调用时，会执行 mutableStateOf("荒天帝") 初始化的值返回
         * 同时会进行缓存，后续再调用，不会再进行初始化，直接会返回缓存的值
         */
        setContent {
            var name by remember {
                mutableStateOf("荒天帝")
            }

            Text(text = name, Modifier.padding(40.dp), color = Color.Red, fontSize = 26.sp)

            lifecycleScope.launch {
                delay(3000)
                name = "云溪"
            }
        }
    }
}

@Composable
fun ShowCharCount(value: String) {
    /**
     * 如果第一次 value = "123"
     * 第二次 value = "123"
     * 第三次 value 值发生了变化
     * 这个时候由于 remember 返回的是缓存的值，那么计算就会有问题
     * 所以需要带参数版本的 remember 函数
     */
    val length = remember {
        value.length
    }
    Text(text = "字符串的长度是：$length")

    /**
     * remember(key1, key2，...) {}
     * 带参数版本的 remember 函数
     * 如果 key 相同，那么就使用缓存的值
     * 如果 key 发生变化，那么就重新计算
     */
    val length2 = remember(value) {
        value.length
    }
    Text(text = "字符串的长度是：$length2")
}