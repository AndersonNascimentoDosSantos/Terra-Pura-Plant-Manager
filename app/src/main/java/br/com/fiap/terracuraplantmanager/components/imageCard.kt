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
import br.com.fiap.terracuraplantmanager.data.PlantInfo
import coil.compose.rememberImagePainter

@Composable
fun ImageCard(plantInfo: PlantInfo, navController: NavController) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable {
                navController.navigate("imageDetail/${plantInfo.index}")
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberImagePainter(plantInfo.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(text = plantInfo.name, textAlign = TextAlign.Center)
        val formattedPercentage = String.format("probabilidade: %.2f%%", plantInfo.probability.toFloat() * 100)

        Text(text = formattedPercentage, textAlign = TextAlign.Center)
    }
}