package com.loaizasoftware.bankingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.loaizasoftware.bankingapp.presentation.navigation.BankingAppNavigation
import com.loaizasoftware.bankingapp.presentation.ui.theme.BankingAppTheme
import com.loaizasoftware.feature_login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

//import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //private val loginViewModel by viewModels<LoginViewModel>()
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankingAppTheme {
                BankingAppNavigation(viewModel = loginViewModel)
            }
        }
    }
}