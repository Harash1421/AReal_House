package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FilterActions
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.WidgetsConfiguration
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.FilterOptions
import com.miab.arealhouse.ui.theme.ARealHouseTheme

@Suppress("DEPRECATION")
class FilterActivity : ComponentActivity() {
    private val apartmentViewModel: ApartmentViewModel by viewModels()

    companion object {
        var newFilterOptions = FilterOptions()
    }

    @SuppressLint("AutoboxingStateValueProperty")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARealHouseTheme {
                val context = LocalContext.current
                Surface(color = MaterialTheme.colors.background) {
                    val selectedHomeType = remember { mutableIntStateOf(0) }
                    val selectedPropertyType = remember { mutableIntStateOf(0) }
                    val minPrice = remember { mutableDoubleStateOf(0.0) }
                    val maxPrice = remember { mutableDoubleStateOf(5000.0) }

                    val bedroomCount = remember { mutableIntStateOf(0) }
                    val bathroomCount = remember { mutableIntStateOf(0) }
                    val parkingCount = remember { mutableIntStateOf(0) }
                    val homeType = stringArrayResource(id = R.array.accommodation_types)
                    val propertyType = stringArrayResource(id = R.array.property_conditions)

                    //Country lists variables
                    val countryList = stringArrayResource(id = R.array.countries)
                    val cityList = remember { mutableStateListOf<String>() }

                    val selectedCountry = remember { mutableIntStateOf(0) }
                    val selectedCity = remember { mutableIntStateOf(0) }

                    val facilities = remember {
                        mutableStateOf(
                            mapOf(
                                "WiFi" to false,
                                "AC" to false,
                                "Fully Furnished" to false,
                                "24 Hour Access" to false
                            )
                        )
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        TopBarConfiguration {
                            onBackPressed()
                        }

                        Box(modifier = Modifier.fillMaxSize()) {
                            WidgetsConfiguration(
                                context = context,
                                selectedHomeType = selectedHomeType,
                                minPrice = minPrice,
                                maxPrice = maxPrice,
                                selectedPropertyType = selectedPropertyType,
                                bedroomCount = bedroomCount,
                                bathroomCount = bathroomCount,
                                parkingCount = parkingCount,
                                countryList = countryList,
                                cityList = cityList,
                                selectedCountry = selectedCountry,
                                selectedCity = selectedCity,
                                facilities = facilities
                            )
                            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                                FilterActions(
                                    onReset = {
                                        // Reset the filter state
                                        selectedHomeType.value = 0
                                        selectedPropertyType.value = 0
                                        minPrice.value = 0.0
                                        maxPrice.value = 5000.0
                                        bedroomCount.value = 0
                                        bathroomCount.value = 0
                                        parkingCount.value = 0
                                        selectedCountry.value = 0
                                        selectedCity.value = 0
                                        facilities.value = facilities.value.mapValues { false }
                                        apartmentViewModel.clearFilters()
                                    },

                                    onApply = {
                                        val selectedPropertyTypeStr =
                                            if (selectedPropertyType.value > 0)
                                                propertyType[selectedPropertyType.value]
                                            else ""
                                        val selectedHomeTypeStr =
                                            if (selectedHomeType.value > 0)
                                                homeType[selectedHomeType.value]
                                            else ""

                                        newFilterOptions = FilterOptions(
                                            homeType = selectedHomeTypeStr,
                                            minPrice = minPrice.value,
                                            maxPrice = maxPrice.value,
                                            bedroomCount = bedroomCount.value,
                                            bathroomCount = bathroomCount.value,
                                            parkingCount = parkingCount.value,
                                            propertyType = selectedPropertyTypeStr,
                                            facilities = facilities.value
                                        )

                                        apartmentViewModel.updateFilterSettings(newFilterOptions)

                                        onBackPressed()
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarConfiguration(onClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Filter") },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    )
}

@Preview(showBackground = true)
@Composable
fun FilterActivityPreview() {
    ARealHouseTheme {
        FilterActivity()
    }
}
