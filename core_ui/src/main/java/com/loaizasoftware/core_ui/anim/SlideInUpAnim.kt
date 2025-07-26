package com.loaizasoftware.core_ui.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable

@Composable
fun SlideInUpAnim(visible: Boolean, content: @Composable () -> Unit) {

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            // Slide in from bottom
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 500)
        ) + fadeIn(animationSpec = tween(500)), // Optional fade-in
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 500)
        ) + fadeOut(animationSpec = tween(500))
    ) {
        content()
    }

}