package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WidgetsConfiguration(
    context: Context,
    selectedHomeType: MutableState<Int>,
    minPrice: MutableState<Double>,
    maxPrice: MutableState<Double>,
    selectedPropertyType: MutableState<Int>,
    bedroomCount: MutableState<Int>,
    bathroomCount: MutableState<Int>,
    parkingCount: MutableState<Int>,
    countryList: Array<String>,
    cityList: MutableList<String>,
    selectedCountry: MutableState<Int>,
    selectedCity: MutableState<Int>,
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

        minPrice.value = String.format("%.2f", minPrice.value).toDouble()
        maxPrice.value = String.format("%.2f", maxPrice.value).toDouble()

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

        CountriesSection(
            context,
            countryList = countryList,
            selectedCountry = selectedCountry,
            cityList = cityList,
            selectedCity = selectedCity
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        CitiesSection(
            cityList = cityList,
            selectedCity = selectedCity
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        FacilityBoardingSection(facilities = facilities)

        Spacer(modifier = Modifier.height(48.dp))
    }
}