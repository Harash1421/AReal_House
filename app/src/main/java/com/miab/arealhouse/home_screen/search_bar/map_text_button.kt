package com.miab.arealhouse.home_screen.search_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalStdlibApi
@Composable
fun MapTextButton(
    modifier: Modifier = Modifier,
    buttonText:String,
    onClick: () -> Unit = { }){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
    ) {
        Text(text = buttonText, color = Color.Blue)
    }
}

@ExperimentalStdlibApi
@Composable
@Preview
fun MapTextButtonPreview(){
    MapTextButton(buttonText = "Map")
}
