package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
    val greenPlantColor = colorResource(id = R.color.green_plant)

// Defina o valor alpha (transparência)
    val alpha = 0.5f // 0.0f para completamente transparente, 1.0f para completamente opaco
    val colorWithAlpha = greenPlantColor.copy(alpha = alpha)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(
                BorderStroke(1.dp, colorWithAlpha),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                navController.navigate("imageDetail/${plantInfo.index}")
            }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = colorResource(id = R.color.green_plant)
                // Defina as outras propriedades da sombra, como a cor
                // e o deslocamento (offset) aqui.
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(
                    BorderStroke(1.dp, Color.Transparent),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {


            Image(
                painter = rememberImagePainter(plantInfo.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(5.dp)
            )

            Spacer(modifier = Modifier.width(25.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    , verticalArrangement = Arrangement.SpaceBetween
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
}