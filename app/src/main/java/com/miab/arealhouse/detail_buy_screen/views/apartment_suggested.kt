package com.miab.arealhouse.detail_buy_screen.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentsCard


@Composable
fun ShowSuggestedApartments(apartments: List<Apartment>, apartment: Apartment) {
    val suggestedApartments = remember { getRandomSuggestedApartments(apartments, 2, apartment) }

    Column{
        Text(
            text = "Suggested Apartments",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        suggestedApartments.forEach { apartment ->
            ApartmentsCard(apartment = apartment)
        }
    }
    Spacer(Modifier.height(64.dp))
}

// Helper function to get random suggested apartments without duplicates
private fun getRandomSuggestedApartments(apartments: List<Apartment>, count: Int, currentApartment: Apartment?): List<Apartment> {
    val shuffledApartments = apartments.shuffled()
    val filteredApartments = shuffledApartments.filter { it != currentApartment }
    return filteredApartments.take(count)
}

@Preview(showBackground = true)
@Composable
fun ApartmentSuggestedPreview(){ }