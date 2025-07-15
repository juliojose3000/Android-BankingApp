package com.loaizasoftware.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loaizasoftware.core.utils.UiState
import com.loaizasoftware.domain.models.SignInRequest
import com.loaizasoftware.domain.usecases.UserSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val signInUseCase: UserSignInUseCase) :
    ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Success(Unit))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun signIn(username: String, password: String) {

        _uiState.value = UiState.Loading

        viewModelScope.launch {

            signInUseCase(SignInRequest(username, password))
                .fold(
                    onSuccess = {
                        _uiState.value = UiState.Success(it)
                    }, onFailure = {
                        _uiState.value = UiState.Error(it.message ?: "Unknown error")
                    })

        }

    }

}