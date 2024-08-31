package com.cfx.custommutablestate

import android.os.Bundle
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfx.custommutablestate.ui.theme.SimpleComposeTheme

/**
 * 重组的性能风险和智能优化
 */
class ReComposeScopeActivity : ComponentActivity() {
    companion object {
        const val TAG = "ReComposeScopeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var name by mutableStateOf("荒天帝")
        var user = User("荒天帝")
        var user2 = User2("荒天帝")
        var user3 = User3("荒天帝")
        var user4 = User4("荒天帝")
        var user5 = User5("荒天帝")

        setContent {
            println("$TAG test 1")
            Column(Modifier.padding(10.dp, 40.dp)) {
                println("$TAG test 2")

                // HeavyWork()
                // HeavyWork2(name)
                // Text(
                //     text = name,
                //     fontSize = 24.sp,
                //     color = Color.Red,
                //     modifier = Modifier.clickable {
                //         name = "云溪"
                //     })

                // HeavyWork3(user)
                // Text(
                //     text = name,
                //     fontSize = 24.sp,
                //     color = Color.Red,
                //     modifier = Modifier.clickable {
                //         name = "云溪"
                //         user = User("荒天帝")
                //     })

                // HeavyWork4(user2)
                // Text(
                //     text = name,
                //     fontSize = 24.sp,
                //     color = Color.Red,
                //     modifier = Modifier.clickable {
                //         name = "云溪"
                //         user2 = User2("荒天帝")
                //     })

                // HeavyWork5(user3)
                // Text(
                //     text = name,
                //     fontSize = 24.sp,
                //     color = Color.Red,
                //     modifier = Modifier.clickable {
                //         name = "云溪"
                //         user3 = User3("荒天帝")
                //     })

                // HeavyWork6(user4)
                // Text(
                //     text = name,
                //     fontSize = 24.sp,
                //     color = Color.Red,
                //     modifier = Modifier.clickable {
                //         name = "云溪"
                //     })

                HeavyWork7(user5)
                Text(
                    text = name,
                    fontSize = 24.sp,
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        name = "云溪"
                    })
            }
        }
    }
}

@Composable
fun HeavyWork() {
    println("ReComposeScopeActivity test HeavyWork")
    Text(
        text = "HeavyWork",
        fontSize = 24.sp,
        color = Color.Red)
}

@Composable
fun HeavyWork2(text: String) {
    println("ReComposeScopeActivity test HeavyWork2")
    Text(
        text = "HeavyWork2 $text",
        fontSize = 24.sp,
        color = Color.Red)
}

@Composable
fun HeavyWork3(user: User) {
    println("ReComposeScopeActivity test HeavyWork3")
    Text(
        text = "HeavyWork3 ${user.name}",
        fontSize = 24.sp,
        color = Color.Red)
}

@Composable
fun HeavyWork4(user: User2) {
    println("ReComposeScopeActivity test HeavyWork4")
    Text(
        text = "HeavyWork4 ${user.name}",
        fontSize = 24.sp,
        color = Color.Red)
}

@Composable
fun HeavyWork5(user: User3) {
    println("ReComposeScopeActivity test HeavyWork5")
    Text(
        text = "HeavyWork5 ${user.name}",
        fontSize = 24.sp,
        color = Color.Red)
}

@Composable
fun HeavyWork6(user: User4) {
    println("ReComposeScopeActivity test HeavyWork6")
    Text(
        text = "HeavyWork6 ${user.name}",
        fontSize = 24.sp,
        color = Color.Red)
}

@Composable
fun HeavyWork7(user: User5) {
    println("ReComposeScopeActivity test HeavyWork7")
    Text(
        text = "HeavyWork7 ${user.name}",
        fontSize = 24.sp,
        color = Color.Red)
}

class User(val name: String) {
    override fun equals(other: Any?): Boolean {
        return false
    }
}

data class User2(var name: String) {

}

@Stable
data class User3(var name: String) {

}

@Stable
class User4(var name: String) {

}

/**
 * Compose 判断是否可靠，主要依据第二种
 * 当公开属性改变的时候，需要通知到使用到这个属性的 Composition
 */
class User5(name: String) {
    // 当公开属性改变的时候，需要通知到使用到这个属性的 Composition
    var name by mutableStateOf(name)
}



