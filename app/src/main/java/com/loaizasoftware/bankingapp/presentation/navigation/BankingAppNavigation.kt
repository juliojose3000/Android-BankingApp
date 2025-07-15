package com.loaizasoftware.bankingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loaizasoftware.feature_login.LoginScreen
import com.loaizasoftware.feature_login.LoginViewModel

@Composable
fun BankingAppNavigation(viewModel: LoginViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SIGN_IN_SCREEN.route) {

        composable(Routes.SIGN_IN_SCREEN.route) {
            LoginScreen(viewModel)
        }

    }

}