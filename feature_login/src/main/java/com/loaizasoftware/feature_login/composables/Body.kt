package com.loaizasoftware.feature_login.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loaizasoftware.core_ui.anim.SlideInUpAnim
import com.loaizasoftware.core_ui.anim.SlideSwitcherAnim
import com.loaizasoftware.core_ui.resources.BankingColors
import com.loaizasoftware.feature_login.R
import com.loaizasoftware.feature_login.noRippleClickable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Body(
    modifier: Modifier,
    setUsernameValue: (String) -> Unit,
    usernameTextFieldValue: StateFlow<String>,
    setPasswordValue: (String) -> Unit,
    passwordTextFieldValue: StateFlow<String>
) {

    val username by usernameTextFieldValue.collectAsState() //In Jetpack Compose, you should collect flows using collectAsState(), so the composition can automatically update when the value changes.
    val password by passwordTextFieldValue.collectAsState()
    var signInWithPassword by remember { mutableStateOf(true) }

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
                text = stringResource(R.string.sign_in_greeting),
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Medium)
            )

        }

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            LabeledInputField(
                textLabel = stringResource(R.string.sign_in_user),
                textFieldPlaceholder = stringResource(R.string.sign_in_placeholder_username),
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
                    SignInWithFingerprint {
                        signInWithPassword = true
                    }
                }
            )
        }
    }

}

@Composable
fun SignInWithFingerprint(labelAction: () -> Unit) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.sign_in_with_fingerprint),
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
                modifier = Modifier.size(40.dp),
                tint = BankingColors.Blue
            )

        }

        ClickableLabel(stringResource(R.string.sign_in_with_password)) {
            labelAction()
        }

    }

}

@Composable
fun SignInWithPassword(password: String, setPasswordValue: (String) -> Unit, labelAction: () -> Unit) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        LabeledInputField(
            textLabel = stringResource(R.string.sign_in_password),
            textFieldPlaceholder = stringResource(R.string.sign_in_placeholder_password),
            textFieldValue = password,
            setTextFieldValue = setPasswordValue
        )

        ClickableLabel(stringResource(R.string.sign_in_or_with_fingerprint)) {
            labelAction()
        }

    }

}

@Composable
fun ClickableLabel(text: String, action: () -> Unit) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { action() },
        text = stringResource(R.string.sign_in_or_with_fingerprint),
        textAlign = TextAlign.Center,
        color = BankingColors.Blue,
        fontWeight = FontWeight(600)
    )

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

@Preview
@Composable
fun PreviewBody() {
    Body(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        setUsernameValue = {},
        usernameTextFieldValue = MutableStateFlow(""),
        setPasswordValue = {},
        passwordTextFieldValue = MutableStateFlow("")
    )
}