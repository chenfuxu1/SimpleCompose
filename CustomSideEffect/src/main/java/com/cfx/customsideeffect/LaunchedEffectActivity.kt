package com.cfx.customsideeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.cfx.customsideeffect.ui.theme.SimpleComposeTheme

/**
 * 1.在 Composable 组件展示完成后启动协程
 * 2.在 Composable 组件所依赖参数发生改变时重启协程
 */
class LaunchedEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding )) {
                        /**
                         * key1 发生变化了才会重新启动协程
                         * 参数如果不变，就算发生重组也不会发生变化
                         */
                        LaunchedEffect(key1 = Unit) {

                        }
                    }
                }
            }
        }
    }
}