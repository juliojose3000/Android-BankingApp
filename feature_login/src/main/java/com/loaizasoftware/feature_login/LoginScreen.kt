package com.loaizasoftware.feature_login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loaizasoftware.core.utils.UiState

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->

        when(uiState) {

            is UiState.Error -> {
                //Show error
            }

            is UiState.Loading -> {
                //Show Loader
            }

            is UiState.Success -> {
                BuildUI(paddingValues = padding) {
                    viewModel.signIn("juliojose3000", "12345")
                }
            }

        }

    }

}

@Composable
fun BuildUI(paddingValues: PaddingValues, onClick: () -> Unit, ) {

    Column(modifier = Modifier.padding(paddingValues)) {

        Button(onClick = onClick) {
            Text("Sign In")
        }

    }

}


@Composable
@Preview
fun PreviewBuildUI() {

    BuildUI(PaddingValues(0.dp)){}

}