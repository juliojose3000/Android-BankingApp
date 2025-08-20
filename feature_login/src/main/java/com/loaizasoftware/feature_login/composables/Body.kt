package com.loaizasoftware.feature_login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.loaizasoftware.core_ui.anim.SlideSwitcherAnim
import com.loaizasoftware.core_ui.extensions.noRippleClickable
import com.loaizasoftware.core_ui.resources.BankingColors
import com.loaizasoftware.feature_login.BiometricAuthState
import com.loaizasoftware.feature_login.LoginViewModel
import com.loaizasoftware.feature_login.R
import com.loaizasoftware.feature_login.biometric.BiometricAuthManager
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Body(
    modifier: Modifier,
    viewModel: LoginViewModel,
    setUsernameValue: (String) -> Unit,
    usernameTextFieldValue: StateFlow<String>,
    setPasswordValue: (String) -> Unit,
    passwordTextFieldValue: StateFlow<String>
) {

    val username by usernameTextFieldValue.collectAsState() //In Jetpack Compose, you should collect flows using collectAsState(), so the composition can automatically update when the value changes.
    val password by passwordTextFieldValue.collectAsState()
    var signInWithPassword by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val activity = context as FragmentActivity
    val biometricAuthState by viewModel.biometricAuthState.collectAsState()

    //By providing activity as a key to remember, you're telling Compose that if activity changes, the remember block should be re-executed, and a new BiometricAuthManager should be created.
    val biometricAuthManager = remember(activity) { BiometricAuthManager(activity) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row {

            Icon(
                painter = painterResource(id = R.drawable.nights_stay),
                contentDescription = "",
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.greeting),
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Medium)
            )

        }

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            LabeledInputField(
                textLabel = stringResource(R.string.user),
                textFieldPlaceholder = stringResource(R.string.placeholder_username),
                textFieldValue = username,
                setTextFieldValue = setUsernameValue
            )

            //Switches between password and fingerprint sign-in methods
            SlideSwitcherAnim(
                showFirst = signInWithPassword,
                firstContent = {
                    SignInWithPassword(password = password, setPasswordValue = setPasswordValue) {
                        signInWithPassword = false
                    }
                },
                secondContent = {
                    SignInWithFingerprint(
                        labelAction = {
                            signInWithPassword = true
                        }, signInWithFingerprintClick = {

                            biometricAuthManager.authenticate(
                                onSuccess = { viewModel.onBiometricAuthSuccess() },
                                onError = { error -> viewModel.onBiometricAuthError(error) },
                                onFailed = { viewModel.onBiometricAuthFailed() }
                            )

                        }
                    )
                }
            )

            // Handle biometric auth states if needed
            when (biometricAuthState) {
                is BiometricAuthState.Success -> {
                    LaunchedEffect(Unit) {
                        // Reset state after handling
                        viewModel.resetBiometricAuthState()
                        viewModel.showToastMessage.emit("Ok")
                    }
                }

                is BiometricAuthState.Error -> {
                    LaunchedEffect(Unit) {
                        // Reset state after handling
                        viewModel.resetBiometricAuthState()
                        viewModel.showToastMessage.emit((biometricAuthState as BiometricAuthState.Error).message)
                    }
                }

                is BiometricAuthState.Failed -> {
                    LaunchedEffect(Unit) {
                        viewModel.resetBiometricAuthState()
                    }
                }

                BiometricAuthState.Idle -> { /* Do nothing */ }
            }

        }
    }

}

@Composable
fun SignInWithFingerprint(labelAction: () -> Unit, signInWithFingerprintClick: () -> Unit) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.with_fingerprint),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontWeight = FontWeight(600)
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(BankingColors.LightBlue),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(id = R.drawable.fingerprint),
                contentDescription = "",
                modifier = Modifier
                    .clickable { signInWithFingerprintClick() }
                    .size(40.dp),
                tint = BankingColors.Blue,
            )

        }

        ClickableLabel(stringResource(R.string.with_password)) {
            labelAction()
        }

    }

}

@Composable
fun SignInWithPassword(
    password: String,
    setPasswordValue: (String) -> Unit,
    labelAction: () -> Unit
) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        LabeledInputField(
            textLabel = stringResource(R.string.password),
            textFieldPlaceholder = stringResource(R.string.placeholder_password),
            textFieldValue = password,
            setTextFieldValue = setPasswordValue
        )

        ClickableLabel(
            stringResource(R.string.cannot_sign_in),
            horizontalArrangement = Arrangement.End
        ) {
            //Add action
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {},
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = BankingColors.Blue
            )
        ) {
            Text(
                text = stringResource(R.string.sign_in),
            )
        }

        ClickableLabel(
            text = stringResource(R.string.or_with_fingerprint),
            trailingIcon = R.drawable.fingerprint
        ) {
            labelAction()
        }

    }

}

@Composable
fun ClickableLabel(
    text: String,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    trailingIcon: Int? = null,
    action: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .noRippleClickable { action() },
            text = text,
            color = BankingColors.Blue,
            fontWeight = FontWeight(600)
        )

        if (trailingIcon != null) {
            Icon(
                painter = painterResource(trailingIcon),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(15.dp),
                tint = BankingColors.Blue
            )
        }

    }

}

@Composable
fun LabeledInputField(
    textLabel: String,
    textFieldPlaceholder: String,
    textFieldValue: String,
    setTextFieldValue: (String) -> Unit
) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = textLabel,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    /*brush = Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Green) // example gradient
                    )*/
                    brush = Brush.linearGradient(
                        colors = listOf(Color.LightGray, Color.LightGray)
                    )
                ),
            value = textFieldValue,
            onValueChange = setTextFieldValue,
            placeholder = { Text(textFieldPlaceholder) },
            trailingIcon = {

                if (textFieldValue.isNotEmpty()) {
                    IconButton(onClick = {
                        setTextFieldValue("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = ""
                        )
                    }
                }

            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,       // Border color when not focused
                focusedBorderColor = Color.LightGray,         // Border color when focused
                //disabledBorderColor = Color.LightGray,
                errorBorderColor = Color.Red             // Optional, if you handle error state
            ),
            shape = RectangleShape, // ⬅️ Removes rounded corners
        )

    }

}

/*@Preview
@Composable
fun PreviewBody() {


    class MockViewModel: LoginViewModel()

    Body(
        viewModel = MockViewModel(),
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        setUsernameValue = {},
        usernameTextFieldValue = MutableStateFlow(""),
        setPasswordValue = {},
        passwordTextFieldValue = MutableStateFlow(""),

    )
}*/