package com.cfx.customsideeffect

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cfx.customsideeffect.ui.theme.SimpleComposeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 非协程使用 DisposableEffect
 * 协程中使用 LaunchedEffect
 *
 * 简化版的包装函数 produceState
 */
class ProduceStateActivity : ComponentActivity() {
    private var mGeoManager: GeoManager? = null
    private val mPositionData: LiveData<Point> = MutableLiveData<Point>()
    private val mPositionState: StateFlow<Point> = MutableStateFlow(Point(0, 0))

    @SuppressLint("ProduceStateDoesNotAssignValue")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {

                    }

                    var position by remember {
                        mutableStateOf(Point(0, 0))
                    }

                    DisposableEffect(Unit) {
                        val callback: (pos: Point) -> Unit = { newPos ->
                            position = newPos
                        }
                        // 订阅位置信息
                        mGeoManager?.register(callback)
                        onDispose {
                            // 取消订阅信息
                            mGeoManager?.unregister(callback)
                        }
                    }

                    DisposableEffect(Unit) {
                        val observer = Observer<Point> { newPos ->
                            position = newPos
                        }
                        // 订阅位置信息
                        mPositionData.observe(this@ProduceStateActivity, observer)
                        onDispose {
                            // 取消订阅信息
                            mPositionData.removeObserver(observer)
                        }
                    }

                    /**
                     * fun <R, T : R> LiveData<T>.observeAsState(initial: R): State<R> {
                     *     val lifecycleOwner = LocalLifecycleOwner.current
                     *     val state = remember {
                     *         @Suppress("UNCHECKED_CAST") /* Initialized values of a LiveData<T> must be a T */
                     *         mutableStateOf(if (isInitialized) value as T else initial)
                     *     }
                     *     DisposableEffect(this, lifecycleOwner) {
                     *         val observer = Observer<T> { state.value = it }
                     *         observe(lifecycleOwner, observer)
                     *         onDispose { removeObserver(observer) }
                     *     }
                     *     return state
                     * }
                     *
                     * 可以自动订阅，在使用时返回订阅的最新值
                     * 且 onDispose 时可以自动取消订阅
                     *
                     */
                    mPositionData.observeAsState()

                    /**
                     * 协程中使用
                     */
                    LaunchedEffect(Unit) {
                        mPositionState.collect { newPos ->
                            position = newPos
                        }
                    }

                    val producedState = produceState(Point(0, 0)) {
                        mPositionState.collect { newPos ->
                            value = newPos
                        }
                    }

                    // 等价于上述代码
                    val positionStateFromFlow = mPositionState.collectAsState()
                }
            }
        }
    }
}

interface GeoManager {
    fun register(newPos: (pos: Point) -> Unit)

    fun unregister(newPos: (pos: Point) -> Unit)
}