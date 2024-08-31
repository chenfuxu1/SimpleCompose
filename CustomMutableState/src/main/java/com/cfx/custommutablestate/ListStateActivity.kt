package com.cfx.custommutablestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.ui.theme.SimpleComposeTheme

/**
 * 更新 List 不会触发刷新 - 状态机制的背后
 */
class ListStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var num by mutableStateOf(1) // MutableState
        var numStr by mutableStateOf("11") // MutableState

        var numList by mutableStateOf(mutableListOf(1, 2, 3)) // MutableState<MutableList<Int>>
        var flag by mutableStateOf(1)

        val numStateList = mutableStateListOf(1, 2, 3)
        val numStateMap = mutableStateMapOf(1 to "one", 2 to "two", 3 to "three")

        setContent {
            SimpleComposeTheme {
                // 1.Int 类型
                // Text(
                //     text = "num 当前的值为：$num",
                //     color = Color.Red,
                //     modifier = Modifier
                //         .padding(40.dp)
                //         .clickable {
                //             num++
                //         })

                // 2.String 类型
                // Text(
                //     text = "numStr 当前的值为：$numStr",
                //     color = Color.Red,
                //     modifier = Modifier
                //         .padding(top = 70.dp, start = 40.dp)
                //         .clickable {
                //             numStr = (numStr.toInt() + 1).toString()
                //         })

                // 3.List 类型
                // Column(Modifier.padding(10.dp, 40.dp)) {
                //     Button(
                //         colors = ButtonDefaults.buttonColors(
                //             contentColor = Color.Red, // 修改内容颜色
                //             containerColor = Color.Green // 修改背景颜色
                //         ), onClick = {
                //             numList.add(numList.last() + 1) // 点击增加一条数据
                //         }) {
                //         Text(text = "点击增加数据", fontSize = 24.sp)
                //     }
                //     for (num in numList) {
                //         Text(text = "这是第 $num 条数据", color = Color.Red, fontSize = 24.sp)
                //     }
                // }

                // 4.List 类型 + flag
                // Column(Modifier.padding(10.dp, 40.dp)) {
                //
                //     Text(text = "修改 flag $flag", fontSize = 24.sp, modifier = Modifier.clickable {
                //         flag++
                //     })
                //
                //     Button(
                //         colors = ButtonDefaults.buttonColors(
                //             contentColor = Color.Red, // 修改内容颜色
                //             containerColor = Color.Green // 修改背景颜色
                //         ), onClick = {
                //             numList.add(numList.last() + 1) // 点击增加一条数据
                //         }) {
                //         Text(text = "点击增加数据", fontSize = 24.sp)
                //     }
                //     for (num in numList) {
                //         Text(text = "这是第 $num 条数据", color = Color.Red, fontSize = 24.sp)
                //     }
                // }

                // 5.List 类型 + 重新 setValue
                // Column(Modifier.padding(10.dp, 40.dp)) {
                //
                //     Button(
                //         colors = ButtonDefaults.buttonColors(
                //             contentColor = Color.Red, // 修改内容颜色
                //             containerColor = Color.Green // 修改背景颜色
                //         ), onClick = {
                //             numList = numList.toMutableList().apply {
                //                 add(numList.last() + 1)
                //             }  // 点击增加一条数据
                //         }) {
                //         Text(text = "点击增加数据", fontSize = 24.sp)
                //     }
                //     for (num in numList) {
                //         Text(text = "这是第 $num 条数据", color = Color.Red, fontSize = 24.sp)
                //     }
                // }

                // 6.mutableStateListOf
                Column(Modifier.padding(10.dp, 40.dp)) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Red, // 修改内容颜色
                            containerColor = Color.Green // 修改背景颜色
                        ), onClick = {
                            numStateList.add(numStateList.last() + 1) // 点击增加一条数据
                        }) {
                        Text(text = "点击增加数据", fontSize = 24.sp)
                    }
                    for (num in numStateList) {
                        Text(text = "这是第 $num 条数据", color = Color.Red, fontSize = 24.sp)
                    }
                }

                // 7.mutableStateMapOf
                Column(Modifier.padding(10.dp, 40.dp)) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Red, // 修改内容颜色
                            containerColor = Color.Green // 修改背景颜色
                        ), onClick = {
                            numStateMap[4] = "four" // 点击增加一条数据
                        }) {
                        Text(text = "点击增加数据", fontSize = 24.sp)
                    }
                    for ((key, value) in numStateMap) {
                        Text(text = "这是第 $key 条数据 $value", color = Color.Red, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}