package com.miab.arealhouse.maps_screen

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment

@Composable
fun MapView(apartmentList: List<Apartment>) {
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

    val cameraPosition = rememberCameraPositionState {
        val firstApartmentLocation = LatLng(apartmentList[0].latitude, apartmentList[0].longitude)
        position = CameraPosition.fromLatLngZoom(firstApartmentLocation, 15f)
    }

    var selectedApartment by remember { mutableStateOf(apartmentList.first()) }
    var selectedMarker by remember { mutableStateOf(apartmentList.first()) }

    val lazyListState = rememberLazyListState()

    Column {
        Box(modifier = Modifier.fillMaxSize()) {
            if (permissionState.value) {
                MapContent(
                    cameraPosition = cameraPosition,
                    apartmentList = apartmentList,
                    selectedMarker = selectedMarker,
                    onMarkerClick = { apartment ->
                        selectedApartment = apartment
                        selectedMarker = apartment

                    }
                )
                LazyRow(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    state = lazyListState
                ) {
                    items(apartmentList) { apartment ->
                        ApartmentMapCard(
                            apartment = apartment,
                            isSelected = apartment == selectedApartment,
                            onCardClick = {
                                selectedApartment = apartment
                                selectedMarker = apartment

                                val apartmentLocation = LatLng(apartment.latitude, apartment.longitude)
                                cameraPosition.position = CameraPosition.fromLatLngZoom(apartmentLocation, 15f)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            } else {
                // Handle the case where permissions are not granted
            }
        }
    }

    ScrollToApartment(apartmentList, selectedApartment, lazyListState)


    LaunchedEffect(key1 = true) {
        if (!permissionState.value) {
            requestPermissionLauncher.launch(permissions)
        }
    }
}

@Composable
fun ScrollToApartment(
    apartmentList: List<Apartment>,
    selectedApartment: Apartment,
    lazyListState: LazyListState
) {
    val index = apartmentList.indexOf(selectedApartment)
    if (index != -1) {
        LaunchedEffect(key1 = index) {
            lazyListState.animateScrollToItem(index)
        }
    }
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable!!.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Composable
private fun MapContent(
    cameraPosition: CameraPositionState,
    apartmentList: List<Apartment>,
    selectedMarker: Apartment,
    onMarkerClick: (Apartment) -> Unit
) {
    val context = LocalContext.current
    val mapStyle = MapStyleOptions.loadRawResourceStyle(
        LocalContext.current,
        R.raw.map_style
    )
    val properties = MapProperties(
        mapType = MapType.NORMAL,
        isMyLocationEnabled = true,
        isTrafficEnabled = false,
        mapStyleOptions = mapStyle,
    )
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false,
        rotationGesturesEnabled = false,
        mapToolbarEnabled = false,
    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPosition
    ) {
        apartmentList.forEach { apartment ->
            val apartmentLocation = LatLng(apartment.latitude, apartment.longitude)
            val isSelectedMarker = apartment == selectedMarker

            val pinUnselected = bitmapDescriptorFromVector(context, R.drawable.pin_unselected)
            val pinSelected = bitmapDescriptorFromVector(context, R.drawable.pin_selected)
            Marker(
                state = MarkerState(position = apartmentLocation),
                title = apartment.name,
                snippet = "Marker for ${apartment.name}",
                icon = if (isSelectedMarker) pinSelected else pinUnselected,
                onClick = {
                    onMarkerClick(apartment)
                    true
                }
            )
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
