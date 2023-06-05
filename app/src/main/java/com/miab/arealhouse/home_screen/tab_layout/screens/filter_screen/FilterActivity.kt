package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.miab.arealhouse.MainActivity
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FacilityBoardingSection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FacilitySection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FilterActions
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.HomeType
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.PriceSection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.PropertyConditionPicker
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.FilterOptions
import com.miab.arealhouse.list
import com.miab.arealhouse.ui.theme.ARealHouseTheme
import kotlin.math.min

class FilterActivity : ComponentActivity() {
    private val apartmentViewModel: ApartmentViewModel by viewModels()

    companion object{
        var newFilterOptions = FilterOptions()
    }

        @SuppressLint("AutoboxingStateValueProperty")
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARealHouseTheme {
                val context = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val selectedHomeType = remember { mutableIntStateOf(0) }
                    val selectedPropertyType = remember { mutableIntStateOf(0) }
                    val minPrice = remember { mutableStateOf("") }
                    val maxPrice = remember { mutableStateOf("") }

                    val bedroomCount = remember { mutableIntStateOf(0) }
                    val bathroomCount = remember { mutableIntStateOf(0) }
                    val parkingCount = remember { mutableIntStateOf(0) }
                    val homeType = stringArrayResource(id = R.array.accommodation_types)
                    val propertyType = stringArrayResource(id = R.array.property_conditions)

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
                        TopBarConfiguration(){
                            onBackPressed()
                        }

                        Box(modifier = Modifier.fillMaxSize()) {
                            WidgetsConfiguration(
                                selectedHomeType = selectedHomeType,
                                minPrice = minPrice,
                                maxPrice = maxPrice,
                                selectedPropertyType = selectedPropertyType,
                                bedroomCount = bedroomCount,
                                bathroomCount = bathroomCount,
                                parkingCount = parkingCount,
                                facilities = facilities

                            )
                            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                                FilterActions(
                                    onReset = {
                                        // Reset the filter state
                                        selectedHomeType.value = 0
                                        selectedPropertyType.value = 0
                                        minPrice.value = ""
                                        maxPrice.value = ""
                                        bedroomCount.value = 0
                                        bathroomCount.value = 0
                                        parkingCount.value = 0
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
                                            minPrice = 0,
                                            maxPrice = Int.MAX_VALUE,
                                            bedroomCount = bedroomCount.value,
                                            bathroomCount = bathroomCount.value,
                                            parkingCount = parkingCount.value,
                                            propertyType = selectedPropertyTypeStr,
                                            facilities = facilities.value
                                        )


                                        apartmentViewModel.updateFilterSettings(newFilterOptions)

                                        onBackPressed()
                                    }

                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

// Method for top bar configuration
@Composable
fun TopBarConfiguration(onClick: () -> Unit){
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

// Function for Widgets on Screen
@Composable
fun WidgetsConfiguration(
    selectedHomeType: MutableState<Int>,
    minPrice: MutableState<String>,
    maxPrice: MutableState<String>,
    selectedPropertyType: MutableState<Int>,
    bedroomCount: MutableState<Int>,
    bathroomCount: MutableState<Int>,
    parkingCount: MutableState<Int>,
    facilities: MutableState<Map<String, Boolean>>
    ){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        HomeType(
            selectedItem = selectedHomeType.value,
        ){ text, index ->
            selectedHomeType.value = index
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        PriceSection(
            minPrice = minPrice.value,
            maxPrice = maxPrice.value,
            onMinPriceChange = { minPrice.value = it },
            onMaxPriceChange = { maxPrice.value = it }
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        PropertyConditionPicker(
            selectedPropertyType.value
        ) { text, index ->
            selectedPropertyType.value = index
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        FacilitySection(
            bedroomCount,
            bathroomCount,
            parkingCount
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        FacilityBoardingSection(facilities = facilities)


        Spacer(modifier = Modifier.height(48.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun FilterActivityPreview() {
    ARealHouseTheme {
        FilterActivity()
    }
}
