package com.miab.arealhouse.home_screen.bottom_nav.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.miab.arealhouse.home_screen.search_bar.SearchBar
import com.miab.arealhouse.home_screen.tab_layout.TabLayout

var names = listOf("Rent", "Sale")
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBar(modifier = Modifier.fillMaxWidth())
        TabLayout(tabNames = names, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(0.001f))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}