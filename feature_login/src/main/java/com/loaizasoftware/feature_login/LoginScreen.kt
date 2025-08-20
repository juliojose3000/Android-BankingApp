package com.loaizasoftware.feature_login

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.loaizasoftware.core.ext.showToast
import com.loaizasoftware.core.utils.UiState
import com.loaizasoftware.core_ui.composables.Loader
import com.loaizasoftware.core_ui.extensions.noRippleClickable
import com.loaizasoftware.core_ui.resources.BankingColors
import com.loaizasoftware.feature_login.composables.MainContainer
import com.loaizasoftware.feature_login.composables.FooterContent
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope() // Add this for coroutine scope

    LaunchedEffect(key1 = Unit) {
        viewModel.showToastMessage.collect {
            context showToast it
        }
    }

    // Animated blur radius based on sheet expansion
    /*val blurRadius by animateFloatAsState(
        targetValue = when (sheetState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> 8f
            SheetValue.PartiallyExpanded -> 0f
            SheetValue.Hidden -> 0f
        },
        animationSpec = tween(durationMillis = 300),
        label = "blur_animation"
    )

    // Animated overlay alpha
    val overlayAlpha by animateFloatAsState(
        targetValue = when (sheetState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> 0.2f
            SheetValue.PartiallyExpanded -> 0.1f
            SheetValue.Hidden -> 0f
        },
        animationSpec = tween(durationMillis = 150),
        label = "overlay_animation"
    )*/


    var isDragging by remember { mutableStateOf(false) }

    // Monitor dragging by observing transitions between states
    LaunchedEffect(sheetState.bottomSheetState) {
        snapshotFlow {
            Pair(
                sheetState.bottomSheetState.currentValue,
                sheetState.bottomSheetState.targetValue
            )
        }.collect { (current, target) ->
            isDragging = current != target
        }
    }

    // You can tweak this to make it animated or proportional
    val blurRadius by animateFloatAsState(
        targetValue = if (isDragging || sheetState.bottomSheetState.currentValue == SheetValue.Expanded) 16f else 0f,
        animationSpec = tween(300),
        label = "blur_anim"
    )

    val overlayAlpha by animateFloatAsState(
        targetValue = if (blurRadius > 0f) 0.2f else 0f,
        animationSpec = tween(300),
        label = "alpha_anim"
    )

    // BottomSheetScaffold should be the root composable
    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize() // Apply the modifier to the scaffold
            .background(Color.Blue),
        scaffoldState = sheetState, // Pass the state
        sheetContent = {
            FooterContent(
                onItemClick = { action ->
                    // Expand when footer item is clicked
                    scope.launch {
                        if (!sheetState.bottomSheetState.isVisible) {
                            sheetState.bottomSheetState.expand()
                        } else {
                            sheetState.bottomSheetState.partialExpand()
                        }
                    }
                },
                isExpanded = sheetState.bottomSheetState.isVisible
            )
        },
        sheetPeekHeight = 110.dp, // Height of the visible bottom sheet
        sheetContainerColor = Color.White,
        containerColor = BankingColors.WhiteBackground,
        sheetDragHandle = {
            // Optional: Add a drag handle to indicate it's draggable
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .width(80.dp)
                    .height(4.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.3f),
                        RoundedCornerShape(2.dp)
                    )
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .graphicsLayer {
                renderEffect = if (blurRadius > 0f && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    RenderEffect
                        .createBlurEffect(
                            blurRadius,
                            blurRadius,
                            Shader.TileMode.CLAMP
                        )
                        .asComposeRenderEffect()
                } else {
                    null
                }
            }
        ) {

            // Your main content
            when (uiState) {
                is UiState.Error -> {
                    context showToast uiState.error
                }

                is UiState.Loading -> {
                    Loader()
                }

                is UiState.Success -> {
                    MainContainer(
                        viewModel = viewModel,
                        paddingValues = padding,
                        onClick = { },
                        setUsernameValue = viewModel::setUsernameValue,
                        usernameTextFieldValue = viewModel.usernameTextFieldMutableState,
                        setPasswordValue = viewModel::setPasswordValue,
                        passwordTextFieldValue = viewModel.passwordTextFieldMutableState
                    )
                }
            }

        }

        // ✅ Overlay sits inside the content layer now
        if (overlayAlpha > 0.1f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = overlayAlpha))
                    .noRippleClickable {
                        scope.launch {
                            sheetState.bottomSheetState.partialExpand()
                        }
                    }
            )

        }

    }

}

/*@Composable
@Preview
fun PreviewBuildUI() {

    MainContainer(
        paddingValues = PaddingValues(0.dp),
        onClick = {},
        setUsernameValue = {},
        usernameTextFieldValue = MutableStateFlow(""),
        setPasswordValue = {},
        passwordTextFieldValue = MutableStateFlow(""),
    )

}*/