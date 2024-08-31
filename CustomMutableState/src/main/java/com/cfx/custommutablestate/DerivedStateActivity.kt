package com.cfx.custommutablestate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.custommutablestate.DerivedStateActivity.Companion.TAG
import com.cfx.custommutablestate.ui.theme.SimpleComposeTheme

/**
 * derivedStateOf() 和 remember() 的区别
 */
class DerivedStateActivity : ComponentActivity() {
    companion object {
        const val TAG = "DerivedStateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 1.监听变化的为 string 类型
            // SimpleComposeTheme {
            //     var name by remember {
            //         mutableStateOf("huangtiandi")
            //     }
            //
            //     /**
            //      * processedName1 与 processedName2 区别，都可以实现 name 状态发生改变时，外界重组变化
            //      * 1.processedName1 是 by 赋值，processedName2 是 = 号赋值，这个区别可以忽略，by 只是一个代理
            //      * 2.processedName2 的 remember 函数是有参数，当参数发生变化时，会重新执行 {} 内部代码
            //      * 但 remember 是无参数，意味着不会重新执行 remember {} 大括号内部的代码
            //      * 3.processedName1 的 remember 内部加了 derivedStateOf {}，当 derivedStateOf 内部的代码即
            //      * name 发生变化时，会重新执行 derivedStateOf 大括号内部的代码，从而能够监听内部的变化
            //      */
            //     val processedName1 by remember {
            //         derivedStateOf {
            //             name.uppercase()
            //         }
            //     }
            //
            //     val processedName2 = remember(name) {
            //         name.uppercase()
            //     }
            //
            //     Text(text = processedName2, fontSize = 24.sp, color = Color.Red,
            //         modifier = Modifier.padding(10.dp, 40.dp).clickable {
            //             name = "hahaha"
            //         })
            // }

            // // 2.监听变化的为 List 类型
            // SimpleComposeTheme {
            //     Log.d(TAG, "htd 111111111 ")
            //     var names = remember {
            //         mutableStateListOf("zhangsan", "lisi")
            //     }.apply {
            //         Log.d(TAG, "htd names " + this.toList())
            //     }
            //
            //     Log.d(TAG, "htd 2222222 ")
            //
            //     val processedNames = remember(names) {
            //         names.map {
            //             it.uppercase()
            //         }
            //     }.apply {
            //         Log.d(TAG, "htd processedNames " + this)
            //     }
            //     Log.d(TAG, "htdhtd 3333333 ")
            //
            //     Column {
            //         for (processName in processedNames) {
            //             Text(text = processName, fontSize = 24.sp, color = Color.Red,
            //                 modifier = Modifier
            //                     .padding(10.dp, 20.dp)
            //                     .clickable {
            //                         names.add("hahaha") // 会触发重组
            //                     })
            //         }
            //     }
            // }

            // 3.监听变化的为 List 类型 + names 对象重新赋值
            // SimpleComposeTheme {
            //     Log.d(TAG, "htd 111111111 ")
            //     // var names = remember {
            //     //     mutableStateListOf("zhangsan", "lisi")
            //     // }.apply {
            //     //     Log.d(TAG, "htd names " + this.toList())
            //     // }
            //
            //     var names by remember { // mutableStateOf 返回的 State<T> 对象，用 by
            //         mutableStateOf(listOf("zhangsan", "lisi"))
            //     }.apply {
            //         Log.d(TAG, "htd names " + this.value)
            //     }
            //
            //     Log.d(TAG, "htd 2222222 ")
            //
            //     val processedNames = remember(names) {
            //         names.map {
            //             it.uppercase()
            //         }
            //     }.apply {
            //         Log.d(TAG, "htd processedNames " + this)
            //     }
            //     Log.d(TAG, "htdhtd 3333333 ")
            //
            //     Column {
            //         for (processName in processedNames) {
            //             Text(text = processName, fontSize = 24.sp, color = Color.Red,
            //                 modifier = Modifier
            //                     .padding(10.dp, 20.dp)
            //                     .clickable {
            //                         // 对 names 重新赋值
            //                         names = names.toMutableList().apply {
            //                             add("hahaha")
            //                         }
            //                     })
            //         }
            //     }
            // }

            // 4.监听变化的为 List 类型 + 使用 derivedStateOf
            // SimpleComposeTheme {
            //     Log.d(TAG, "htd 111111111 ")
            //     var names = remember {
            //         mutableStateListOf("zhangsan", "lisi")
            //     }.apply {
            //         Log.d(TAG, "htd names " + this.toList())
            //     }
            //
            //     Log.d(TAG, "htd 2222222 ")
            //
            //     /**
            //      * 1.remember 负责内部发生变化时也不重新执行代码，使用缓存
            //      * 2.derivedStateOf 负责内部的数据发生变化时，重新执行
            //      * 二者各司其职
            //      */
            //     val processedNames by remember {
            //         derivedStateOf { // derivedStateOf 返回的是一个 State<T> 对象，所以这里使用 by
            //             names.map {
            //                 it.uppercase()
            //             }
            //         }
            //     }.apply {
            //         Log.d(TAG, "htd processedNames " + this.value)
            //     }
            //     Log.d(TAG, "htdhtd 3333333 ")
            //
            //     Column {
            //         for (processName in processedNames) {
            //             Text(text = processName, fontSize = 24.sp, color = Color.Red,
            //                 modifier = Modifier
            //                     .padding(10.dp, 20.dp)
            //                     .clickable {
            //                         names.add("hahaha") // 触发重组
            //                     })
            //         }
            //     }
            // }

            // 5.状态对象作为参数到函数内部，其状态会被剥离
            // SimpleComposeTheme {
            //     Log.d(TAG, "htd 111111111 ")
            //     /**
            //      * 由于这里使用的是 by 表示的 name 只是代理对象的 value 值
            //      * 所以 ProcessedName1(name) 函数中传入的 name 是没有状态的，只是一个普通的 string
            //      * 因此，当点击后，触发重组的范围是 name 使用的位置，也就是在 SimpleComposeTheme {} 重新执行
            //      * 但是在 ProcessedName1(name) 中虽然使用到了 name，但此时传入的 name 状态已经剥离，所以不会重组
            //      * 但是还是能执行到 ProcessedName1 函数内部，只是相当于一个普通的字符串
            //      * val processedName2 = remember(name) {
            //      *     name.uppercase()
            //      * }
            //      * 检测到 name 的值发生了变化，所以会刷新界面
            //      */
            //     var name by remember {
            //         mutableStateOf("huangtiandi")
            //     }.apply {
            //         Log.d(TAG, "htd name ${this.value}")
            //     }
            //
            //     Log.d(TAG, "htd 222222 ")
            //     ProcessedName1(name) {
            //         name = "hahaha"
            //     }
            // }

            // 6.状态对象作为参数到函数内部，其状态会被剥离
            // SimpleComposeTheme {
            //     Log.d(TAG, "htd 111111111 ")
            //     /**
            //      * 由于这里使用的是 by 表示的 name 只是代理对象的 value 值
            //      * 所以 ProcessedName2(name) 函数中传入的 name 是没有状态的，只是一个普通的 string
            //      * 因此，当点击后，触发重组的范围是 name 使用的位置，也就是在 SimpleComposeTheme {} 重新执行
            //      * 但是在 ProcessedName2(name) 中虽然使用到了 name，但此时传入的 name 状态已经剥离，所以不会重组
            //      * 但是还是能执行到 ProcessedName2 函数内部，只是相当于一个普通的字符串
            //      * val processedName1 by remember {
            //      *    derivedStateOf {
            //      *         name.uppercase()
            //      *    }
            //      * }
            //      * 由于 name 是普通字符串，无状态，所以 derivedStateOf 内部检测不到，所以不会刷新界面
            //      */
            //     var name by remember {
            //         mutableStateOf("huangtiandi")
            //     }.apply {
            //         Log.d(TAG, "htd name ${this.value}")
            //     }
            //
            //     Log.d(TAG, "htd 222222 ")
            //     ProcessedName2(name) {
            //         name = "hahaha"
            //     }
            // }

            // 7.直接状态对象作为参数到函数内部
            // SimpleComposeTheme {
            //     Log.d(TAG, "htd 111111111 ")
            //     /**
            //      * 既然状态对象使用 by 作为参数其状态会被剥离，我们可以直接传入状态对象
            //      * 这里可以直接使用 = 号，并将状态的 name 直接作为参数传入
            //      *
            //      * 这个时候，在 ProcessedName3(name) 内部的 name 就是一个状态对象了
            //      * val processedName1 by remember {
            //      *    derivedStateOf {
            //      *       name.value.uppercase()
            //      *    }
            //      * }
            //      * 使用 derivedStateOf 也能监听到内部数据的变化了，显然界面也可以刷新
            //      */
            //     var name = remember {
            //         mutableStateOf("huangtiandi")
            //     }.apply {
            //         Log.d(TAG, "htd name ${this.value}")
            //     }
            //
            //     Log.d(TAG, "htd 222222 ")
            //     ProcessedName3(name) {
            //         name.value = "hahaha"
            //     }
            // }

            // 8.直接状态对象作为参数到函数内部 + List
            SimpleComposeTheme {
                Log.d(TAG, "htd 111111111 ")

                var name = remember {
                    mutableStateListOf("zhangsan", "lisi")
                }.apply {
                    Log.d(TAG, "htd name ${this}")
                }

                Log.d(TAG, "htd 222222 ")
                ProcessedName4(name) {
                    name.add("hahaha")
                }
            }
        }
    }
}

