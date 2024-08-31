package com.cfx.customsideeffect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.cfx.customsideeffect.ui.theme.SimpleComposeTheme
import kotlinx.coroutines.delay

class RememberUpdatedStateActivity : ComponentActivity() {
    companion object {
        const val TAG = "RememberUpdatedStateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        var welcome by remember { mutableStateOf("欢迎光临") }
                        // CustomLaunchedEffect(welcome)
                        // CustomLaunchedEffect2(welcome)
                        CustomLaunchedEffect3(welcome)
                        Button(onClick = {
                            welcome = "不欢迎"
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)) {
                            Text(text = "不欢迎", fontSize = 26.sp)
                        }
                    }

                }

            }
        }
    }


}

/**
 * LaunchedEffect 只会启动一个协程
 * 如果在 4s 之内改变了 welcome 的值，由于 CustomLaunchedEffect 的 welcome 只是普通的字符串
 * 在重组过程中 LaunchedEffect 只会执行一次，所以输出的值 welcome 还是 欢迎光临
 * 这样就是有问题的，因为在在 4s 内已经点击改变了，但实际输出还是初始值
 */
@Composable
private fun CustomLaunchedEffect(welcome: String) {
    LaunchedEffect(Unit) {
        delay(4000)
        Log.d(RememberUpdatedStateActivity.TAG, "htd welcome: $welcome")
    }
}

/**
 * 在函数内部增加一个多次重组的状态可以解决上述存在的问题
 */
@Composable
private fun CustomLaunchedEffect2(welcome: String) {
    var rememberedWelcome by remember { mutableStateOf(welcome) }
    rememberedWelcome = welcome
    LaunchedEffect(Unit) {
        delay(4000)
        Log.d(RememberUpdatedStateActivity.TAG, "htd welcome: $rememberedWelcome")
    }
}

/**
 * var rememberedWelcome by remember { mutableStateOf(welcome) }
 * rememberedWelcome = welcome
 * 上述这俩行就等价于 rememberUpdatedState 函数
 */
@Composable
private fun CustomLaunchedEffect3(welcome: String) {
    var rememberUpdatedState = rememberUpdatedState(newValue = welcome)
    LaunchedEffect(Unit) {
        delay(4000)
        Log.d(RememberUpdatedStateActivity.TAG, "htd welcome: ${rememberUpdatedState.value}")
    }
}

@Composable
private fun CustomDisposableEffect(user: User, subscriber: Subscriber) {
    val updatedUser = rememberUpdatedState(newValue = user)
    DisposableEffect(Unit) {
        subscriber.subscribe(updatedUser.value)
        onDispose {
            subscriber.unSubscribe()
        }
    }
}

class User

interface Subscriber {
    fun subscribe(user: User)

    fun unSubscribe()
}