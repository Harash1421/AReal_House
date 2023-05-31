package com.miab.arealhouse.home_screen.tab_layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miab.arealhouse.R



@Composable
fun FilterIcon(modifier: Modifier = Modifier, onFilterClick: () -> Unit){
    Image(
        painter = painterResource(id = R.drawable.filter),
        contentDescription = null,
        modifier = modifier
            .width(47.dp)
            .height(47.dp)
            .clickable { onFilterClick() }
    )
}


//.weight(0.1f)
//.align(Alignment.CenterVertically)

@Preview(showBackground = true)
@Composable
fun FilterIconPreview(){
    FilterIcon() {}
}