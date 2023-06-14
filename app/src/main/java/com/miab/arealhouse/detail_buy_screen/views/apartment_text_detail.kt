package com.miab.arealhouse.detail_buy_screen.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment


@Composable
fun ApartmentTextDetail(apartment: Apartment?){
    Column(Modifier.padding(5.dp)) {
        Text(
            text = apartment?.name ?: "",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "${apartment?.city}, ${apartment?.country}" ?: "",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        )
        Spacer(Modifier.height(14.dp))
        val price = apartment!!.price
        Text(
            text = if (apartment.isSale) "$$price" else "$$price/month",
            style = TextStyle(
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )
    }

    Spacer(Modifier.height(10.dp))
    Divider()
}
@Preview(showBackground = true)
@Composable
fun ApartmentTextDetailPreview(){

}