package com.miab.arealhouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.miab.arealhouse.home_screen.bottom_nav.BottomBar
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.FilterActivity.Companion.newFilterOptions
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.ui.theme.ARealHouseTheme

class MainActivity : ComponentActivity() {
    private val apartmentViewModel: ApartmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARealHouseTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Column {
                        BottomBar()
                    }
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        apartmentViewModel.applyFilters(newFilterOptions)
    }
}

val list = listOf(
    Apartment(
        homeType = "Apartment",
        imageUrl = R.drawable.image,
        name = "Luxury Loft",
        location = "Los Angles, United States",
        price = "$3000/month",
        bedroom = 2,
        bathroom = 2,
        parking = 2,
        owner = "Jennifer",
        ownerProperty = "Owner",
        isFavorite = false,
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
        facilities = mapOf("Fully Furnished" to true, "WiFi" to true, "AC" to true, "24 Hour Access" to false)
    ),
    Apartment(
        homeType = "House",
        imageUrl = R.drawable.image_second,
        name = "Cosy Studio",
        location = "New York, United Stated",
        price = "$1200/month",
        bedroom = 1,
        bathroom = 1,
        parking = 0,
        owner = "Brian",
        ownerProperty = "Independent Agent",
        isFavorite = true,
        description = "A cosy studio apartment in the city center.",
        landSize = 100,
        homeSize = 50,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to true, "24 Hour Access" to true)
    ),
    Apartment(
        homeType = "Villa",
        imageUrl = R.drawable.image,
        name = "Suburban Villa",
        location = "Los Angles, United States",
        price = "$2200/month",
        bedroom = 4,
        bathroom = 3,
        parking = 2,
        owner = "Emma",
        ownerProperty = "Owner",
        isFavorite = true,
        description = "A spacious Villa in the suburbs.",
        landSize = 300,
        homeSize = 250,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to false, "24 Hour Access" to false)
    ),
    Apartment(
        homeType = "Apartment",
        imageUrl = R.drawable.image_second,
        name = "Downtown Flat",
        location = "New York, United States",
        price = "$1800/month",
        bedroom = 2,
        bathroom = 2,
        parking = 1,
        owner = "Oliver",
        ownerProperty = "Independent Agent",
        isFavorite = false,
        description = "A modern flat in the downtown area.",
        landSize = 120,
        homeSize = 65,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to false, "24 Hour Access" to true)
    )
)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARealHouseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                BottomBar()
            }
        }
    }
}
