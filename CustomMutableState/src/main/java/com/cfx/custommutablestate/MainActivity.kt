package com.cfx.custommutablestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

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
 * Compose 中自动更新
 * MutableState
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val name = mutableStateOf("荒天帝") // MutableState

        /**
         * 使用 by 函数，可以代理 mutableStateOf 对象，但是需要引入
         * import androidx.compose.runtime.getValue
         * import androidx.compose.runtime.setValue
         * 这样定义的 sex 直接代理 value 值，不用再通过 name.value 获取了
         */
        var sex by mutableStateOf("男")
        
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Column {
                    Text(text = name.value, Modifier.padding(40.dp), fontSize = 26.sp, color = Color.Red)

                    Text(text = sex, Modifier.padding(top = 10.dp), fontSize = 26.sp, color = Color.Red)

                }
            }
        }

        lifecycleScope.launch {
            delay(3000)
            name.value = "火灵儿" // 3s 后更改 name 的值，完成控件自动更新

            sex = "女"
        }
    }
}


