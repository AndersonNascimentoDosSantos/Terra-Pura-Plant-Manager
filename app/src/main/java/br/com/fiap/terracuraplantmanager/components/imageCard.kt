package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ImageCard(photoUrl: String, name: String,
//              onPlantClick: (id: String) -> Unit,
              id: String
) {
    Column(
//        modifier = Modifier.clickable { onPlantClick(id) }
    ) {
        Image(
            painter = rememberImagePainter(photoUrl),
            contentDescription = null,
            modifier = Modifier
                .size(220.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(text = name, textAlign = TextAlign.Center)
    }


}


//@Composable
//fun PlantList(suggestions: List<Suggestion>, onPlantClick: (Suggestion) -> Unit) {
//    LazyRow {
//        items(suggestions) { suggestion ->
//
//        }
//    }
//}
