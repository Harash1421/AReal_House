package com.miab.arealhouse.home_screen.tab_layout

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.miab.arealhouse.home_screen.tab_layout.screens.RentScreen
import com.miab.arealhouse.home_screen.tab_layout.screens.SaleScreen
import com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.FilterActivity
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import com.miab.arealhouse.home_screen.tab_layout.screens.views.SortView

@Composable
fun TabLayout(tabNames: List<String>,
              modifier: Modifier = Modifier,
              apartmentViewModel: ApartmentViewModel = viewModel(),
              showMap: MutableState<Boolean>){
    val pagerState = rememberPagerState()
    val context = LocalContext.current
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 7.dp, start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabRow(tabNames = tabNames,
                onTabSelected = {
                    println("Tab $it selected")
                },
                pagerState = pagerState,
                modifier = Modifier.weight(0.9f))

            FilterIcon(modifier = Modifier
                .weight(0.1f)
                .align(Alignment.CenterVertically)) {
                val intent = Intent(context, FilterActivity::class.java)
                context.startActivity(intent)
            }
        }

        Divider()

        HorizontalPager(
            count = tabNames.size,
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = false
        ) { page ->
            when(page) {

                0 -> RentScreen(context, apartmentViewModel = apartmentViewModel, showMap)
                1 -> SaleScreen(apartmentViewModel = apartmentViewModel, showMap)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TabLayoutPreview(){
    val names = listOf("Rent", "Sale")
//    TabLayout(tabNames = names)
}