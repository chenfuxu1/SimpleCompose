package com.cfx.simplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class SemanticsModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Text("床前明月光")

                        /**
                         * Box 和 Text 是两个节点
                         * 点击 Box 区域读 “大方块”
                         * 点击 Text 区域读 “小方块”
                         */
                        Box(
                            Modifier
                                .padding(0.dp, 20.dp)
                                .width(100.dp)
                                .height(60.dp)
                                .background(Color.Magenta)
                                .semantics(false) {
                                    contentDescription = "大方块"
                                }) {
                            Text("小方块")
                        }

                        /**
                         * 将 Box 和 Text 合并为一个节点
                         * 点击 Box 范围内的所有区域会读出 “大方块小方块”
                         */
                        Box(
                            Modifier
                                .width(100.dp)
                                .height(60.dp)
                                .background(Color.Magenta)
                                .semantics(true) {
                                    contentDescription = "大方块"
                                }) {
                            Text("小方块")
                        }

                        /**
                         * 外部节点吞掉内部节点
                         * 会清除 Box 内部的其他节点
                         * 点击 Box 区域或者 Text 区域播放都是 “大方块”
                         */
                        Box(
                            Modifier
                                .padding(0.dp, 20.dp)
                                .width(100.dp)
                                .height(60.dp)
                                .background(Color.Magenta)
                                .clearAndSetSemantics {
                                    contentDescription = "大方块"
                                }) {
                            Text("小方块")
                        }

                        /**
                         * 默认情况下，在无障碍视角
                         * Button 和 Text 被合并为一个节点
                         * 所以点击 Button 区域和 Text 区域播放是一样的
                         */
                        Button(onClick = {
                        }) {
                            /**
                             * 1.Modifier.semantics(true) 表示可以合并其内部组件，会一起读出
                             * 2.本身不会被其外部组件合并了
                             */
                            Text("疑是地上霜", Modifier.semantics(true) { })
                        }

                    }
                }
            }
        }
    }
}

