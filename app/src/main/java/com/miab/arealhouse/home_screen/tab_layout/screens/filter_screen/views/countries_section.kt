package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CountriesSection(context: Context,
                countryList: Array<String>,
                selectedCountry: MutableState<Int>,
                cityList: MutableList<String>,
                selectedCity: MutableState<Int>
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Text(text = "Country", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(12.dp))
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

// Function to update the city list based on the selected country
@SuppressLint("DiscouragedApi")
fun updateCityList(
    context: Context,
    country: String,
    cityList: MutableList<String>
) {
    // Clear the city list
    cityList.clear()

    // Fetch the array resource ID dynamically based on the selected country
    val countryLowerCase = country.lowercase().replace(" ", "_")
    val cityArrayResourceId = context.resources.getIdentifier(
        "cities_$countryLowerCase",
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