package com.miab.arealhouse.home_screen.tab_layout.screens.views

import android.os.Parcelable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miab.arealhouse.R
import kotlinx.android.parcel.Parcelize

class ApartmentViewModel : ViewModel() {
    private val _apartments = MutableLiveData<List<Apartment>>()
    val apartments: LiveData<List<Apartment>> = _apartments

    fun setApartments(apartments: List<Apartment>) {
        _apartments.value = apartments
    }

    init {
        _apartments.value = listOf(
            Apartment(
                imageUrl = R.drawable.image,
                name = "Luxury Loft",
                description = "This is a luxurious loft apartment.",
                price = "$3000/month",
                bedroom = 2,
                bathroom = 2,
                parking = 2,
                owner = "Jennifer",
                ownerProperty = "Grand Properties",
                isFavorite = false,
                homeType = 1,
                facilities = mapOf("Fully Furnished" to true, "WiFi" to true, "AC" to true)
            ),
            Apartment(
                imageUrl = R.drawable.image_second,
                name = "Cosy Studio",
                description = "A cosy studio apartment in the city center.",
                price = "$1200/month",
                bedroom = 1,
                bathroom = 1,
                parking = 0,
                owner = "Brian",
                ownerProperty = "CityHomes",
                isFavorite = true,
                homeType = 2,
                facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to true)
            ),
            Apartment(
                imageUrl = R.drawable.image,
                name = "Suburban House",
                description = "A spacious house in the suburbs.",
                price = "$2200/month",
                bedroom = 4,
                bathroom = 3,
                parking = 2,
                owner = "Emma",
                ownerProperty = "Suburb Realty",
                isFavorite = true,
                homeType = 3,
                facilities = mapOf("Fully Furnished" to false, "WiFi" to true, "AC" to false)
            ),
            Apartment(
                imageUrl = R.drawable.image_second,
                name = "Downtown Flat",
                description = "A modern flat in the downtown area.",
                price = "$1800/month",
                bedroom = 2,
                bathroom = 2,
                parking = 1,
                owner = "Oliver",
                ownerProperty = "Downtown Estates",
                isFavorite = false,
                homeType = 1,
                facilities = mapOf("Fully Furnished" to true, "WiFi" to true, "AC" to true)
            )
        )
    }

    lateinit var propertyConditions: Array<String>

    private val _filterOptions = mutableStateOf(FilterOptions())

    // Expose the filter options as read-only state
    val filterOptions: State<FilterOptions> = _filterOptions

    // Function to update the filter options
    fun updateFilterOptions(filterOptions: FilterOptions) {
        _filterOptions.value = filterOptions
        applyFilters(filterOptions)
    }

    private fun applyFilters(filterOptions: FilterOptions) {
        // Replace with your own filtering logic
        _apartments.value = _apartments.value?.filter { apartment ->
            val priceInRange = apartment.price.removePrefix("$").toDouble().let { price ->
                price >= filterOptions.minPrice.toDouble() && price <= filterOptions.maxPrice.toDouble()
            }
            val bedroomInRange = apartment.bedroom >= filterOptions.bedroom
            val bathroomInRange = apartment.bathroom >= filterOptions.bathroom
            val parkingInRange = apartment.parking >= filterOptions.parking
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
    var bedroom: Int = 0,
    var bathroom: Int = 0,
    var parking: Int = 0,
    var facilities: Map<String, Boolean> = mapOf(
        "WiFi" to false,
        "AC" to false,
        "Fully Furnished" to false,
        "24 Hour Access" to false
    )
): Parcelable
