package com.miab.arealhouse.home_screen.search_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalStdlibApi
@Composable
fun SearchTextField(hint: String = "", onSearch: (String) -> Unit = { }){
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onSearch(newText)
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.titleSmall.copy(color = Color.Black),
        placeholder = { Text(text = hint, style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray)) }, // added placeholder
        modifier = Modifier.padding(start = 4.dp), // reduced padding
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@ExperimentalStdlibApi
@Composable
@Preview
fun SearchTextFieldPreview(){
    SearchTextField(hint = "Search")
}