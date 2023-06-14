package com.miab.arealhouse.home_screen.tab_layout.screens.views

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miab.arealhouse.list
import kotlinx.android.parcel.Parcelize

class ApartmentViewModel : ViewModel() {
    var filterOptions = MutableLiveData(FilterOptions())
    val apartments: MutableLiveData<List<Apartment>> = MutableLiveData(list)


    fun updateFilterSettings(filterOptions: FilterOptions) {
        this.filterOptions.value = filterOptions
    }

    fun clearFilters() {
        filterOptions.value = FilterOptions()
        apartments.value = list
    }

    fun filterByName(query: String) {
        val filteredList = list.filter { apartment ->
            apartment.name.contains(query, ignoreCase = true)
        }
        apartments.value = filteredList
    }

    fun filterBySale(isSale: Boolean) {
        val filteredList = list.filter { apartment ->
            apartment.isSale == isSale
        }
        apartments.value = filteredList
    }


    fun applyFilters(filterOptions: FilterOptions) {
        if (filterOptions != FilterOptions()) {
            val filteredList = list.filter { apartment ->
                val isHomeType = apartment.homeType == filterOptions.homeType || filterOptions.homeType == ""
                val isPriceValid = apartment.price in filterOptions.minPrice..filterOptions.maxPrice
                val isBedroomValid = apartment.bedroom == filterOptions.bedroomCount || filterOptions.bedroomCount == 0
                val isBathroomValid = apartment.bathroom == filterOptions.bathroomCount || filterOptions.bathroomCount == 0
                val isParkingValid = apartment.parking == filterOptions.parkingCount || filterOptions.parkingCount == 0
                val isOwnerPropertyValid = apartment.ownerProperty == filterOptions.propertyType || filterOptions.propertyType == ""
                val isCountryValid = apartment.country == filterOptions.country || filterOptions.country == ""
                val isCityValid = apartment.city == filterOptions.city || filterOptions.city == ""

                val isFacilityValid = filterOptions.facilities.entries.all { entry ->
                    !entry.value || apartment.facilities.getOrDefault(entry.key, false)
                }

                isHomeType && isPriceValid && isBedroomValid && isBathroomValid && isParkingValid && isOwnerPropertyValid
                        && isCountryValid && isCityValid && isFacilityValid
            }.toMutableList()

            apartments.value = filteredList
        }
    }
}

@Parcelize
data class FilterOptions(
    val homeType: String = "",
    val minPrice: Double = 0.0,
    val maxPrice: Double = Double.MAX_VALUE,
    val bedroomCount: Int = 0,
    val bathroomCount: Int = 0,
    val parkingCount: Int = 0,
    val propertyType: String = "",
    val country: String = "",
    val city: String = "",
    val facilities: Map<String, Boolean> = mapOf()
    // Add more fields for other filter settings
) : Parcelable
