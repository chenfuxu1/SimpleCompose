package com.cfx.customview

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.cfx.customview.ui.theme.SimpleComposeTheme

/**
 * compose 和 view 混合使用
 */
class CustomComposeFixViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1.xml 中混用
        // setContentView(R.layout.mix_view)
        // val composeView = findViewById<ComposeView>(R.id.compose_view)
        // composeView.setContent {
        //     CustomTextView()
        // }

        // enableEdgeToEdge()
        // 2.代码中混用
        // val linearLayout = LinearLayout(this)
        // // view 中使用 composeView
        // val composeView = ComposeView(this).apply {
        //     setContent { CustomTextView() }
        // }
        // linearLayout.addView(composeView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        //
        // setContent {
        //     SimpleComposeTheme {
        //         Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        //             Box(Modifier.padding(innerPadding)) {
        //
        //             }
        //         }
        //     }
        // }

        // 3. compose 中使用 view
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                val context = LocalContext.current
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        Column {
                            CustomTextView()
                            AndroidView(factory = {
                                TextView(context).apply {
                                    text = "原生 view"
                                    textSize = 26F
                                }
                            }) {
                                // 提供更新逻辑
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * view 中使用 compose
 */
private fun ViewUseCompose() {

}

@Composable
fun CustomTextView() {
    Text("荒天帝", Modifier.background(Color.Green), fontSize = 26.sp, fontWeight = FontWeight.Bold)
}