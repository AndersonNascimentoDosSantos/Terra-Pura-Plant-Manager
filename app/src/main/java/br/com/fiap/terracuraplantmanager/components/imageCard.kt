package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.terracuraplantmanager.R
import br.com.fiap.terracuraplantmanager.data.PlantInfo
import coil.compose.rememberImagePainter

@Composable
fun ImageCard(plantInfo: PlantInfo, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("imageDetail/${plantInfo.index}")
            },
    ) {
        Image(
            painter = rememberImagePainter(plantInfo.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(BorderStroke(5.dp, colorResource(id = R.color.green_plant)))
        )

        Spacer(modifier = Modifier.width(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = plantInfo.name,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.green_plant),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            val formattedPercentage =
                String.format("Chance: %.1f%%", plantInfo.probability.toFloat() * 100)

            Spacer(modifier = Modifier.height(80.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formattedPercentage,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

        }

    }
}