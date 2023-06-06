package com.example.veterinaria.screens.img

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.veterinaria.R

@Composable
fun AbrirGaleria(
    openGallery:()->Unit
) {
    Button(onClick = openGallery) {
        Text(text = "  Add image ",
            fontSize = 17.sp
        )
        Icon(
            painter = painterResource(R.drawable.ic_whatsapp_background),
            contentDescription = "Downloads"
        )
    }
}