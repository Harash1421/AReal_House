package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FacilityBoardingSection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FacilitySection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.FilterActions
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.HomeType
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.PriceSection
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views.PropertyConditionPicker
import com.miab.arealhouse.ui.theme.ARealHouseTheme

class FilterActivity : ComponentActivity() {
     @SuppressLint("AutoboxingStateValueProperty")
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARealHouseTheme {
                val context = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = { Text(text = "Filter") },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                }
                            },
                            backgroundColor = MaterialTheme.colors.surface,
                            contentColor = MaterialTheme.colors.onSurface,
                        )

                        var selectedHomeType by remember { mutableIntStateOf(0) }
                        var selectedPropertyType by remember { mutableIntStateOf(0) }
                        var minPrice by remember { mutableStateOf("") }
                        var maxPrice by remember { mutableStateOf("") }

                        val bedroomCount = remember { mutableIntStateOf(0) }
                        val bathroomCount = remember { mutableIntStateOf(0) }
                        val parkingCount = remember { mutableIntStateOf(0) }

                        val facilities = remember {
                            mutableStateOf(
                                mapOf(
                                    "WiFi" to false,
                                    "AC" to false,
                                    "Fully Furnished" to false,
                                    "24 Hour Access" to false
                                )
                            )
                        }

                        Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {

                            HomeType(
                                selectedItem = selectedHomeType,
                            ){ text, index ->
                                selectedHomeType = index
                            }

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            PriceSection(
                                minPrice = minPrice,
                                maxPrice = maxPrice,
                                onMinPriceChange = { minPrice = it },
                                onMaxPriceChange = { maxPrice = it }
                            )

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            PropertyConditionPicker(
                                selectedPropertyType
                            ) { text, index ->
                                selectedPropertyType = index
                            }

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            FacilitySection(
                                bedroomCount,
                                bathroomCount,
                                parkingCount
                            )

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            FacilityBoardingSection(facilities = facilities)


                            Spacer(modifier = Modifier.height(48.dp))
                        }

                            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                                FilterActions(
                                    onReset = {
                                        // Reset your filters here
                                        selectedHomeType = 0
                                        selectedPropertyType = 0
                                        minPrice = ""
                                        maxPrice = ""
                                        bedroomCount.value = 0
                                        bathroomCount.value = 0
                                        parkingCount.value = 0
                                        facilities.value = facilities.value.mapValues { false }
                                    },
                                    onApply = {
                                        // Compose your filter string here
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FilterActivityPreview() {
    ARealHouseTheme {
        FilterActivity()
    }
}
