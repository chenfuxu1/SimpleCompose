package com.cfx.simplecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.modifier.ModifierLocalConsumer
import androidx.compose.ui.modifier.ModifierLocalProvider
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.modifier.ProvidableModifierLocal
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class ModifierLocalActivity : ComponentActivity() {
    companion object {
        const val TAG = "ModifierLocalActivity"
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val sharedKey = modifierLocalOf { "空" }
                    Modifier
                        .modifierLocalProvider(sharedKey) {
                            "荒天帝"
                        }
                        .modifierLocalConsumer {
                            Log.d(TAG, "htd sharedKey.current：${sharedKey.current}")
                        }

                    val sharedWidthKey = modifierLocalOf { arrayOf("0") }
                    Column(Modifier.padding(innerPadding)) {
                        Modifier
                            .then(object : LayoutModifier,
                                ModifierLocalProvider<Array<String>> {
                                val widthString = arrayOf("")

                                override fun MeasureScope.measure(
                                    measurable: Measurable,
                                    constraints: Constraints
                                ): MeasureResult {
                                    val placeable = measurable.measure(constraints)
                                    widthString[0] = placeable.width.toString()
                                    return layout(placeable.width, placeable.height) {
                                        placeable.placeRelative(0, 0)
                                    }
                                }

                                override val key: ProvidableModifierLocal<Array<String>>
                                    get() = sharedWidthKey
                                override val value: Array<String>
                                    get() = widthString

                            })
                            .then(object : LayoutModifier, ModifierLocalConsumer {
                                lateinit var sharedString: String

                                override fun MeasureScope.measure(
                                    measurable: Measurable,
                                    constraints: Constraints
                                ): MeasureResult {
                                    val placeable = measurable.measure(constraints)
                                    // 使用 sharedString 值
                                    return layout(placeable.width, placeable.height) {
                                        placeable.placeRelative(0, 0)
                                    }
                                }

                                override fun onModifierLocalsUpdated(scope: ModifierLocalReadScope) =
                                    with(scope) {
                                        sharedString = sharedWidthKey.current[0]
                                        println("$TAG htd current $sharedString")
                                    }
                            })

                        /**
                         * windowInsetsPadding 实现了 ModifierLocalConsumer, ModifierLocalProvider
                         * 连续调用了，中间的 windowInsetsPadding 既是 Consumer 又是下游的Provider
                         */
                        Modifier.windowInsetsPadding(WindowInsets(4.dp, 4.dp, 4.dp, 4.dp))
                            .windowInsetsPadding(WindowInsets(4.dp, 4.dp, 4.dp, 4.dp))
                            .windowInsetsPadding(WindowInsets(4.dp, 4.dp, 4.dp, 4.dp))
                    }
                }
            }
        }
    }
}