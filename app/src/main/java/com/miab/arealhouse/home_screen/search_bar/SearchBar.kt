package com.miab.arealhouse.home_screen.search_bar

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalStdlibApi
@Composable
fun SearchBar(context: Context,
              modifier: Modifier = Modifier,
              onSearch: (String) -> Unit,
              onMapClick: () -> Unit,
              isMap:Boolean = false) {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .padding(top = 7.dp, start = 12.dp, end = 12.dp)
            .size(54.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
        ) {
            Surface(
                color = Color.White,
                elevation = 8.dp,
                modifier = Modifier
                    .weight(0.7f)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(12.dp))
                    SearchIcon()
                    SearchTextField(hint = "Search", onSearch = onSearch)
                }
            }

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            MapTextButton(modifier = Modifier.weight(0.2f),
                buttonText = if(isMap) "List" else "Map",
                onClick = {
                    onMapClick()
                })
        }
    }
}

@ExperimentalStdlibApi
@Composable
@Preview
fun SearchBarPreview(){
//    SearchBar(modifier = Modifier.fillMaxWidth())
}