package com.cfx.simplecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.OnRemeasuredModifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class OnRemeasuredModifierActivity : ComponentActivity() {
    companion object {
        const val TAG = "OnRemeasuredModifierActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Text("黄台年底", Modifier.then(object: OnRemeasuredModifier {
                            override fun onRemeasured(size: IntSize) {
                            }
                        }))

                        Text(text = "黄台年底", Modifier.onSizeChanged {

                        })

                        /**
                         * 因为 OnRemeasuredModifier 是和右侧最近的 LayoutModifier 包裹在一起的
                         * 所以 onRemeasured 是在右侧 padding(40.dp) 测量完成后调用
                         */
                        Text("黄台年底", Modifier.padding(20.dp).then(object : OnRemeasuredModifier {
                            override fun onRemeasured(size: IntSize) {
                                Log.d(TAG, "htd size: $size")
                            }

                        }).padding(40.dp))
                    }
                }
            }
        }
    }
}