package com.loaizasoftware.feature_login.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loaizasoftware.feature_login.R

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