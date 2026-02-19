package com.example.bottomsheetbug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true,
                )
                val scope = rememberCoroutineScope()
                var showSheet by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        Button(onClick = { showSheet = true }) {
                            Text("Show Bottom Sheet")
                        }
                    }

                    if (showSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showSheet = false },
                            sheetState = sheetState,
                            dragHandle = null,
                            contentWindowInsets = {
                                val density = LocalDensity.current
                                val layoutDirection = LocalLayoutDirection.current
                                WindowInsets(
                                    bottom = 0,
                                    top = BottomSheetDefaults.windowInsets.getTop(density),
                                    right = BottomSheetDefaults.windowInsets.getRight(
                                        density,
                                        layoutDirection,
                                    ),
                                    left = 0,
                                )
                            },
                        ) {
                            SampleBottomSheetContent(
                                onDismiss = {
                                    scope.launch {
                                        sheetState.hide()
                                        showSheet = false
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
