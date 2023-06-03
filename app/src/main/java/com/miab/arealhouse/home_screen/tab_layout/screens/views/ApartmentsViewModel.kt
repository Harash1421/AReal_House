package com.miab.arealhouse.home_screen.tab_layout.screens.views

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

class ApartmentViewModel : ViewModel() {
    private val _apartments = MutableLiveData<List<Apartment>>()
    val apartments: LiveData<List<Apartment>> = _apartments

    fun setApartments(apartments: List<Apartment>) {
        _apartments.value = apartments
    }

    lateinit var propertyConditions: Array<String>

    fun applyFilters(filterOptions: FilterOptions) {
        // Replace with your own filtering logic
        _apartments.value = _apartments.value?.filter { apartment ->
            val priceInRange = apartment.price.removePrefix("$").toDouble().let { price ->
                price >= filterOptions.minPrice.toDouble() && price <= filterOptions.maxPrice.toDouble()
            }
            val bedroomInRange = apartment.bedroom >= filterOptions.minBedroom && apartment.bedroom <= filterOptions.maxBedroom
            val bathroomInRange = apartment.bathroom >= filterOptions.minBathroom && apartment.bathroom <= filterOptions.maxBathroom
            val parkingInRange = apartment.parking >= filterOptions.minParking && apartment.parking <= filterOptions.maxParking
            val homeTypeMatch = apartment.homeType == filterOptions.selectedHomeType
            val propertyTypeMatch = apartment.ownerProperty == propertyConditions[filterOptions.selectedPropertyType]
            val facilitiesMatch = filterOptions.facilities.all { (key, value) ->
                !value || (value && apartment.facilities[key] == true)
            }

            priceInRange && bedroomInRange && bathroomInRange && parkingInRange && homeTypeMatch && propertyTypeMatch && facilitiesMatch
        }
    }
}

@Parcelize
data class FilterOptions(
    var selectedHomeType: Int = 0,
    var selectedPropertyType: Int = 0,
    var minPrice: String = "",
    var maxPrice: String = "",
    var minBedroom: Int = 0,
    var maxBedroom: Int = 0,
    var minBathroom: Int = 0,
    var maxBathroom: Int = 0,
    var minParking: Int = 0,
    var maxParking: Int = 0,
    var facilities: Map<String, Boolean> = mapOf(
        "WiFi" to false,
        "AC" to false,
        "Fully Furnished" to false,
        "24 Hour Access" to false
    )
): Parcelable

