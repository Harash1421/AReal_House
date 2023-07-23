package com.miab.arealhouse.maps_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment

@Composable
fun MapContent(
    context: Context,
    cameraPosition: CameraPositionState,
    apartmentList: List<Apartment>,
    selectedMarker: Apartment,
    onMarkerClick: (Apartment) -> Unit
) {
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
        val currentCameraPosition = cameraPosition.position
        val zoomLevel = currentCameraPosition.zoom

        // Show the apartments only when the camera is zoomed to a level greater than 15f
        if (zoomLevel > 10f) {
            val visibleApartments = apartmentList.filter {
                it.latitude in (currentCameraPosition.target.latitude - zoomLevel) .. (currentCameraPosition.target.latitude + zoomLevel)
                        && it.longitude in (currentCameraPosition.target.longitude - zoomLevel) .. (currentCameraPosition.target.longitude + zoomLevel)
            }

            visibleApartments.forEach { apartment ->
                val apartmentLocation = LatLng(apartment.latitude, apartment.longitude)
                val isSelectedMarker = apartment == selectedMarker

                val pinUnselected = bitmapDescriptorFromVector(context, R.drawable.pin_unselected)
                val pinSelected = bitmapDescriptorFromVector(context, R.drawable.pin_selected)

                Marker(
                    state = MarkerState(position = apartmentLocation),
                    icon = if (isSelectedMarker) pinSelected else pinUnselected,
                    onClick = {
                        onMarkerClick(apartment)
                        true
                    }
                )
            }
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