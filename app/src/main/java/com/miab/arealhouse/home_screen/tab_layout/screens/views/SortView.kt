package com.miab.arealhouse.home_screen.tab_layout.screens.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R

//Enum Class for Options of Sorting List
enum class SortOption {
    PRICE_HIGH_TO_LOW,
    PRICE_LOW_TO_HIGH,
    SIZE_HIGH_TO_LOW,
    SIZE_LOW_TO_HIGH
}

//Sort View for Rent and Sale Screen
@Composable
fun SortView(onSortClick: (SortOption) -> Unit = {}){
    var showDialog by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Closets to you")
        IconButton(onClick = { showDialog = true }) {
            Icon(
                painter = painterResource(id = R.drawable.sort),
                contentDescription = "Sort",
                tint = Color.Black
            )
        }
    }

    // Sort Dialog to display options when Sort Icon is clicked
    if (showDialog) {
        SortDialog(
            onDismiss = { showDialog = false },
            onSortOptionSelected = { sortOption ->
                showDialog = false
                onSortClick(sortOption)
            }
        )
    }
}


@Composable
fun SortDialog(
    onDismiss: () -> Unit,
    onSortOptionSelected: (SortOption) -> Unit
) {
    // Dialog to show sorting options
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { androidx.compose.material.Text(text = "Sort By") },
        text = {
            Column {
                // Sort option buttons
                SortOptionButton(
                    text = "Highest Price",
                    onClick = {
                        onSortOptionSelected(SortOption.PRICE_HIGH_TO_LOW)
                    }
                )
                SortOptionButton(
                    text = "Lowest Price",
                    onClick = {
                        onSortOptionSelected(SortOption.PRICE_LOW_TO_HIGH)
                    }
                )
                SortOptionButton(
                    text = "Size (High to Low)",
                    onClick = {
                        onSortOptionSelected(SortOption.SIZE_HIGH_TO_LOW)
                    }
                )
                SortOptionButton(
                    text = "Size (Low to High)",
                    onClick = {
                        onSortOptionSelected(SortOption.SIZE_LOW_TO_HIGH)
                    }
                )
            }
        },
        dismissButton = {
            // Cancel button in the dialog
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
            ) {
                androidx.compose.material.Text(text = "Cancel")
            }
        },
        confirmButton = {},
        modifier = Modifier
    )
}

@Composable
fun SortOptionButton(
    text: String,
    onClick: () -> Unit
) {
    // Button for each sort option
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent)
    ) {
        androidx.compose.material.Text(text = text)
    }
}