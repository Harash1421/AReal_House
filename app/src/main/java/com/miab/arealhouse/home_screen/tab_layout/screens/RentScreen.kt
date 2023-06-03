package com.miab.arealhouse.home_screen.tab_layout.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentsCard

@Composable
fun RentScreen(apartments: List<Apartment>){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(apartments) { index, apartment ->
            ApartmentsCard(apartment, index)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RentScreenPreview(){
    val apartments = listOf(
        Apartment(
            imageUrl = R.drawable.image,
            name = "Awesome Apartment 1",
            description = "This is an awesome apartment.",
            price = "$2000/month",
            bedroom = 3,
            bathroom = 2,
            parking = 1,
            owner = "Ahmed",
            ownerProperty = "Simphony Property",
            isFavorite = true,
            homeType = 1,
            facilities = mapOf("Fully Furnished" to true, "WiFi" to false)
        )
    )

    RentScreen(apartments)
}