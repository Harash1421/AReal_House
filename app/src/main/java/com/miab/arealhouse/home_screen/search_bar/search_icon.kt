package com.miab.arealhouse.home_screen.search_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalStdlibApi
@Composable
fun SearchIcon(){
    Box(
        contentAlignment = Alignment.CenterEnd // aligns the content to the end (right)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search Icon",
            tint = Color.Blue
        )
    }
}

@ExperimentalStdlibApi
@Composable
@Preview
fun SearchIconPreview(){
    SearchIcon()
}