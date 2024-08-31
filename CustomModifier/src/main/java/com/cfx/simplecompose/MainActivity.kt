package com.cfx.simplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SimpleComposeTheme {

            }

            Row {

                Image(rememberImagePainter("https://p3.itc.cn/images01/20230812/380059d6f55541688676afdaa0d35a3c.jpeg"), "", Modifier.size(128.dp))
                Text(text = "张三", Modifier.padding(top = 20.dp))
            }

            val names = listOf("荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪","荒天帝", "叶天帝", "云溪", "荒天帝", "叶天帝", "云溪")
            LazyColumn {
                item {
                    Image(rememberImagePainter("https://p3.itc.cn/images01/20230812/380059d6f55541688676afdaa0d35a3c.jpeg"), "", Modifier.size(128.dp))
                    Text(text = "张三", Modifier.padding(top = 20.dp))
                }
                items(names) {name ->
                    Box {
                        Text(text = name)
                    }
                }
                item {
                    Image(rememberImagePainter("https://p3.itc.cn/images01/20230812/380059d6f55541688676afdaa0d35a3c.jpeg"), "", Modifier.size(128.dp))
                    Text(text = "张三", Modifier.padding(top = 20.dp))
                }
            }

            /**
             * 1.compose 没有 margin，都是通过 padding 设置，可以根据调用顺序来实现 margin，先调用了 padding 就类似于 margin
             * 此外 Modifier 还可以链式调用
             * 2.compose 控件默认高度是 wrap_content, 可以通过 fillMaxHeight 设置 match_parent
             * 3.通用的设置用 Modifier，专项的设置用函数参数
             * 例如：padding、背景色等用 Modifier，文字的大小，颜色用 Text 控件自带的方法
             */
            Row(
                Modifier
                    // .fillMaxHeight()
                    .padding(top = 30.dp, start = 10.dp) // 外部 padding
                    .background(Color.Red, RoundedCornerShape(8.dp))
                    .padding(8.dp)) { // 内部 padding
                Image(
                    rememberImagePainter("https://p3.itc.cn/images01/20230812/380059d6f55541688676afdaa0d35a3c.jpeg"),
                    "",
                    Modifier.size(128.dp).clip(CircleShape)
                )
                Text(text = "张三",
                    Modifier

                        .padding(20.dp)
                        .background(Color.Green)
                        .padding(20.dp)
                        .clickable {  }
                    ,
                    fontSize = 24.sp,
                    color = Color.Red)
            }
        }
    }
}