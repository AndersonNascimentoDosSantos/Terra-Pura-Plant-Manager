package br.com.fiap.terracuraplantmanager.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.terracuraplantmanager.components.ImageCard
import br.com.fiap.terracuraplantmanager.data.PlantInfo
import br.com.fiap.terracuraplantmanager.model.PlantIdentificationViewModel


@Composable
fun PlantInfoScreen(viewModel: PlantIdentificationViewModel,navController: NavController) {
    val plantInfo = viewModel.plantInfo.value
//    val context = LocalContext.current
//    val plantInfo = JsonUtilsMockData.loadJsonFromAsset(context, "mockdata.json")



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        plantInfo?.let { info ->
//            val imageUri = info.optString("image")

            val suggestions = info.getJSONObject("result").getJSONObject("classification").getJSONArray("suggestions")
            val suggestionImages = mutableListOf<PlantInfo>()

            for (i in 0 until suggestions.length()) {
                val suggestion = suggestions.getJSONObject(i)
                val similarImages = suggestion.optJSONArray("similar_images")
                val name = suggestion.optString("name")
                val probability = suggestion.optString("probability")
                if (similarImages != null && similarImages.length() > 0) {
                    val imageUrl = similarImages.getJSONObject(0).optString("url")
//                        val name = similarImages.getJSONObject(0).optString("name")
//                        val id = similarImages.getJSONObject(0).optString("id")
                    if (imageUrl.isNotEmpty()) {
//                            Log.e("if imageurl", "item no imageUrl")
//                            Log.e("index:", "valor do index:$i")
                        suggestionImages.add(PlantInfo(imageUrl, name,i,probability))
                    }
                }else{
                    Log.e("imageUrl:","nao tem imagem similar")
                }
            }

            if (suggestionImages.isNotEmpty()) {
//                ImageSlider(images = suggestionImages)
                val chunkedImages = suggestionImages.chunked(2)
                chunkedImages.forEach { rowImages ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowImages.forEach { plantInfo ->
                            ImageCard( plantInfo = plantInfo, navController = navController)
                        }
                    }
                }


            }



        } ?: run {
            Text(text = "Aguardando informações...")
        }

    }

}


