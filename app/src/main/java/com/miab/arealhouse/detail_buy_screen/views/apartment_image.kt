package com.miab.arealhouse.detail_buy_screen.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ApartmentImage(imageUrl: Int) {
    Image(
        painter = painterResource(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(275.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Preview(showBackground = true)
@Composable
fun ApartmentImagePreview(){ }