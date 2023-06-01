package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FacilitySection(
    bedroomCount: MutableState<Int>,
    bathroomCount: MutableState<Int>,
    parkingCount: MutableState<Int>,
) {
    Text(text = "Facility", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))
    QuantityPicker("Bedroom", bedroomCount.value) { newValue -> bedroomCount.value = newValue }
    Spacer(modifier = Modifier.height(8.dp))
    QuantityPicker("Bathroom", bathroomCount.value) { newValue -> bathroomCount.value = newValue }
    Spacer(modifier = Modifier.height(8.dp))
    QuantityPicker("Parking", parkingCount.value) { newValue -> parkingCount.value = newValue }
    Spacer(modifier = Modifier.height(8.dp))
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