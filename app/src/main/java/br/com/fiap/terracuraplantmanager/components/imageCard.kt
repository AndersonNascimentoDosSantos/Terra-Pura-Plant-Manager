package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun ImageCard(photoUrl: String, name: String, id: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable {
                navController.navigate("imageDetail/$id")
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberImagePainter(photoUrl),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(text = name, textAlign = TextAlign.Center)
    }
}