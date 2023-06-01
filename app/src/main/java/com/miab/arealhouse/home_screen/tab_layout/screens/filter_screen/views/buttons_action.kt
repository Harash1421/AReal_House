package com.miab.arealhouse.home_screen.tab_layout.screens.filter_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun FilterActions(onReset: () -> Unit, onApply: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(12.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = { onReset() },
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .padding(10.dp)
        ) {
            Text(text = "Reset",
                modifier = Modifier.wrapContentSize(Alignment.CenterStart),
                style = TextStyle(color = Color.Blue)
            )
        }

        Button(
            onClick = { onApply() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
                .padding(7.dp)
        ) {
            Text(text = "Apply",
                modifier = Modifier.wrapContentSize(Alignment.Center),
                style = TextStyle(color = Color.White)
            )
        }
    }
}