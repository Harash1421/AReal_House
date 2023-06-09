package com.miab.arealhouse.detail_buy_screen.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment


@Composable
fun ApartmentFacilities1(apartment: Apartment?){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FacilityViews(image = R.drawable.bed, fName = "Bedrooms", fCount = apartment!!.bedroom)
            Spacer(Modifier.width(15.dp))
            FacilityViews(image = R.drawable.bathroom, fName = "Bathrooms", fCount = apartment.bathroom)
            Spacer(Modifier.width(15.dp))
            FacilityViews(image = R.drawable.car, fName = "Parking", fCount = apartment.parking)
        }
    }

    Divider()
}

@Composable
fun ApartmentFacilities2(apartment: Apartment?){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FacilityViews(image = R.drawable.size, fName = "Land Size", fCount = apartment!!.landSize, isSize = true)
            Spacer(Modifier.width(15.dp))
            FacilityViews(image = R.drawable.size, fName = "Home Size", fCount = apartment.homeSize, isSize = true)
        }
    }
    Divider()
    Spacer(Modifier.height(10.dp))
}


@Composable
fun FacilityViews(image: Int, fName:String, fCount: Int, isSize:Boolean = false){
    Image(
        painter = painterResource(image),
        contentDescription = null,
        Modifier.size(27.dp)
    )
    Spacer(Modifier.width(4.dp))
    Text(text = "$fName\n${fCount ?: 0} ${if (isSize) "M2" else ""}")
}

@Preview(showBackground = true)
@Composable
fun ApartmentFacilitiesPreview(){ }