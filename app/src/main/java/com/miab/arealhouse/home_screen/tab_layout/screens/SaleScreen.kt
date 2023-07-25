package com.miab.arealhouse.home_screen.tab_layout.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.MapView
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentsCard
import com.miab.arealhouse.home_screen.tab_layout.screens.views.SortOption
import com.miab.arealhouse.home_screen.tab_layout.screens.views.SortView
import com.miab.arealhouse.maps_screen.MapView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SaleScreen(apartmentViewModel: ApartmentViewModel = viewModel(), showMap: MutableState<Boolean>){
    apartmentViewModel.filterBySale(true)
    val apartments by apartmentViewModel.apartments.observeAsState(initial = emptyList())
    var sortOption by remember { mutableStateOf(SortOption.PRICE_HIGH_TO_LOW) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                keyboardController?.hide()
                return super.onPreScroll(available, source)
            }
        }
    }
    if(showMap.value){
        Column(modifier = Modifier.fillMaxSize()) {
            //Sort View Coding
            SortView(onSortClick = { newSortOption ->
                // Update the sortOption state when a new option is selected
                sortOption = newSortOption

                val sortedApartments = when (sortOption) {
                    SortOption.PRICE_HIGH_TO_LOW -> apartments.sortedByDescending { it.price }
                    SortOption.PRICE_LOW_TO_HIGH -> apartments.sortedBy { it.price }
                    SortOption.SIZE_HIGH_TO_LOW -> apartments.sortedByDescending { it.calculateTotalSize() }
                    SortOption.SIZE_LOW_TO_HIGH -> apartments.sortedBy { it.calculateTotalSize() }
                }

                apartmentViewModel.apartments.value = sortedApartments
            })

            Divider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(apartments) { index, apartment ->
                    ApartmentsCard(apartment, index)
                }
            }
        }
    }else{
        MapView(apartments)
    }
}

@Preview(showBackground = true)
@Composable
fun SaleScreenPreview(){
//    SaleScreen()
}