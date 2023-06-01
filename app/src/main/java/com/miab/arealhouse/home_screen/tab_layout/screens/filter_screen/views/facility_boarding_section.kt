package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FacilityBoardingSection(facilities: MutableState<Map<String, Boolean>>){
    Text(text = "Boarding Facilities", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(8.dp))

    facilities.value.keys.forEach { facility ->
        CheckboxOption(
            text = facility,
            checked = facilities.value[facility] ?: false,
            onCheckedChange = {
                facilities.value = facilities.value.toMutableMap().apply {
                this[facility] = !(this[facility] ?: false)
                }
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
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
