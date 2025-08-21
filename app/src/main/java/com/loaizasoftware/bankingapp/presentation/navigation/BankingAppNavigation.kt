package com.loaizasoftware.bankingapp.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loaizasoftware.core_ui.composables.SystemBottomNavigationBarColored
import com.loaizasoftware.feature_login.LoginScreen
import com.loaizasoftware.feature_login.LoginViewModel

@Composable
fun BankingAppNavigation(viewModel: LoginViewModel) {

    val navController = rememberNavController()

    // Get the current Density, which is required for toDp()
    val density = LocalDensity.current

    // Use a SideEffect or launch a coroutine to get the value
    // as it's not a state and might be called in a different context.
    val navBarHeightDp = with(density) { WindowInsets.navigationBars.getBottom(density).toDp() }

    NavHost(navController = navController, startDestination = Routes.SIGN_IN_SCREEN.route) {

        composable(Routes.SIGN_IN_SCREEN.route) {
            SystemBottomNavigationBarColored {
                LoginScreen(viewModel = viewModel, navBarHeightDp = navBarHeightDp)
            }
        }

    }

}