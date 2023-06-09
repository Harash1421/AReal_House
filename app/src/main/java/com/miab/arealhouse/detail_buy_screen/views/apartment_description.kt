package com.miab.arealhouse.detail_buy_screen.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment

@Composable
fun ApartmentDescription(apartment: Apartment) {
    val (showFullText, setShowFullText) = remember { mutableStateOf(false) }
    Column {
        Text(
            text = "Description:",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
        Spacer(Modifier.height(7.dp))
        Text(
            text = if (showFullText) apartment.description else apartment.description.take(200) + "...",
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 15.sp)
        )
        // Only show the "More" and "Less" button if the description is longer than 100 characters
        if (apartment.description.length > 200) {
            TextButton(onClick = { setShowFullText(!showFullText) }) {
                Text(if (showFullText) "Less" else "More", color = Color.Blue)
            }
        }
    }
    Divider()
    Spacer(Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun ApartmentDescriptionPreview(){ }