package com.miab.arealhouse.home_screen.bottom_nav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.rememberPagerState
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment
import com.miab.arealhouse.home_screen.tab_layout.screens.views.ApartmentViewModel
import kotlinx.coroutines.launch

// Define the list of icons and labels for the bottom navigation
val bottomList = listOf(
    R.drawable.home to "Home",
    R.drawable.favorite to "Favorite",
    R.drawable.message to "Message",
    R.drawable.profile to "Profile"
)

@ExperimentalComposeUiApi
@Composable
fun BottomBar() {
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()

    // Column to arrange the BottomNavPager and BottomNavigation
    Column {
        BottomNavPager(
            iconsList = bottomList,
            pagerState = pagerState,
            modifier = Modifier.weight(1f)
        )

        // BottomNavigation composable for displaying the bottom navigation items
        BottomNavigation(
            Modifier
                .height(60.dp)
                .shadow(elevation = 10.dp),
            backgroundColor = Color.White
        ) {
            bottomList.forEachIndexed { index, label ->
                BottomNavigationItem(
                    icon = {
                        // Set the icon tint color based on the selected page
                        val tint = if (pagerState.currentPage == index) Color.Blue else Color.Gray
                        IconsBottomBar(index = index, iconsList = bottomList, tint = tint)
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        // Update the pager state when a bottom navigation item is clicked
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun BottomBarPreview(){
    BottomBar()
}