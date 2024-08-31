package com.cfx.simplecompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class ComposedModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        /**
                         * 这种写法，不共享内部状态
                         * 因为每次 compose 重组都重新执行工厂方法返回一个对象
                         * 1.ComposedModifier 可以创造出带有状态的 Modifier 函数
                         * 能让这个 Modifier 能在多处重用状态
                         * 状态：将 remember 包在 composed 内部，和 Modifier 绑在一起，作为内部状态使用
                         * 2.ComposedModifier 可以在每一个 Modifier 的使用处都创创建一个独立的 Modifier
                         * 这样每个 Modifier 的内部状态都是独立的，互不影响
                         */
                        val modifier1 = Modifier.composed {
                            var padding by remember { mutableStateOf(20.dp) }
                            Modifier
                                .padding(padding)
                                .clickable {
                                    padding = 0.dp
                                }
                        }
                        var padding by remember {
                            mutableStateOf(20.dp)
                        }

                        /**
                         * 这种写法是共享内部状态的
                         * 一处调整了内部状态，所有使用的地方都会发生改变
                         */
                        val modifier2 = Modifier
                            .padding(padding)
                            .clickable {
                                padding = 0.dp
                            }
                        Box(Modifier.background(Color.Red) then modifier1)
                        Text("荒天帝", Modifier.background(Color.Green) then modifier1)
                    }
                }
            }
        }
    }
}

/**
 * composed 主要提供 compose 上下文环境
 * 这样内部才能使用 Composable 函数，例如 remember
 * 提供了在组合过程中实时创建 Modifier 的功能
 *
 * 原则：当创建的 Modifier 需要使用 Composable 函数时，我们就需要包上一层 composed
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.paddingJumpModifier() = composed {
    var padding by remember { mutableStateOf(20.dp) }
    Modifier
        .padding(padding)
        .clickable {
            padding = 0.dp
        }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.coroutineModifier() = composed {
    LaunchedEffect(key1 = Unit) {

    }
    Modifier
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.localModifier() = composed {
    LocalContext.current
    Modifier
}