package com.cfx.customcomposable.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.customcomposable.ui.ui.theme.SimpleComposeTheme

/**
 * 自定义 Composable
 *
 * @Composable - 是识别符
 * 表示加了 @Composable 注解的函数 - Composable 函数
 *
 * 自定义 Composable 的使用场景
 * 1.自定义 view + xml 布局文件
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                ColumnCfx("荒天帝")
            }
        }
    }
}

@Composable
private fun ColumnCfx(name: String) {
    Column(
        Modifier
            .padding(0.dp, 30.dp, 0.dp, 0.dp)
            .background(Color.Red)) {
        Text(text = name, fontSize = 26.sp)

    }
}