package com.miab.arealhouse.maps_screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
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
        position = CameraPosition.fromLatLngZoom(firstApartmentLocation, 17f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (permissionState.value) {
            MapContent(cameraPosition, apartmentList)
        }else{}
    }

    LaunchedEffect(key1 = true) {
        if (!permissionState.value) {
            requestPermissionLauncher.launch(permissions)
        }
    }
}

@Composable
private fun MapContent(cameraPosition: CameraPositionState, apartmentList: List<Apartment>) {
    val properties = MapProperties(
        mapType = MapType.NORMAL,
        isMyLocationEnabled = true,
        isTrafficEnabled = false
    )
    val uiSettings = MapUiSettings(zoomControlsEnabled = true)

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPosition
    ) {
        apartmentList.forEach { apartment ->
            val apartmentLocation = LatLng(apartment.latitude, apartment.longitude)
            Marker(
                state = MarkerState(position = apartmentLocation),
                title = apartment.name,
                snippet = "Marker for ${apartment.name}",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
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
