package com.miab.arealhouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.ui.theme.ARealHouseTheme

class DetailBuyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apartment = intent.getParcelableExtra<Apartment>("Apartment")
        setContent {
            ARealHouseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    DetailScreen(apartment)
                }
            }
        }
    }

    @Composable
    fun DetailScreen(apartment: Apartment?) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(text = apartment?.name ?: "Apartment Name Not Found") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                backgroundColor = androidx.compose.material.MaterialTheme.colors.surface,
                contentColor = androidx.compose.material.MaterialTheme.colors.onSurface,
            )

            ApartmentImage(apartment?.imageUrl ?: 0)

            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())) {

                Column(Modifier.padding(5.dp)) {
                    Text(text = apartment?.name ?: "",
                        style = TextStyle(fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold))
                    Spacer(Modifier.height(5.dp))
                    Text(text = apartment?.location ?: "",
                        style = TextStyle(fontSize = 14.sp,
                            fontWeight = FontWeight.Light))
                    Spacer(Modifier.height(14.dp))
                    Text(text = apartment?.price ?: "",
                        style = TextStyle(fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue))
                }

                Spacer(Modifier.height(10.dp))
                Divider()

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bed),
                            contentDescription = null,
                            Modifier.size(27.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = "Bedrooms\n${apartment?.bedroom ?: 0}")
                        Spacer(Modifier.width(16.dp))
                        Image(
                            painter = painterResource(R.drawable.bathroom),
                            contentDescription = null,
                            Modifier.size(27.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = "Bathrooms\n${apartment?.bathroom ?: 0}")
                        Spacer(Modifier.width(16.dp))
                        Image(
                            painter = painterResource(R.drawable.car),
                            contentDescription = null,
                            Modifier.size(27.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = "Parking\n${apartment?.parking ?: 0}")
                    }
                }

                Divider()

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.size),
                            contentDescription = null,
                            Modifier.size(27.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = "Land Size\n${apartment?.landSize ?: 0} M2")
                        Spacer(Modifier.width(16.dp))
                        Image(
                            painter = painterResource(R.drawable.size),
                            contentDescription = null,
                            Modifier.size(27.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = "Home Size\n${apartment?.homeSize ?: 0} M2")
                    }
                }


                Divider()
                Spacer(Modifier.height(10.dp))

                ShowFullText(apartment = apartment!!)
            }

        }
    }

    @Composable
    fun ShowFullText(apartment: Apartment) {
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
    }


    @Composable
    fun ApartmentImage(imageUrl: Int) {
        Image(
            painter = painterResource(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    ARealHouseTheme {
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
            description = "Classic 2-storey Modern House with Scandinavian theme in Depok, on Jalan Pekapuran, West Java\n" +
                    "\n" +
                    "Specifications as follows:\n" +
                    "2 storey building\n" +
                    "Dirty Kitchen\n" +
                    "Clean Kitchen\n" +
                    "FULL AC 11 Units\n" +
                    "Electricity 23000 Watt\n" +
                    "Front / back garden\n" +
                    "Swimming Pool",
            landSize = 90,
            homeSize = 40,
            facilities = mapOf(
                "WiFi" to true,
                "AC" to true,
                "Fully Furnished" to true,
                "24 Hour Access" to true
            )
        )
        DetailBuyActivity().DetailScreen(apartment)
    }
}