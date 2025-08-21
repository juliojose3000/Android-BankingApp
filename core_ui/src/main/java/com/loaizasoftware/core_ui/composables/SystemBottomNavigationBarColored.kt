package com.loaizasoftware.core_ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

/**
 * A composable that creates a custom colored overlay for the system bottom navigation bar area.
 *
 * This composable addresses the visual inconsistency that can occur when the system navigation
 * bar has a different background color than your app's content. It creates a colored overlay
 * that sits on top of the system navigation bar area to ensure visual consistency.
 *
 * **Use Cases:**
 * - When you need a solid color navigation bar that matches your app's theme
 * - To prevent content from being visually cut off by transparent navigation bars
 * - To maintain consistent branding colors throughout your app's interface
 *
 * **Implementation Details:**
 * - Uses [WindowInsets.navigationBars] to get the exact system navigation bar height
 * - Creates a colored overlay box positioned at the bottom of the screen
 * - The overlay sits on top of your content, ensuring the navigation bar area appears solid
 *
 * @param navigationBarColor The color to apply to the navigation bar area overlay.
 *                          Defaults to [Color.Black] if not specified.
 * @param content The composable content to display above the navigation bar overlay.
 *               This is typically your main screen content.
 *
 * @sample
 * ```kotlin
 * SystemBottomNavigationBarColored(
 *     navigationBarColor = MaterialTheme.colorScheme.surface
 * ) {
 *     YourScreenContent()
 * }
 * ```
 *
 * **Note:** This composable should be used at the screen level, wrapping your main content.
 * It will occupy the full available space and position the overlay correctly.
 *
 * @see WindowInsets.navigationBars For system navigation bar insets
 * @see LocalDensity For density-aware measurements
 */
@Composable
fun SystemBottomNavigationBarColored(
    navigationBarColor: Color = Color.Black,
    content: @Composable () -> Unit
) {
    // Get the current screen density for accurate pixel-to-dp conversion
    val density = LocalDensity.current

    // Calculate the system navigation bar height in dp
    // This ensures the overlay matches the exact system bar dimensions
    val navBarHeightDp = with(density) {
        WindowInsets.navigationBars.getBottom(density).toDp()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Render the main content first (bottom layer)
        content()

        // Create a colored overlay that covers the navigation bar area
        // This ensures a consistent background color regardless of system settings
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(navBarHeightDp)
                .background(navigationBarColor)
        )
    }
}