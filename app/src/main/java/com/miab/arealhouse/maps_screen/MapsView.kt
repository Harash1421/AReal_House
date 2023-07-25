package com.miab.arealhouse.maps_screen

import android.Manifest
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.miab.arealhouse.detail_buy_screen.DetailBuyActivity
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel

@Composable
fun MapView(apartmentViewModel: ApartmentViewModel = viewModel()) {
    val apartments by apartmentViewModel.apartments.observeAsState(initial = emptyList())

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val permissionState = remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            permissionState.value = it[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        }
    )

    val context = LocalContext.current

    val cameraPosition = rememberCameraPositionState {
        val firstApartmentLocation = LatLng(apartments[0].latitude, apartments[0].longitude)
        position = CameraPosition.fromLatLngZoom(firstApartmentLocation, 15f)
    }

    val selectedApartment = remember { mutableStateOf(apartments.first()) }
    val selectedMarker = remember { mutableStateOf(apartments.first()) }

    val pagerState = rememberPagerState(
        initialPage = apartments.indexOf(selectedApartment.value)
    )

    // Filter apartments based on the visible area of the map
    val currentCameraPosition = cameraPosition.position
    val zoomLevel = currentCameraPosition.zoom
    val visibleApartments = apartments.filter { apartment ->
        val apartmentLocation = LatLng(apartment.latitude, apartment.longitude)
        apartmentLocation.latitude in (currentCameraPosition.target.latitude - zoomLevel) .. (currentCameraPosition.target.latitude + zoomLevel) &&
                apartmentLocation.longitude in (currentCameraPosition.target.longitude - zoomLevel) .. (currentCameraPosition.target.longitude + zoomLevel)
    }

    // Update the selected apartment when the pager state changes
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                val apartment = visibleApartments[page]
                selectedApartment.value = apartment
                selectedMarker.value = apartment
            }
    }

    Column {
        Box(modifier = Modifier.fillMaxSize()) {
            if (permissionState.value) {
                MapContent(
                    context,
                    cameraPosition = cameraPosition,
                    apartmentList = apartments,
                    selectedMarker = selectedMarker.value,
                    onMarkerClick = { apartment ->
                        selectedApartment.value = apartment
                        selectedMarker.value = apartment
                        // When a marker is clicked, scroll the HorizontalPager to the selected apartment
                    }
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    count = visibleApartments.size
                ) { page ->
                    val apartment = visibleApartments[page]
                        if(cameraPosition.position.zoom >= 10f) {
                            ApartmentMapCard(
                                apartment = apartment,
                                isSelected = apartment == selectedApartment.value,
                                onCardClick = {
                                    val intent = Intent(context, DetailBuyActivity::class.java)
                                    intent.putExtra("Apartment", apartment)
                                    intent.putExtra("Index", visibleApartments[page])
                                    context.startActivity(intent)
                                }
                            )
                        }else{
                            // Handle the case where Camera Position are not 10f
                        }
                }
            } else {
                // Handle the case where permissions are not granted
            }
        }
    }

    ScrollPagerToApartment(visibleApartments, selectedApartment.value, pagerState)

    LaunchedEffect(key1 = true) {
        if (!permissionState.value) {
            requestPermissionLauncher.launch(permissions)
        }
    }
}

@Composable
fun ScrollPagerToApartment(
    visibleApartments:List<Apartment>,
    apartment: Apartment,
    pagerState: PagerState) {
    // Scroll the HorizontalPager to show the selected apartment
    val index = visibleApartments.indexOf(apartment)
    if (index != -1) {
        LaunchedEffect(key1 = index) {
            pagerState.animateScrollToPage(index)
        }
    }
}

//@Composable
//private fun RequestPermissionUI(
//    permissions: Array<String>,
//    requestPermissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
//) {
//    // Implement UI to request permission from the user
//    // and handle the result from the requestPermissionLauncher
//}
