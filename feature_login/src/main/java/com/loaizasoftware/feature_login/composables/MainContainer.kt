package com.loaizasoftware.feature_login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.loaizasoftware.core_ui.resources.BankingColors
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainContainer(
    paddingValues: PaddingValues,
    onClick: () -> Unit,
    setUsernameValue: (String) -> Unit,
    usernameTextFieldValue: StateFlow<String>,
    setPasswordValue: (String) -> Unit,
    passwordTextFieldValue: StateFlow<String>,
) {

    ConstraintLayout(
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

        val (headerRef, bodyRef, bodyBottomRef) = createRefs()

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

        Header(modifier = headerModifier)

        Body(
            modifier = bodyModifier,
            setUsernameValue = setUsernameValue,
            usernameTextFieldValue = usernameTextFieldValue,
            setPasswordValue = setPasswordValue,
            passwordTextFieldValue = passwordTextFieldValue
        )

        AffiliationBox(modifier = bodyBottomModifier)

    }

}