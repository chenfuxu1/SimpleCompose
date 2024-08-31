package com.cfx.simplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

/**
 * 全局定位
 * OnGloballyPositionedModifier 面向的整个窗口的位置可能会发生改变时，会回调
 * 比 OnPlacedModifier 的回调时机更多，一般可以用 OnPlacedModifier 就用 OnPlacedModifier
 */
class OnGloballyPositionedModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        var offsetX by remember { mutableStateOf(0) }
                        var offsetY by remember { mutableStateOf(0) }
                        /**
                         * offset 的回调时机在 onPlaced 之后
                         */
                        Modifier.onPlaced {
                            val posInParent = it.positionInParent()
                            offsetX = posInParent.x.toInt()
                            offsetY = posInParent.y.toInt()
                        }.offset {
                            IntOffset(offsetX, offsetY)
                        }.size(100.dp)
                        /**
                         * 相对于窗口 / 全局位置发生改变时，该方法会回调
                         * 正常情况下，只要重新发生布局，就会回调
                         */
                        Modifier.onGloballyPositioned {  }
                    }
                }
            }
        }
    }
}