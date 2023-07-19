package com.miab.arealhouse.maps_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment

@Composable
fun ApartmentMapCard(apartment: Apartment, isSelected: Boolean, onCardClick: () -> Unit = {}){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(1.dp, if (isSelected) Color.Green else Color.Gray, RoundedCornerShape(8.dp))
            .background(Color.White)
            .clip(RoundedCornerShape(bottomStart = 8.dp))
            .clickable { onCardClick() }
    ) {
        Row(modifier = Modifier.height(100.dp).width(325.dp).clip(RoundedCornerShape(bottomStart = 8.dp))) {
            ApartmentImage(imageUrl = apartment.imageUrl)
            Spacer(modifier = Modifier.width(14.dp))
            Column(Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                ApartmentName(name = apartment.name)
                Spacer(modifier = Modifier.height(8.dp))
                ApartmentPrice(price = apartment.price, isSale = apartment.isSale)
                Spacer(modifier = Modifier.weight(0.75f))
                ApartmentFacility(bedroom = apartment.bedroom, bathroom = apartment.bathroom, parking = apartment.parking)
            }
        }
    }
}

@Composable
fun ApartmentImage(imageUrl: Int){
    Image(
        painter = painterResource(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        contentScale = ContentScale.FillHeight
    )
}

@Composable
fun ApartmentName(name: String){
    Text(
        text = name,
        style = TextStyle(color = Color.Black, fontSize = 18.sp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ApartmentPrice(price: Double, isSale: Boolean){
    Text(
        text = if (isSale) "$$price" else "$$price/month",
        style = TextStyle(color = Color.Blue, fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
    )
}

@Composable
fun ApartmentFacility(bedroom: Int, bathroom: Int, parking: Int){
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ApartmentFacilityIcon(R.drawable.bed, "Bedroom", " $bedroom")
        ApartmentFacilityIcon(R.drawable.bathroom, "Bathroom", " $bathroom")
        ApartmentFacilityIcon(R.drawable.car, "Parking", " $parking")
    }
}

@Composable
fun ApartmentFacilityIcon(drawable: Int, contentDescription: String, text: String){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(drawable),
            contentDescription = contentDescription
        )
        Text(text, style = TextStyle(fontSize = 12.sp))
    }
}

@Preview(showBackground = true)
@Composable
fun ApartmentsMapCardPreview() {
//    ApartmentMapCard()
}