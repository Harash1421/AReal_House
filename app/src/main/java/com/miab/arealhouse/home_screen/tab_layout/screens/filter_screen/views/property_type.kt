package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R

@Composable
fun PropertyConditionPicker(selectedItem: Int, onItemSelected: (String, Int) -> Unit) {
    val propertyType = stringArrayResource(id = R.array.property_conditions)
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
            value = propertyType[selectedItem],
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
            propertyType.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item, index)
                    expanded = false
                }) {
                    Text(text = item)
                }
            }
        }
    }
}