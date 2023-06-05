package com.miab.arealhouse

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.miab.arealhouse.home_screen.bottom_nav.BottomBar
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.FilterActivity.Companion.newFilterOptions
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.FilterOptions
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
                    color = MaterialTheme.colorScheme.background
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
        description = "This is a luxurious loft apartment.",
        price = "$3000/month",
        bedroom = 2,
        bathroom = 2,
        parking = 2,
        owner = "Jennifer",
        ownerProperty = "Owner",
        isFavorite = false,
        facilities = mapOf("Fully Furnished" to true, "WiFi" to true, "AC" to true, "24 Hour Access" to false)
    ),
    Apartment(
        homeType = "House",
        imageUrl = R.drawable.image_second,
        name = "Cosy Studio",
        description = "A cosy studio apartment in the city center.",
        price = "$1200/month",
        bedroom = 1,
        bathroom = 1,
        parking = 0,
        owner = "Brian",
        ownerProperty = "Independent Agent",
        isFavorite = true,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to true, "24 Hour Access" to true)
    ),
    Apartment(
        homeType = "Villa",
        imageUrl = R.drawable.image,
        name = "Suburban House",
        description = "A spacious house in the suburbs.",
        price = "$2200/month",
        bedroom = 4,
        bathroom = 3,
        parking = 2,
        owner = "Emma",
        ownerProperty = "Owner",
        isFavorite = true,
        facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to false, "24 Hour Access" to false)
    ),
    Apartment(
        homeType = "Apartment",
        imageUrl = R.drawable.image_second,
        name = "Downtown Flat",
        description = "A modern flat in the downtown area.",
        price = "$1800/month",
        bedroom = 2,
        bathroom = 2,
        parking = 1,
        owner = "Oliver",
        ownerProperty = "Independent Agent",
        isFavorite = false,
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
