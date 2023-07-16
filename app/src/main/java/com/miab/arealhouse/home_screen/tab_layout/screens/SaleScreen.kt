package com.miab.arealhouse.home_screen.tab_layout.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.MapView
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentsCard
import com.miab.arealhouse.maps_screen.MapView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SaleScreen(apartmentViewModel: ApartmentViewModel = viewModel(), showMap: MutableState<Boolean>){
    apartmentViewModel.filterBySale(true)
    val apartments = apartmentViewModel.apartments.observeAsState(initial = emptyList())

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
        MapView(apartments.value)
    }else{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(apartments.value) { index, apartment ->
                ApartmentsCard(apartment, index)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaleScreenPreview(){
//    SaleScreen()
}