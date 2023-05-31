package com.miab.arealhouse.home_screen.tab_layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TabRow(tabNames: List<String>,
           onTabSelected: (Int) -> Unit,
           modifier: Modifier = Modifier,
           pagerState: PagerState){

    val coroutineScope = rememberCoroutineScope()

    androidx.compose.material.TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                color = Color.Blue
            )
        },
        divider = { }
    ) {
        tabNames.forEachIndexed { index, name ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    onTabSelected(index)
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}