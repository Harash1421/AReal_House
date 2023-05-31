package com.miab.arealhouse.home_screen.search_bar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalStdlibApi
@Composable
fun SearchBar(modifier: Modifier = Modifier, ) {
    Column(
        modifier = modifier
            .padding(top = 7.dp, start = 12.dp, end = 12.dp)
            .size(48.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                elevation = 8.dp,
                modifier = Modifier
                    .weight(0.7f)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(12.dp))
                    SearchIcon()
                    SearchTextField(hint = "Search")
                }
            }

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            MapTextButton(modifier = Modifier.weight(0.2f), buttonText = "Map")
        }
    }
}

@ExperimentalStdlibApi
@Composable
@Preview
fun SearchBarPreview(){
    SearchBar(modifier = Modifier.fillMaxWidth())
}