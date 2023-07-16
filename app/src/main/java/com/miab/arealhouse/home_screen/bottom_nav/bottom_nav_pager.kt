package com.miab.arealhouse.home_screen.bottom_nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.miab.arealhouse.home_screen.bottom_nav.screens.FavoriteScreen
import com.miab.arealhouse.home_screen.bottom_nav.screens.HomeScreen
import com.miab.arealhouse.home_screen.bottom_nav.screens.MessageScreen
import com.miab.arealhouse.home_screen.bottom_nav.screens.ProfileScreen
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel

@ExperimentalComposeUiApi
@Composable
fun BottomNavPager(iconsList: List<Pair<Int, String>>,
                   pagerState: PagerState,
                   modifier: Modifier = Modifier,
                   apartmentViewModel: ApartmentViewModel,
                   context: Context)
{
    HorizontalPager(
        count = iconsList.size,
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { page ->
        when(page) {
            0 -> HomeScreen(context, apartmentViewModel = apartmentViewModel)
            1 -> FavoriteScreen()
            2 -> MessageScreen()
            3 -> ProfileScreen()
            // add more screens here if necessary
        }
    }
}