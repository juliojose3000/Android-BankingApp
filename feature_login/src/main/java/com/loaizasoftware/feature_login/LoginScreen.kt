package com.loaizasoftware.feature_login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.loaizasoftware.core.ext.showToast
import com.loaizasoftware.core.utils.UiState
import com.loaizasoftware.core_ui.composables.Loader
import com.loaizasoftware.core_ui.resources.BankingColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->

        when(uiState) {

            is UiState.Error -> {
                context showToast uiState.error
            }

            is UiState.Loading -> {
                Loader()
            }

            is UiState.Success -> {
                BuildUI(
                    paddingValues = padding,
                    onClick = {

                    },
                    usernameTextFieldOnValueChange = viewModel::onUsernameTextFieldValueChange,
                    usernameTextFieldValue = viewModel.usernameTextFieldMutableState
                )
            }

        }

    }

}

@Composable
fun BuildUI(
    paddingValues: PaddingValues,
    onClick: () -> Unit,
    usernameTextFieldOnValueChange: (String) -> Unit,
    usernameTextFieldValue: StateFlow<String>
) {

    ConstraintLayout (
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
                bottom = 0.dp
            )
            .fillMaxSize()
            .background(BankingColors.WhiteBackground)
    ) {

        val (headerRef, bodyRef, bodyBottomRef, footerModifierRef) = createRefs()

        val headerModifier = Modifier
            .background(BankingColors.Red)
            .fillMaxWidth()
            .height(200.dp)
            .constrainAs(headerRef) {
                top.linkTo(parent.top)
            }

        val bodyModifier = Modifier
            .background(Color.White)
            .height(350.dp)
            //.fillMaxWidth() //In ConstraintLayout, you don't need fillMaxWidth(). The constraints define the width.
            .constrainAs(bodyRef) {
                top.linkTo(headerRef.bottom, margin = (-65).dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            }
            .padding(12.dp)

        val bodyBottomModifier = Modifier
            .background(Color.White)
            .height(70.dp)
            .constrainAs(bodyBottomRef) {
                top.linkTo(bodyRef.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            }
            .padding(20.dp)

        val footerModifier = Modifier
            .background(Color.Gray)
            .constrainAs(footerModifierRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }


        Header(modifier = headerModifier)

        Body(
            modifier = bodyModifier,
            onValueChange = usernameTextFieldOnValueChange,
            usernameTextFieldValue = usernameTextFieldValue
        )

        BodyBottom(modifier = bodyBottomModifier)

        Footer(modifier = footerModifier)

    }

}

@Composable
fun Header(modifier: Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Row {

            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(35.dp),
                painter = painterResource(id = R.drawable.bank),
                contentDescription = "",
                tint = Color.White,
            )

            Text(
                text = "BANKING",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

        }


    }

}


@Composable
fun Body(modifier: Modifier, onValueChange: (String) -> Unit, usernameTextFieldValue: StateFlow<String>) {

    val username by usernameTextFieldValue.collectAsState() //In Jetpack Compose, you should collect flows using collectAsState(), so the composition can automatically update when the value changes.

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


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = onValueChange,
            placeholder = { Text(stringResource(R.string.sign_in_placeholder_user)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = ""
                )
            }
        )

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

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.sign_in_with_password),
            textAlign = TextAlign.Center,
            color = BankingColors.Blue,
            fontWeight = FontWeight(600)
        )

    }

}

@Composable
fun BodyBottom(modifier: Modifier) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.people),
            contentDescription = "",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = stringResource(R.string.sign_in_client_or_create_user))

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "",
            tint = Color.Gray,
            modifier = Modifier.size(30.dp)
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Footer(modifier: Modifier) {
    val sheetState = rememberBottomSheetScaffoldState()

    // BottomSheetScaffold should be the root composable
    BottomSheetScaffold(
        modifier = modifier, // Apply the modifier to the scaffold
        scaffoldState = sheetState, // Pass the state
        sheetContent = {
            FooterContent()
        },
        sheetPeekHeight = 130.dp, // Height of the visible bottom sheet
        sheetContainerColor = Color.White,
    ) { _ ->
        // Your main screen content



    }
}

@Composable
fun FooterContent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.currency_exchange),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.LightGray
            )

            Text("Tipo de Cambio")

        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.lock),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.LightGray
            )

            Text("Código BAC")

        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.LightGray
            )

            Text("Ayuda")

        }

    }
}



@Composable
@Preview
fun PreviewBuildUI() {

    BuildUI(
        paddingValues = PaddingValues(0.dp),
        onClick = {},
        usernameTextFieldOnValueChange = {},
        usernameTextFieldValue = MutableStateFlow("")
    )

}