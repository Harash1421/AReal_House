package com.miab.arealhouse.home_screen.bottom_nav.screens.favorite_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R


@Composable
fun TopBarConfiguration(onSortClick: (SortOption) -> Unit = {}) {
    var showDialog by remember { mutableStateOf(false) }

    // Top App Bar with Title and Sort Icon
    TopAppBar(
        title = {
            Text(text = "Favorites")
        },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        actions = {
            // Sort Icon Button
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.sort),
                    contentDescription = "Sort",
                    tint = Color.Black
                )
            }
        }
    )

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
        title = { Text(text = "Sort By") },
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
                Text(text = "Cancel")
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
        Text(text = text)
    }
}