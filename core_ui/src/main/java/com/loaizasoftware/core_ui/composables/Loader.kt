package com.loaizasoftware.core_ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Loader() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Blue
        )

    }

}

@Composable
@Preview
fun PreviewLoader() {
    Loader()
}