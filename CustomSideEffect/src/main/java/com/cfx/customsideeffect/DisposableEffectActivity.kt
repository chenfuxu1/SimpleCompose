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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.customsideeffect.ui.theme.SimpleComposeTheme

class DisposableEffectActivity : ComponentActivity() {
    companion object {
        const val TAG = "DisposableEffectActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var showText by remember { mutableStateOf(false) }
                    Column(Modifier.padding(innerPadding)) {
                        Button(
                            onClick = {
                                showText = !showText
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
                        ) {
                            Text("点击", fontSize = 26.sp)

                            if (showText) {
                                Text("荒天帝", fontSize = 26.sp, modifier = Modifier.padding(0.dp, 10.dp))
                            }

                            /**
                             * 每次界面重组后，内部都会执行
                             */
                            SideEffect {
                                Log.d(TAG, "htd SideEffect")
                            }

                            /**
                             * 当 key1 发生变化时，DisposableEffect 会重启
                             * 且界面重启时，会先执行上一次界面的 onDispose，然后才执行 进入界面了
                             *
                             */
                            DisposableEffect(showText) {
                                Log.d(TAG, "htd 进入界面了")

                                /**
                                 * 离开 compose 时会回调
                                 */
                                onDispose {
                                    Log.d(TAG, "htd 离开界面了")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}