@Composable
private fun ProcessedName1(name: String, onClick: () -> Unit) {
    Log.d(TAG, "htd 33333 ")

    val processedName1 by remember {
        derivedStateOf {
            name.uppercase()
        }
    }

    val processedName2 = remember(name) {
        name.uppercase()
    }
    Text(text = processedName2, fontSize = 24.sp, color = Color.Red,
        modifier = Modifier
            .padding(10.dp, 40.dp)
            .clickable(onClick = onClick))
}

@Composable
private fun ProcessedName2(name: String, onClick: () -> Unit) {
    Log.d(TAG, "htd 33333 ")

    val processedName1 by remember {
        derivedStateOf {
            name.uppercase()
        }
    }

    val processedName2 = remember(name) {
        name.uppercase()
    }
    Text(
        text = processedName1, fontSize = 24.sp, color = Color.Red,
        modifier = Modifier
            .padding(10.dp, 40.dp)
            .clickable(onClick = onClick)
    )

}

@Composable
private fun ProcessedName3(name: State<String>, onClick: () -> Unit) {
    Log.d(TAG, "htd 33333 ")

    val processedName1 by remember {
        derivedStateOf {
            name.value.uppercase()
        }
    }

    Text(
        text = processedName1, fontSize = 24.sp, color = Color.Red,
        modifier = Modifier
            .padding(10.dp, 40.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun ProcessedName4(name: List<String>, onClick: () -> Unit) {
    Log.d(TAG, "htd 33333 ")

    /**
     * 因为该方式的 remember 没有参数，所以后续就算 name 对象发生了变化也感知不到
     * 获取的只是第一次进入的缓存值
     * 所以该方式不可取
     */
    val processedName1 by remember {
        derivedStateOf {
            name.map {
                it.uppercase()
            }
        }
    }

    /**
     * 此时，如果传入的 name 对象本身没有发生变化
     * 不会进入内部，该方式不可取
     */
    val processedName2 = remember(name) {
        name.map {
            it.uppercase()
        }
    }

    /**
     * 因此，使用带参数的 remember + derivedStateOf
     * 当参数 name 对象发生变化时，remember 会执行
     * 当 name 内部状态发生变化时， 会执行 derivedStateOf
     */
    val processedName3 by remember(name) {
        derivedStateOf {
            name.map {
                it.uppercase()
            }
        }
    }

    Column {
        for (name in processedName3) {
            Text(
                text = name, fontSize = 24.sp, color = Color.Red,
                modifier = Modifier
                    .padding(10.dp, 40.dp)
                    .clickable(onClick = onClick)
            )
        }
    }
    Button(onClick = { /*TODO*/ }) {
        
    }
}


