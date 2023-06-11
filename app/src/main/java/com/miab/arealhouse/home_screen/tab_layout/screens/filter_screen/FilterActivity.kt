package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FacilityBoardingSection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FacilitySection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FilterActions
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.HomeType
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.PriceSection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.PropertyConditionPicker
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.FilterOptions
import com.miab.arealhouse.ui.theme.ARealHouseTheme
import java.util.Locale

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

@Composable
fun WidgetsConfiguration(
    context: Context,
    selectedHomeType: MutableState<Int>,
    minPrice: MutableState<String>,
    maxPrice: MutableState<String>,
    selectedPropertyType: MutableState<Int>,
    bedroomCount: MutableState<Int>,
    bathroomCount: MutableState<Int>,
    parkingCount: MutableState<Int>,
    facilities: MutableState<Map<String, Boolean>>
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HomeType(
            selectedItem = selectedHomeType.value
        ) { text, index ->
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

        PropertyConditionPicker(selectedPropertyType.value) { text, index ->
            selectedPropertyType.value = index
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        FacilitySection(
            bedroomCount = bedroomCount,
            bathroomCount = bathroomCount,
            parkingCount = parkingCount
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        //Country lists variables
        val countryList = stringArrayResource(id = R.array.countries)
        val cityList = remember { mutableStateListOf<String>() }

        val selectedCountry = remember { mutableStateOf(0) }
        val selectedCity = remember { mutableStateOf(0) }

        CountryList(
            context,
            countryList = countryList,
            selectedCountry = selectedCountry,
            cityList = cityList,
            selectedCity = selectedCity
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        CityList(
            cityList = cityList,
            selectedCity = selectedCity
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        FacilityBoardingSection(facilities = facilities)

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun CountryList(context: Context,
                countryList: Array<String>,
                selectedCountry: MutableState<Int>,
                cityList: MutableList<String>,
                selectedCity: MutableState<Int>) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Box {
        OutlinedTextField(
            value = countryList[selectedCountry.value],
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            countryList.forEachIndexed { index, type ->
                DropdownMenuItem(onClick = {
                    selectedCountry.value = index
                    expanded = false
                    selectedCity.value = 0
                    updateCityList(context, countryList[selectedCountry.value], cityList)
                }) {
                    Text(text = type)
                }
            }
        }
    }
}

@Composable
fun CityList(cityList: List<String>, selectedCity: MutableState<Int>) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Box {
        OutlinedTextField(
            value = cityList.getOrElse(selectedCity.value) {"Select Country First"},
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            cityList.forEachIndexed { index, city ->
                DropdownMenuItem(onClick = {
                    selectedCity.value = index
                    expanded = false
                }) {
                    Text(text = city)
                }
            }
        }
    }
}

// Function to update the city list based on the selected country
@SuppressLint("DiscouragedApi")
private fun updateCityList(
    context: Context,
    country: String,
    cityList: MutableList<String>
) {
    // Clear the city list
    cityList.clear()

    // Fetch the array resource ID dynamically based on the selected country
    val cityArrayResourceId = context.resources.getIdentifier(
        "cities_${country.lowercase()}",
        "array",
        context.packageName
    )
    if (cityArrayResourceId != 0) {
        // Retrieve the city array using the resource ID
        val cities = context.resources.getStringArray(cityArrayResourceId)

        // Add the cities to the city list
        cityList.addAll(cities)
    }
}

@Preview(showBackground = true)
@Composable
fun FilterActivityPreview() {
    ARealHouseTheme {
        FilterActivity()
    }
}
