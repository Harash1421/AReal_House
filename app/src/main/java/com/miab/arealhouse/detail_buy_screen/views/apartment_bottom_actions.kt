package com.miab.arealhouse.detail_buy_screen.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miab.arealhouse.R
import com.miab.arealhouse.home_screen.tab_layout.screens.views.Apartment

@Composable
fun BottomActions(onApply: () -> Unit, apartment: Apartment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OwnerInfo(owner = apartment.owner,
            ownerProperty = apartment.ownerProperty,
            modifier = Modifier.padding(7.dp).weight(0.5f))
        Button(
            onClick = { onApply() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(40.dp)
                .weight(0.5f)
        ) {
            Text(
                text = "Chat",
                style = TextStyle(color = Color.White)
            )
        }
    }
}

@Composable
fun OwnerInfo(owner: String, ownerProperty: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OwnerImage()
        Spacer(modifier = Modifier.width(8.dp))
        OwnerDetails(owner, ownerProperty)
    }
}

@Composable
fun OwnerImage() {
    Image(
        painter = painterResource(R.drawable.image),
        contentDescription = "Owner Image",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}

@Composable
fun OwnerDetails(owner: String, ownerProperty: String) {
    Column {
        Text(
            text = owner,
            style = TextStyle(color = Color.Black, fontSize = 12.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = ownerProperty,
            style = TextStyle(color = Color.Gray, fontSize = 12.sp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ApartmentBottomActionsPreview(){}