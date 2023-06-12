package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableDoubleStateOf
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
    minPrice: Double,
    maxPrice: Double,
    onMinPriceChange: (Double) -> Unit,
    onMaxPriceChange: (Double) -> Unit
) {

    var seekBarMin by remember { mutableFloatStateOf(minPrice.toFloat()) }
    var seekBarMax by remember { mutableFloatStateOf(maxPrice.toFloat()) }

    Text(text = "Price", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))
    RangeSlider(
        value = seekBarMin..seekBarMax,
        onValueChange = { range ->
            seekBarMin = range.start
            seekBarMax = range.endInclusive
            onMinPriceChange(range.start.toDouble())
            onMaxPriceChange(range.endInclusive.toDouble())
        },
        colors = SliderDefaults.colors(
            thumbColor = Color.Blue,
            activeTrackColor = Color.Blue,
            inactiveTrackColor = Color.LightGray
        ),
        steps = 100,
        valueRange = 0.0f..5000.0f,
    )
    Spacer(modifier = Modifier.height(0.dp))
    Row {
        Column(modifier = Modifier.weight(1f).height(75.dp)) {
            Text(
                text = "Min Price",
                style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = minPrice.toString(),
                onValueChange = { value ->
                    onMinPriceChange(value.toDoubleOrNull() ?: 0.0)
                    val floatValue = value.toFloatOrNull() ?: 0f
                    seekBarMin = floatValue.coerceIn(0f, seekBarMax)
                },
                modifier = Modifier.weight(1f).height(48.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f).height(75.dp)) {
            Text(
                text = "Max Price",
                style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = maxPrice.toString(),
                onValueChange = { value ->
                    onMaxPriceChange(value.toDoubleOrNull()?.coerceAtMost(5000.0) ?: 5000.0)
                    val floatValue = value.toFloatOrNull() ?: 5000.0f
                    seekBarMax = floatValue.coerceIn(seekBarMin, 5000.0f)
                },
                modifier = Modifier.weight(1f).height(48.dp)
            )
        }
    }

}




@Preview(showBackground = true)
@Composable
fun PriceSectionPreview() {
    var minPrice by remember { mutableDoubleStateOf(0.0) }
    var maxPrice by remember { mutableDoubleStateOf(0.0) }
    PriceSection(
        minPrice = minPrice,
        maxPrice = maxPrice,
        onMinPriceChange = { minPrice = it },
        onMaxPriceChange = { maxPrice = it }
    )
}
