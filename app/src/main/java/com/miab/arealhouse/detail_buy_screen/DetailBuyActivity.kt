package com.miab.arealhouse.detail_buy_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.R
import com.miab.arealhouse.detail_buy_screen.views.ApartmentDescription
import com.miab.arealhouse.detail_buy_screen.views.ApartmentFacilities1
import com.miab.arealhouse.detail_buy_screen.views.ApartmentFacilities2
import com.miab.arealhouse.detail_buy_screen.views.ApartmentImage
import com.miab.arealhouse.detail_buy_screen.views.ApartmentTextDetail
import com.miab.arealhouse.detail_buy_screen.views.BottomActions
import com.miab.arealhouse.detail_buy_screen.views.ShowSuggestedApartments
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentsCard
import com.miab.arealhouse.list
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
                    Box(modifier = Modifier.fillMaxSize()){
                        Column(modifier = Modifier.fillMaxSize()) {
                            TopBarConfiguration(apartment = apartment) {
                                onBackPressed()
                            }
                            DetailScreen(apartment = apartment)
                        }

                        Box(modifier = Modifier.align(Alignment.BottomStart)) {
                            BottomActions({}, apartment!!)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DetailScreen(apartment: Apartment?) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ApartmentImage(apartment?.imageUrl ?: 0)

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                ApartmentTextDetail(apartment = apartment)
                ApartmentFacilities1(apartment = apartment)
                ApartmentFacilities2(apartment = apartment)
                ApartmentDescription(apartment = apartment!!)
            }
            ShowSuggestedApartments(list, apartment!!)
        }
    }
}

@Composable
fun TopBarConfiguration(apartment: Apartment?, onClick: () -> Unit){
    TopAppBar(
        title = { Text(text = apartment!!.name ?: "Apartment Name Not Found") },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        backgroundColor = androidx.compose.material.MaterialTheme.colors.surface,
        contentColor = androidx.compose.material.MaterialTheme.colors.onSurface,
    )
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    ARealHouseTheme {
        val apartment = Apartment(
            homeType = "Apartment",
            imageUrl = R.drawable.image,
            name = "Awesome Apartment 1",
            country = "United States",
            city = "Los Angles",
            price = 2000.0,
            bedroom = 3,
            bathroom = 2,
            parking = 1,
            owner = "Ahmed",
            ownerProperty = "Simphony Property",
            isFavorite = true,
            isSale = false,
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