package com.miab.arealhouse.home_screen.bottom_nav.screens

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.search_bar.SearchBar
import com.miab.arealhouse.home_screen.tab_layout.TabLayout
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.list

var names = listOf("Rent", "Sale")

@ExperimentalComposeUiApi
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun HomeScreen(){
    val keyboardController = LocalSoftwareKeyboardController.current
    val apartmentViewModel: ApartmentViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { keyboardController!!.hide() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var searchQuery by remember { mutableStateOf("") }
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            onSearch = { query ->
                searchQuery = query
                apartmentViewModel.filterByName(searchQuery)
            }
        )
        TabLayout(tabNames = names, modifier = Modifier.weight(1f), apartmentViewModel = apartmentViewModel)
        Spacer(modifier = Modifier.weight(0.001f))
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}