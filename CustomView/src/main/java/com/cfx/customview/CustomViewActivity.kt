package com.cfx.customview

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint as AndroidPaint
import androidx.compose.ui.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.cfx.customview.ui.theme.SimpleComposeTheme
import kotlin.math.roundToInt

/**
 * 自定义 view
 */
class CustomViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /**
                     * 可以在 Box 内绘制， Box 相当于一个白板，什么内容也没有
                     * fun Box(modifier: Modifier) {
                     *     Layout({}, measurePolicy = EmptyBoxMeasurePolicy, modifier = modifier)
                     * }
                     */
                    Box(Modifier.padding(innerPadding))

                    /**
                     * fun Canvas(modifier: Modifier, onDraw: DrawScope.() -> Unit) =
                     *     Spacer(modifier.drawBehind(onDraw))
                     *
                     * fun Spacer(modifier: Modifier) {
                     *     Layout({}, measurePolicy = SpacerMeasurePolicy, modifier = modifier)
                     * }
                     */
                    Canvas(Modifier.size(100.dp)) {

                    }

                    // CustomImage()
                    // CustomImage2()
                    // CustomImage3()
                    // CustomImage4()
                    CustomImage5()
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomText() {
    Text("荒天帝", Modifier.drawBehind {
        /**
         * 默认填满控件范围，也可指定绘制区域
         */
        drawRect(Color.Red)
    })
}

@Preview
@Composable
fun CustomText2() {
    Text("荒天帝", Modifier.drawWithContent {
        /**
         * 绘制背景
         */
        drawRect(Color.Red)
        // 绘制本身内容
        drawContent()
    })
}

@Preview
@Composable
fun CustomText3() {
    Text("荒天帝", Modifier.drawWithContent {
        /**
         * 绘制背景
         */
        drawRect(Color.Red)
        // 绘制本身内容
        drawContent()
        // 绘制横线
        drawLine(
            Color.Cyan,
            Offset(0f, size.height / 2),
            Offset(size.width, size.height / 2),
            2.dp.toPx()
        )
    })
}

@Preview
@Composable
fun CustomImage() {
    val image = ImageBitmap.imageResource(id = R.drawable.a)
    Canvas(Modifier.size(100.dp)) {
        // 二维旋转，绕 z 轴
        rotate(30f) {
            drawImage(image)
        }
    }
}

@Preview
@Composable
fun CustomImage2() {
    val image = ImageBitmap.imageResource(id = R.drawable.a)
    Canvas(
        Modifier
            .size(200.dp)
            .graphicsLayer {
                rotationX = 80f
            }) {
        // 绕 x 轴旋转
        drawImage(image)
    }
}

/**
 * 沿 x 旋转
 * 旋转中心移到图片中心
 */
@Preview
@Composable
fun CustomImage3() {
    val image = ImageBitmap.imageResource(id = R.drawable.a)
    val mPaint by remember {
        mutableStateOf(Paint())
    }
    // 用作三维旋转的 camera
    val mCamera by remember { mutableStateOf(Camera()) }.apply {
        // x 方向旋转，默认是以坐标原点旋转的，需要将旋转中心处于图像中心
        value.rotateX(45f)
    }
    Box(Modifier.padding(50.dp)) {
        Canvas(Modifier.size(150.dp)) {
            drawIntoCanvas {
                // 2.将图像再移回去
                it.translate(size.width / 2, size.height / 2)
                /**
                 * it 是 compose 的 canvas
                 * it.nativeCanvas 是原生的 canvas
                 */
                mCamera.applyToCanvas(it.nativeCanvas)
                // 1.先将图像中心移到坐标原点
                it.translate(-(size.width / 2), -(size.height / 2))
                it.drawImageRect(
                    image,
                    dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt()),
                    paint = mPaint
                )
            }
        }
    }
}

/**
 * 三维旋转
 * 沿 x 旋转，且沿 y 轴旋转
 * 旋转中心移到图片中心
 */
@Preview
@Composable
fun CustomImage4() {
    val image = ImageBitmap.imageResource(id = R.drawable.a)
    val mPaint by remember {
        mutableStateOf(Paint())
    }
    // 用作三维旋转的 camera
    val mCamera by remember { mutableStateOf(Camera()) }.apply {
        // x 方向旋转，默认是以坐标原点旋转的，需要将旋转中心处于图像中心
        value.rotateX(45f)
    }
    Box(Modifier.padding(50.dp)) {
        Canvas(Modifier.size(150.dp)) {
            drawIntoCanvas {
                // 2.将图像再移回去
                it.translate(size.width / 2, size.height / 2)
                // 沿 y 轴旋转
                it.rotate(-45f)
                /**
                 * it 是 compose 的 canvas
                 * it.nativeCanvas 是原生的 canvas
                 */
                mCamera.applyToCanvas(it.nativeCanvas)
                it.rotate(45f)

                // 1.先将图像中心移到坐标原点
                it.translate(-(size.width / 2), -(size.height / 2))
                it.drawImageRect(
                    image,
                    dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt()),
                    paint = mPaint
                )
            }
        }
    }
}

/**
 * 三维旋转动画
 * 沿 x 旋转，且沿 y 轴旋转
 * 旋转中心移到图片中心
 */
@Preview
@Composable
fun CustomImage5() {
    val image = ImageBitmap.imageResource(id = R.drawable.a)
    val mPaint by remember {
        mutableStateOf(Paint())
    }
    val mRotationAnim = remember {
        Animatable(0f)
    }
    // 用作三维旋转的 camera
    val mCamera by remember { mutableStateOf(Camera()) }

    LaunchedEffect(Unit) {
        mRotationAnim.animateTo(360f, infiniteRepeatable(tween(2000)))
    }

    Box(Modifier.padding(50.dp)) {
        Canvas(Modifier.size(150.dp)) {
            drawIntoCanvas {
                // 2.将图像再移回去
                it.translate(size.width / 2, size.height / 2)
                // 沿 y 轴旋转
                it.rotate(-45f)
                /**
                 * it 是 compose 的 canvas
                 * it.nativeCanvas 是原生的 canvas
                 */
                mCamera.save()
                // x 方向旋转，默认是以坐标原点旋转的，需要将旋转中心处于图像中心
                mCamera.rotateX(mRotationAnim.value)
                mCamera.applyToCanvas(it.nativeCanvas)
                mCamera.restore()
                it.rotate(45f)

                // 1.先将图像中心移到坐标原点
                it.translate(-(size.width / 2), -(size.height / 2))
                it.drawImageRect(
                    image,
                    dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt()),
                    paint = mPaint
                )
            }
        }
    }
}

class CustomView(context: Context?, attrs: AttributeSet) : View(context, attrs) {
    private val mPaint = AndroidPaint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = android.graphics.Color.CYAN
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
    }
}