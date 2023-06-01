package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R
import com.miab.arealhouse.ui.theme.ARealHouseTheme
import java.text.NumberFormat

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
                                IconButton(onClick = { onBackPressed() }) {
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

                        val accommodations = stringArrayResource(id = R.array.accommodation_types)
                        val conditions = stringArrayResource(id = R.array.property_conditions)
                        Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            AccommodationTypePicker(
                                accommodations,
                                selectedAccommodation
                            ) { selectedAccommodation = it }

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

                            PropertyConditionPicker(
                                conditions,
                                selectedCondition
                            ) { selectedCondition = it }

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            FacilitySection(
                                bedroomCount,
                                bathroomCount,
                                parkingCount,
                                facilities
                            ) { facility ->
                                facilities.value = facilities.value.toMutableMap().apply {
                                    this[facility] = !(this[facility] ?: false)
                                }
                            }

                            Spacer(modifier = Modifier.height(48.dp))
                        }

                            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                                FilterActions(
                                    onReset = {
                                        // Reset your filters here
                                        selectedAccommodation = 0
                                        selectedCondition = 0
                                        price = 0f
                                        minPrice = ""
                                        maxPrice = ""
                                        bedroomCount.value = 0
                                        bathroomCount.value = 0
                                        parkingCount.value = 0
                                        facilities.value = facilities.value.mapValues { false }
                                    },
                                    onApply = {
                                        // Apply your filters here
                                        // Your code here
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
fun AccommodationTypePicker(items: Array<String>, selectedItem: Int, onItemSelected: (Int) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Text(text = "Accommodation Type", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))
    Box {
        OutlinedTextField(
            value = items[selectedItem],
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "",
                    Modifier.clickable { expanded = !expanded }
                )
            }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceSection(
    price: Float,
    minPrice: String,
    maxPrice: String,
    onPriceChange: (Float) -> Unit,
    onMinPriceChange: (String) -> Unit,
    onMaxPriceChange: (String) -> Unit
) {
    var seekBarMin by remember { mutableFloatStateOf(minPrice.toFloatOrNull() ?: 0f) }
    var seekBarMax by remember { mutableFloatStateOf(maxPrice.toFloatOrNull() ?: 5000f) }

    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }

    Text(text = "Price", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))
    RangeSlider(
        value = seekBarMin..seekBarMax,
        onValueChange = { range ->
            seekBarMin = range.start
            seekBarMax = range.endInclusive
            onMinPriceChange(formatter.format(range.start))
            onMaxPriceChange(formatter.format(range.endInclusive))
        },
        colors = SliderDefaults.colors(
            thumbColor = Color.Blue,
            activeTrackColor = Color.Blue,
            inactiveTrackColor = Color.LightGray
        ),
        steps = 100,
        valueRange = 0f..5000f
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        OutlinedTextField(
            placeholder = { Text(text = if (minPrice.isBlank()) {"Min Price"} else "",
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall.copy(color = Color.Gray)) },
            value = minPrice,
            onValueChange = { value ->
                onMinPriceChange(value)
                val floatValue = value.toFloatOrNull() ?: 0f
                seekBarMin = floatValue.coerceIn(0f, seekBarMax)
                onPriceChange(seekBarMin)
            },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            placeholder = { Text(text = if (minPrice.isBlank()) {"Min Price"} else "",
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall.copy(color = Color.Gray)) },
            value = maxPrice,
            onValueChange = { value ->
                onMaxPriceChange(value)
                val floatValue = value.toFloatOrNull() ?: 1000000f
                seekBarMax = floatValue.coerceIn(seekBarMin, 1000000f)
                onPriceChange(seekBarMax)
            },
            modifier = Modifier.weight(1f)
        )
    }
}



@Composable
fun PropertyConditionPicker(items: Array<String>, selectedItem: Int, onItemSelected: (Int) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Text(text = "Property Type", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))
    Box {
        OutlinedTextField(
            value = items[selectedItem],
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "",
                    Modifier.clickable { expanded = !expanded }
                )
            }
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
fun FacilitySection(
    bedroomCount: MutableState<Int>,
    bathroomCount: MutableState<Int>,
    parkingCount: MutableState<Int>,
    facilities: MutableState<Map<String, Boolean>>,
    onFacilityToggle: (String) -> Unit
) {
    Text(text = "Facility", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))
    QuantityPicker("Bedroom", bedroomCount.value) { newValue -> bedroomCount.value = newValue }
    Spacer(modifier = Modifier.height(8.dp))
    QuantityPicker("Bathroom", bathroomCount.value) { newValue -> bathroomCount.value = newValue }
    Spacer(modifier = Modifier.height(8.dp))
    QuantityPicker("Parking", parkingCount.value) { newValue -> parkingCount.value = newValue }
    Spacer(modifier = Modifier.height(8.dp))

    Divider(modifier = Modifier.padding(vertical = 14.dp))

    Text(text = "Boarding Facilities", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))

    facilities.value.keys.forEach { facility ->
        CheckboxOption(
            text = facility,
            checked = facilities.value[facility] ?: false,
            onCheckedChange = { onFacilityToggle(facility) }
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun QuantityPicker(label: String, quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = MaterialTheme.typography.body1, modifier = Modifier.weight(1f))
        IconButton(onClick = { if (quantity > 0) onQuantityChange(quantity - 1) }) {
            Icon(Icons.Filled.AddCircle, contentDescription = "Decrease")
        }
        Text(text = quantity.toString(), style = MaterialTheme.typography.body1)
        IconButton(onClick = { onQuantityChange(quantity + 1) }) {
            Icon(Icons.Filled.AddCircle, contentDescription = "Increase")
        }
    }
}


@Composable
fun CheckboxOption(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun FilterActions(onReset: () -> Unit, onApply: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(12.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = { onReset() },
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .padding(10.dp)
        ) {
            Text(text = "Reset",
                modifier = Modifier.wrapContentSize(Alignment.CenterStart),
                style = TextStyle(color = Color.Blue)
            )
        }

        Button(
            onClick = { onApply() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
                .padding(7.dp)
        ) {
            Text(text = "Apply",
                modifier = Modifier.wrapContentSize(Alignment.Center),
                style = TextStyle(color = Color.White)
            )
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
