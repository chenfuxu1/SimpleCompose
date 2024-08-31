package com.cfx.customsideeffect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.cfx.customsideeffect.ui.theme.SimpleComposeTheme

/**
 * 当用到的 flow 函数用到 compos 时，使用 snapshotFlow
 */
class SnapshotFlowActivity : ComponentActivity() {
    companion object {
        const val TAG = "SnapshotFlowActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {}

                    var name by remember { mutableStateOf("荒天帝") }
                    var age by remember { mutableStateOf(20) }

                    /**
                     * 把 compose 中的 name 转换为协程中可感知的状态
                     * snapshotFlow 可以创建对内部用到的 compose 有感知的 flow
                     */
                    val flow = snapshotFlow {
                        "$name $age"
                    }
                    LaunchedEffect(Unit) {
                        /**
                         * 当 name 发生变化时，协程内部会重新打印
                         */
                        flow.collect { info ->
                            Log.d(TAG, "htd name: $info")
                        }
                    }
                }
            }
        }
    }
}