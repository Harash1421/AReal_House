package com.miab.arealhouse.home_screen.bottom_nav.screens.favorite_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentsCard
import com.miab.arealhouse.home_screen.tab_layout.screens.views.SortOption


@Composable
fun FavoriteScreen(apartmentViewModel: ApartmentViewModel = viewModel()) {
    val apartments = apartmentViewModel.apartments.observeAsState(initial = emptyList())
    var sortOption by remember { mutableStateOf(SortOption.PRICE_HIGH_TO_LOW) }
    var sortedApartments by remember { mutableStateOf(apartments.value) }

    // Sort the apartments based on the selected option
    LaunchedEffect(sortOption) {
        sortedApartments = when (sortOption) {
            SortOption.PRICE_HIGH_TO_LOW -> apartments.value.sortedByDescending { it.price }
            SortOption.PRICE_LOW_TO_HIGH -> apartments.value.sortedBy { it.price }
            SortOption.SIZE_HIGH_TO_LOW -> apartments.value.sortedByDescending { it.calculateTotalSize() }
            SortOption.SIZE_LOW_TO_HIGH -> apartments.value.sortedBy { it.calculateTotalSize() }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarConfiguration(onSortClick = { newSortOption ->
            // Update the sortOption state when a new option is selected
            sortOption = newSortOption
        })
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(sortedApartments.filter { it.isFavorite }) { index, apartment ->
                ApartmentsCard(apartment, index)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview(){
//    FavoriteScreen()
}