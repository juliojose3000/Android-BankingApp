package com.loaizasoftware.feature_login

import androidx.lifecycle.viewModelScope
import com.loaizasoftware.core.base.BaseViewModel
import com.loaizasoftware.core.utils.UiState
import com.loaizasoftware.domain.models.SignInRequest
import com.loaizasoftware.domain.usecases.UserSignInUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//@HiltViewModel
open class LoginViewModel /*@Inject constructor*/(private val signInUseCase: UserSignInUseCase) :
    BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Success(Unit))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _usernameTextFieldMutableState = MutableStateFlow("")
    val usernameTextFieldMutableState: StateFlow<String> = _usernameTextFieldMutableState.asStateFlow()

    private val _passwordTextFieldMutableState = MutableStateFlow("")
    val passwordTextFieldMutableState: StateFlow<String> = _passwordTextFieldMutableState.asStateFlow()

    private val _biometricAuthState = MutableStateFlow<BiometricAuthState>(BiometricAuthState.Idle)
    val biometricAuthState: StateFlow<BiometricAuthState> = _biometricAuthState.asStateFlow()

    val showToastMessage = MutableSharedFlow<String>()

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

    fun setUsernameValue(value: String) {
        _usernameTextFieldMutableState.value = value
    }

    fun setPasswordValue(value: String) {
        _passwordTextFieldMutableState.value = value
    }

    fun onBiometricAuthSuccess() {
        _biometricAuthState.value = BiometricAuthState.Success
        // Handle successful biometric authentication
        //TODO Navigate to main screen
    }

    fun onBiometricAuthError(error: String) {
        _biometricAuthState.value = BiometricAuthState.Error(error)
    }

    fun onBiometricAuthFailed() {
        _biometricAuthState.value = BiometricAuthState.Failed
    }

    fun resetBiometricAuthState() {
        _biometricAuthState.value = BiometricAuthState.Idle
    }

}

sealed class BiometricAuthState {
    data object Idle : BiometricAuthState()
    data object Success : BiometricAuthState()
    data object Failed : BiometricAuthState()
    data class Error(val message: String) : BiometricAuthState()
}