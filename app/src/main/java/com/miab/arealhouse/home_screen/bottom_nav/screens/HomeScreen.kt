package com.miab.arealhouse.home_screen.bottom_nav.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.search_bar.SearchBar
import com.miab.arealhouse.home_screen.tab_layout.TabLayout
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel

var names = listOf("Rent", "Sale")
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
    ),
    Apartment(
        imageUrl = R.drawable.image_second,
        name = "Awesome Apartment 2",
        description = "This is another awesome apartment.",
        price = "$2500/month",
        bedroom = 2,
        bathroom = 2,
        parking = 1,
        owner = "Tommy Wong",
        ownerProperty = "Owner",
        isFavorite = false,
        homeType = 2, // set your homeType here
        facilities = mapOf("WiFi" to true, "AC" to true) // set your facilities here
    ),
    // Add more apartments here...
    Apartment(
        imageUrl = R.drawable.image,
        name = "Awesome Apartment 1",
        description = "This is an awesome apartment.",
        price = "$2000/month",
        bedroom = 1,
        bathroom = 1,
        parking = 1,
        owner = "Ali",
        ownerProperty = "Trinity Property",
        isFavorite = true,
        homeType = 3,
        facilities = mapOf("Fully Furnished" to true, "WiFi" to true)
    ),
    Apartment(
        imageUrl = R.drawable.image_second,
        name = "Awesome Apartment 2",
        description = "This is another awesome apartment.",
        price = "$2500/month",
        bedroom = 2,
        bathroom = 3,
        parking = 1,
        owner = "David Carls",
        ownerProperty = "Agen Independen",
        isFavorite = false,
        homeType = 3,
        facilities = mapOf("WiFi" to true, "AC" to false)
    )
)

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun HomeScreen(apartmentViewModel: ApartmentViewModel = viewModel()){
    val apartments = apartmentViewModel.apartments.observeAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBar(modifier = Modifier.fillMaxWidth())
        TabLayout(tabNames = names, modifier = Modifier.weight(1f), apartments.value)
        Spacer(modifier = Modifier.weight(0.001f))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}