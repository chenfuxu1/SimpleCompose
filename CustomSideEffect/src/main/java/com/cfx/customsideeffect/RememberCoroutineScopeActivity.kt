package com.cfx.customsideeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.cfx.customsideeffect.ui.theme.SimpleComposeTheme
import kotlinx.coroutines.launch

class RememberCoroutineScopeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        /**
                         * lifecycleScope 的生命周期是和 activity 绑定的
                         * 所以这里不能使用，因为范围太大了
                         * 会导致离开 compose 界面不会取消所有的协程
                         */
                        // lifecycleScope.launch {
                        //
                        // }

                        // 1.提供 CoroutineScope
                        val scope = rememberCoroutineScope()
                        // 2.remember 保证重复创建
                        val coroutine = remember {
                            scope.launch {  }
                        }

                        /**
                         * 1.可以提供 CoroutineScope，离开 compose 可以自动取消
                         * 2.LaunchedEffect 内部包有 remember，可以保证重组时，协程不会重复创建
                         */
                        LaunchedEffect(Unit) {

                        }

                        Box(Modifier.clickable {
                            /**
                             * 点击组件时，启动协程，使用 rememberCoroutineScope
                             * 因为这个 rememberCoroutineScope 和组件绑定的，界面不可见时可以最快取消
                             */
                            scope.launch {  }

                            /**
                             * 虽热 lifecycleScope 也可以使用，但是是和 activity 生命周期绑定的
                             * 会很晚取消，activity 销毁时才会取消
                             */
                            lifecycleScope.launch {  }

                            // 这里不是 compose 环境 不能这样调用，而且这里也不会因为重组出现反复调用的情况
                            // LaunchedEffect(key1 = Unit) {
                            //
                            // }
                        })
                    }
                }
            }
        }
    }
}