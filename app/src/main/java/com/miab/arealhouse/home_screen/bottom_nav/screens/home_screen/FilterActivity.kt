package com.miab.arealhouse.home_screen.bottom_nav.screens.home_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R
import com.miab.arealhouse.ui.theme.ARealHouseTheme

class FilterActivity : ComponentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARealHouseTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = { Text(text = "Filter") },
                            navigationIcon = {
                                IconButton(onClick = { /* Handle back button press here */ }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                }
                            },
                            backgroundColor = MaterialTheme.colors.surface,
                            contentColor = MaterialTheme.colors.onSurface,
                        )

                        var selectedAccommodation by remember { mutableIntStateOf(0) }
                        var selectedCondition by remember { mutableIntStateOf(0) }
                        var price by remember { mutableFloatStateOf(0f) }
                        var minPrice by remember { mutableStateOf("") }
                        var maxPrice by remember { mutableStateOf("") }

                        val bedroomCount = remember { mutableIntStateOf(0) }
                        val bathroomCount = remember { mutableIntStateOf(0) }
                        val parkingCount = remember { mutableIntStateOf(0) }

                        val accommodations = stringArrayResource(id = R.array.accommodation_types)
                        val conditions = stringArrayResource(id = R.array.property_conditions)

                        Column(modifier = Modifier.padding(16.dp)) {
                            AccommodationTypePicker(accommodations, selectedAccommodation) { selectedAccommodation = it }

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            PriceSection(
                                price = price,
                                minPrice = minPrice,
                                maxPrice = maxPrice,
                                onPriceChange = { price = it },
                                onMinPriceChange = { minPrice = it },
                                onMaxPriceChange = { maxPrice = it }
                            )

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            PropertyConditionPicker(conditions, selectedCondition) { selectedCondition = it }

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            FacilitySection(bedroomCount, bathroomCount, parkingCount)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AccommodationTypePicker(items: Array<String>, selectedItem: Int, onItemSelected: (Int) -> Unit) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        Text(text = "Accommodation Type", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            OutlinedTextField(
                value = items[selectedItem],
                onValueChange = { /* Ignore changes */ },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
                trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {
                        onItemSelected(index)
                        expanded = false
                    }) {
                        Text(text = item)
                    }
                }
            }
        }
    }



    @Composable
    fun PriceSection(
        price: Float,
        minPrice: String,
        maxPrice: String,
        onPriceChange: (Float) -> Unit,
        onMinPriceChange: (String) -> Unit,
        onMaxPriceChange: (String) -> Unit
    ) {
        Text(text = "Price", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Slider(value = price, onValueChange = onPriceChange, valueRange = 0f..1000000f, steps = 100)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            OutlinedTextField(
                value = minPrice,
                onValueChange = onMinPriceChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = maxPrice,
                onValueChange = onMaxPriceChange,
                modifier = Modifier.weight(1f)
            )
        }
    }


    @Composable
    fun PropertyConditionPicker(items: Array<String>, selectedItem: Int, onItemSelected: (Int) -> Unit) {
        // Similar to AccommodationTypePicker
    }

    @Composable
    fun FacilitySection(
        bedroomCount: MutableState<Int>,
        bathroomCount: MutableState<Int>,
        parkingCount: MutableState<Int>
    ) {
        Text(text = "Facility", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        QuantityPicker("Bedroom", bedroomCount.value) { newValue -> bedroomCount.value = newValue }
        Spacer(modifier = Modifier.height(8.dp))
        QuantityPicker("Bathroom", bathroomCount.value) { newValue -> bathroomCount.value = newValue }
        Spacer(modifier = Modifier.height(8.dp))
        QuantityPicker("Parking", parkingCount.value) { newValue -> parkingCount.value = newValue }
    }



    @Composable
    fun QuantityPicker(label: String, quantity: Int, onQuantityChange: (Int) -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = label, style = MaterialTheme.typography.body1, modifier = Modifier.weight(1f))
            IconButton(onClick = { if (quantity > 0) onQuantityChange(quantity - 1) }) {
                Icon(Icons.Filled.Clear, contentDescription = "Decrease")
            }
            Text(text = quantity.toString(), style = MaterialTheme.typography.body1)
            IconButton(onClick = { onQuantityChange(quantity + 1) }) {
                Icon(Icons.Filled.Add, contentDescription = "Increase")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterActivityPreview() {
    ARealHouseTheme {
        FilterActivity().onCreate(Bundle())
    }
}
