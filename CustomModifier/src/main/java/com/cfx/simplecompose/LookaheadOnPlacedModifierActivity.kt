package com.cfx.simplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.tooling.preview.Preview
import com.cfx.simplecompose.ui.theme.SimpleComposeTheme

class LookaheadOnPlacedModifierActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        /**
                         * 正式测量前的前瞻性测量
                         */
                        // LookaheadLayout(content = {
                        //     Row(Modifier.onPlaced { lookaheadCoordinates, layoutCoordinates ->
                        //
                        //     }) {
                        //
                        //     }
                        // }, measurePolicy = {
                        //
                        // })
                    }
                }
            }
        }
    }
}