package com.loaizasoftware.core_ui.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable

@Composable
fun SlideSwitcherAnim(
    showFirst: Boolean,
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit
) {
    Box {
        AnimatedVisibility(
            visible = showFirst,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(500)),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight / 3 },
                animationSpec = tween(500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            firstContent()
        }

        AnimatedVisibility(
            visible = !showFirst,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(500)),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight / 3 },
                animationSpec = tween(500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            secondContent()
        }
    }
}
