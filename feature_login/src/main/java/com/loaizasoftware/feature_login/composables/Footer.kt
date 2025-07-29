package com.loaizasoftware.feature_login.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loaizasoftware.feature_login.R

// Updated FooterContent with click handling and expanded content
@Composable
fun FooterContent(
    onItemClick: (String) -> Unit = {},
    isExpanded: Boolean = false
) {
    val footerActions: List<Triple<Int, Int, String>> = listOf(
        Triple(R.drawable.currency_exchange, R.string.currency_exchange, "exchange"),
        Triple(R.drawable.lock, R.string.code, "code"),
        Triple(R.drawable.search, R.string.help, "help")
    )

    val footerExpandedActions: List<Triple<Int, Int, String>> = listOf(
        Triple(R.drawable.location, R.string.locate_us, "locate_us"),
        Triple(R.drawable.discount, R.string.offers, "offers"),
        Triple(R.drawable.compass, R.string.compass, "compass")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Always visible footer actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            footerActions.forEach {

                val modifier = Modifier
                    .weight(1f)
                    .clickable { onItemClick(it.third) }

                FooterActionButton(modifier = modifier, itemData = it)

            }
        }

        // Expanded content - only shown when expanded
        if (isExpanded) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                footerExpandedActions.forEach {

                    val modifier = Modifier
                        .weight(1f)
                        .clickable { onItemClick(it.third) }

                    FooterActionButton(modifier = modifier, itemData = it)

                }
            }

        }
    }
}

@Composable
fun FooterActionButton(
    modifier: Modifier,
    itemData: Triple<Int, Int, String>
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(itemData.first),
            contentDescription = "",
            modifier = Modifier.size(30.dp),
            tint = Color.LightGray
        )

        Text(
            text = stringResource(itemData.second),
            style = TextStyle(
                color = Color.Gray,
                fontSize = 12.sp
            )
        )
    }

}