package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import java.text.NumberFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceSection(
    minPrice: String,
    maxPrice: String,
    onMinPriceChange: (String) -> Unit,
    onMaxPriceChange: (String) -> Unit
) {

    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }

    var seekBarMin by remember { mutableFloatStateOf(minPrice.toFloatOrNull() ?: 0f) }
    var seekBarMax by remember { mutableFloatStateOf(maxPrice.toFloatOrNull() ?: 5000f) }

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
            },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            placeholder = { Text(text = if (minPrice.isBlank()) {"Max Price"} else "",
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall.copy(color = Color.Gray)) },
            value = maxPrice,
            onValueChange = { value ->
                onMaxPriceChange(value)
                val floatValue = value.toFloatOrNull() ?: 1000000f
                seekBarMax = floatValue.coerceIn(seekBarMin, 1000000f)
            },
            modifier = Modifier.weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PriceSectionPreview(){
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }
    PriceSection(
        minPrice = minPrice,
        maxPrice = maxPrice,
        onMinPriceChange = {minPrice = it},
        onMaxPriceChange = {maxPrice = it}
    )
}