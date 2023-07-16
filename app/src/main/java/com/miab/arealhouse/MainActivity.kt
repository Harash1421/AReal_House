package com.miab.arealhouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miab.arealhouse.home_screen.bottom_nav.BottomBar
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.FilterActivity.Companion.newFilterOptions
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.ui.theme.ARealHouseTheme

class MainActivity : ComponentActivity() {
    private val apartmentViewModel: ApartmentViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARealHouseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Column {
                        BottomBar(this@MainActivity, apartmentViewModel)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        newFilterOptions.let {
            apartmentViewModel.applyFilters(it)
        }
    }
}

val list = listOf(
    Apartment(
        homeType = "Apartment",
        imageUrl = R.drawable.image,
        name = "Luxury Loft",
        country = "United States",
        city = "Los Angles",
        price = 120000.50,
        bedroom = 2,
        bathroom = 2,
        parking = 2,
        owner = "Jennifer",
        ownerProperty = "Owner",
        isFavorite = false,
        isSale = true,
        description = "Classic 2-storey Modern House with Scandinavian theme in Depok, on Jalan Pekapuran, West Java\n" +
                "\n" +
                "Specifications as follows:\n" +
                "2 storey building\n" +
                "Dirty Kitchen\n" +
                "Clean Kitchen\n" +
                "FULL AC 11 Units\n" +
                "Electricity 23000 Watt\n" +
                "Front / back garden\n" +
                "Swimming Pool",
        landSize = 90,
        homeSize = 40,
        facilities = mapOf("Fully Furnished" to true, "WiFi" to true, "AC" to true, "24 Hour Access" to false),
        latitude = 34.19111323372549,
        longitude = -118.84451455373413
    ),

    Apartment(
        homeType = "House",
        imageUrl = R.drawable.image_second,
        name = "Cosy Studio",
        country = "United States",
        city = "New York",
        price = 1200.0,
        bedroom = 1,
        bathroom = 1,
        parking = 0,
        owner = "Brian",
        ownerProperty = "Independent Agent",
        isFavorite = true,
        isSale = false,
        description = "A cosy studio apartment in the city center.",
        landSize = 100,
        homeSize = 50,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to true, "24 Hour Access" to true),
        latitude = 40.74041824661507,
        longitude = -73.990781100667
    ),
    Apartment(
        homeType = "Villa",
        imageUrl = R.drawable.image,
        name = "Suburban Villa",
        country = "United States",
        city = "Los Angles",
        price = 2200.0,
        bedroom = 4,
        bathroom = 3,
        parking = 2,
        owner = "Emma",
        ownerProperty = "Owner",
        isFavorite = true,
        isSale = false,
        description = "A spacious Villa in the suburbs.",
        landSize = 300,
        homeSize = 250,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to false, "24 Hour Access" to false),
        latitude = 34.18932049210181,
        longitude = -118.84181089004043
    ),
    Apartment(
        homeType = "Apartment",
        imageUrl = R.drawable.image_second,
        name = "Downtown Flat",
        country = "United States",
        city = "New York",
        price = 1800.0,
        bedroom = 2,
        bathroom = 2,
        parking = 1,
        owner = "Oliver",
        ownerProperty = "Independent Agent",
        isFavorite = false,
        isSale = false,
        description = "A modern flat in the downtown area.",
        landSize = 120,
        homeSize = 65,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to false, "24 Hour Access" to true),
        latitude = 40.74414122348834,
        longitude = -73.99208998139481
    )
)


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARealHouseTheme {
        val context = LocalContext.current
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                val apartmentViewModel: ApartmentViewModel = viewModel()
                BottomBar(context, apartmentViewModel)
            }
        }
    }
}
