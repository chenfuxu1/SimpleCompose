package com.cfx.simplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class OnPlacedModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Modifier.onPlaced { layoutCoordinates ->
                            val pos = layoutCoordinates.positionInParent()
                            // if (pos.x > xxx) {
                            //     // 业务逻辑
                            // }
                        }.layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(0, 0) {}
                        }
                    }
                }
            }
        }
    }
}