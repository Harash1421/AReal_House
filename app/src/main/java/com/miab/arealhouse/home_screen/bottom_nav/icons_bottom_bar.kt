package com.miab.arealhouse.home_screen.bottom_nav

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IconsBottomBar(index: Int, iconsList: List<Pair<Int, String>>, tint: Color){
    when(index) {
        0 -> Image(painter = painterResource(id = iconsList[0].first), contentDescription = iconsList[0].second, colorFilter = ColorFilter.tint(tint))
        1 -> Image(painter = painterResource(id = iconsList[1].first), contentDescription = iconsList[1].second, colorFilter = ColorFilter.tint(tint))
        2 -> Image(painter = painterResource(id = iconsList[2].first), contentDescription = iconsList[2].second, colorFilter = ColorFilter.tint(tint))
        else -> Image(painter = painterResource(id = iconsList[3].first), contentDescription = iconsList[3].second, colorFilter = ColorFilter.tint(tint))
    }
}