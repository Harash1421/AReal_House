package com.miab.arealhouse.home_screen.tab_layout.screens.views

import android.content.Intent
import android.os.Parcelable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.detail_buy_screen.DetailBuyActivity
import com.miab.arealhouse.R
import kotlinx.android.parcel.Parcelize

// Data class for Apartment details
@Parcelize
data class Apartment(
    val homeType: String,
    val imageUrl: Int,
    val name: String,
    val location: String,
    val price: String,
    val bedroom: Int,
    val bathroom: Int,
    val parking: Int,
    val owner: String,
    val ownerProperty: String,
    var isFavorite: Boolean,

    //Apartment Configuration
    val description: String,
    val landSize: Int,
    val homeSize: Int,
    val facilities: Map<String, Boolean>
) : Parcelable


// Composable for Apartment card
@Composable
fun ApartmentsCard(apartment: Apartment, index: Int = 0) {
    val context = LocalContext.current
    // Main card container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(Color.White)
            .clip(RoundedCornerShape(topEnd = 8.dp))
            .clickable { // Add this line to make the card clickable
                val intent = Intent(context, DetailBuyActivity::class.java)
                intent.putExtra("Apartment", apartment)
                intent.putExtra("Index", index)
                context.startActivity(intent)
            }
    ) {
        // Apartment image and details
        Column {
            ApartmentImage(apartment.imageUrl)
            ApartmentDetails(apartment)
            Spacer(modifier = Modifier.height(14.dp))
            Divider()
            OwnerInfo(apartment.owner, apartment.ownerProperty)
        }

        // Favorite icon button
        FavoriteIcon(apartment.isFavorite,
            onFavoriteClick = {
                // change the isFavorite status
                apartment.isFavorite = !apartment.isFavorite
            },
            modifier = Modifier.align(Alignment.TopEnd))
    }
}

// Composable for Apartment image
@Composable
fun ApartmentImage(imageUrl: Int) {
    Image(
        painter = painterResource(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentScale = ContentScale.FillHeight
    )
}

// Composable for Apartment details (name, description, price, icons)
@Composable
fun ApartmentDetails(apartment: Apartment) {
    Column(
        modifier = Modifier.padding(17.dp)
    ) {
        ApartmentName(apartment.name)
        ApartmentLocation(apartment.location)
        Spacer(modifier = Modifier.height(14.dp))
        ApartmentPriceAndIcons(apartment.price, apartment.bedroom, apartment.bathroom, apartment.parking)
    }
}

// Composable for Apartment name
@Composable
fun ApartmentName(name: String) {
    Text(
        text = name,
        style = TextStyle(color = Color.Black, fontSize = 18.sp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

// Composable for Apartment description
@Composable
fun ApartmentLocation(description: String) {
    Text(
        text = description,
        style = TextStyle(color = Color.Gray, fontSize = 14.sp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

// Composable for Apartment price and icons
@Composable
fun ApartmentPriceAndIcons(price: String, bedroom: Int, bathroom: Int, parking: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ApartmentPrice(price, modifier = Modifier.weight(1f))
        ApartmentIcons(bedroom, bathroom, parking)
    }
}

// Composable for Apartment price
@Composable
fun ApartmentPrice(price: String, modifier: Modifier = Modifier) {
    Text(
        text = price,
        style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
        modifier = modifier
    )
}

// Composable for Apartment icons (bed, bathroom, parking)
@Composable
fun ApartmentIcons(bedroom: Int, bathroom: Int, parking: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ApartmentIcon(R.drawable.bed, "Bedroom", " $bedroom")
        ApartmentIcon(R.drawable.bathroom, "Bathroom", " $bathroom")
        ApartmentIcon(R.drawable.car, "Parking", " $parking")
    }
}

// Composable for each Apartment icon
@Composable
fun ApartmentIcon(drawable: Int, contentDescription: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(drawable),
            contentDescription = contentDescription
        )
        Text(text, style = TextStyle(fontSize = 12.sp))
    }
}

// Composable for Favorite icon
@Composable
fun FavoriteIcon(isFavorite: Boolean, onFavoriteClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onFavoriteClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = Color.Red
        )
    }
}


// Displays owner's info
@Composable
fun OwnerInfo(owner: String, ownerProperty: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OwnerImage()
        Spacer(modifier = Modifier.width(8.dp))
        OwnerDetails(owner, ownerProperty)
    }
}

// Displays owner's image
@Composable
fun OwnerImage() {
    Image(
        painter = painterResource(R.drawable.image),
        contentDescription = "Owner Image",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}

// Displays owner's details
@Composable
fun OwnerDetails(owner: String, ownerProperty: String) {
    Row {
        OwnerName(owner)
        Spacer(modifier = Modifier.width(4.dp))
        OwnerPropertyType(ownerProperty)
    }
}

// Displays owner's name
@Composable
fun OwnerName(owner: String) {
    Text(
        text = owner,
        style = TextStyle(color = Color.Black, fontSize = 12.sp)
    )
}

// Displays owner's property type
@Composable
fun OwnerPropertyType(ownerProperty: String) {
    Text(
        text = ownerProperty,
        style = TextStyle(color = Color.Gray, fontSize = 12.sp)
    )
}


// Preview of ApartmentsCard
@Preview(showBackground = true)
@Composable
fun ApartmentsCardPreview() {
    val apartment = Apartment(
        homeType = "Apartment",
        imageUrl = R.drawable.image,
        name = "Awesome Apartment 1",
        location = "Los Angles, United States",
        price = "$2000/month",
        bedroom = 3,
        bathroom = 2,
        parking = 1,
        owner = "Ahmed",
        ownerProperty = "Simphony Property",
        isFavorite = true,
        description = "This is a loft Apartment",
        landSize = 90,
        homeSize = 40,
        facilities = mapOf(
            "WiFi" to true,
            "AC" to true,
            "Fully Furnished" to true,
            "24 Hour Access" to true
        )
    )
    ApartmentsCard(apartment = apartment)
}

