package br.com.fiap.terracuraplantmanager.screens

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import br.com.fiap.terracuraplantmanager.R
import br.com.fiap.terracuraplantmanager.components.CommonNameCard
import br.com.fiap.terracuraplantmanager.components.DescriptionsCard
import br.com.fiap.terracuraplantmanager.components.TaxonomyCard
import br.com.fiap.terracuraplantmanager.mock.JsonUtilsMockData
import br.com.fiap.terracuraplantmanager.model.PlantIdentificationViewModel
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Composable
fun ImageDetailScreen(
    plantId: Int?, viewModel: PlantIdentificationViewModel, navController: NavController
) {



   val plantInfo = viewModel.plantInfo.value
    // val context = LocalContext.current
    // val plantInfo = JsonUtilsMockData.loadJsonFromAsset(context, "mockdata.json")
    // Recupere as informações da planta com base no plantId
    val suggestionImages = mutableListOf<String>()
    plantInfo?.let { info ->
        val suggestions =
            info.getJSONObject("result").getJSONObject("classification").getJSONArray("suggestions")


        val similarImages =
            plantId?.let { suggestions.getJSONObject(it).optJSONArray("similar_images") }

        val commonNames = plantId?.let {
            val details = suggestions.getJSONObject(it).optJSONObject("details")
            val commonNamesPt = details?.optJSONObject("common_names")?.optJSONArray("pt")
            val commonNamesEn = details?.optJSONObject("common_names")?.optJSONArray("en")

            if (commonNamesPt?.length() ?: 0 > 0) {
                commonNamesPt
            } else if (commonNamesEn?.length() ?: 0 > 0) {
                commonNamesEn
            } else {
                null
            }
        } ?: null
        val taxonomy = plantId?.let {
            suggestions.getJSONObject(it).getJSONObject("details").getJSONObject("taxonomy")
        }

        val description = plantId?.let {
            val descriptionObject =
                suggestions.getJSONObject(it).getJSONObject("details").getJSONObject("description")

            descriptionObject.optJSONObject("pt") ?: descriptionObject.optJSONObject("en")

        }



        if (similarImages != null && similarImages.length() > 0) {
            for (i in 0 until similarImages.length()) {

                val imageUrl = similarImages.getJSONObject(i).optString("url")
                if (imageUrl.isNotEmpty()) {
                    suggestionImages.add(imageUrl)
                }
            }
        } else {
            Log.e("imageUrl:", "nao tem imagem similar")
        }

        Column(
            modifier = Modifier
                .fillMaxHeight() // Preenche toda a altura disponível
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.green_plant))
        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // Divide igualmente o espaço vertical disponível
                    .fillMaxWidth() // Preenche toda a largura disponível
                    .padding(top = 10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    ImageSlider(images = suggestionImages)
                }
            }

            Column(
                modifier = Modifier
                    .weight(2f) // Divide igualmente o espaço vertical disponível
                    .fillMaxWidth() // Preenche toda a largura disponível
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .horizontalScroll(rememberScrollState()), // Habilita a rolagem horizontal,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CommonNameCard(commonNames)
                    Spacer(modifier = Modifier.width(5.dp))
                    DescriptionsCard(descriptions = description)
                    Spacer(modifier = Modifier.width(5.dp))
                    TaxonomyCard(taxonomy = taxonomy)
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.4f) // Divide igualmente o espaço vertical disponível
                    .fillMaxWidth() // Preenche toda a largura disponível
//                    .background(Color.Red)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            // Navegue de volta para a tela anterior
                            viewModel.goback(navController = navController)
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(65.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                    ) {
                        Text(
                            text = "Voltar",
                            color = colorResource(id = R.color.green_plant),
                            fontSize = 18.sp, fontWeight = FontWeight.Bold
                        )
                    }

                    // Botão de Adicionar Planta
                    Button(
                        onClick = {
                            // Implemente a lógica para adicionar uma planta aqui
                            // Por exemplo, você pode abrir uma nova tela para adicionar a planta
                        },

                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                        modifier = Modifier
                            .width(200.dp)
                            .height(65.dp)
                    ) {
                        Text(
                            text = "Adicionar Planta",
                            color = colorResource(id = R.color.green_plant),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        }
    }
}


@SuppressLint("RememberReturnType")
@Composable
fun ImageSlider(images: List<String>) {
    val context = LocalContext.current
    val viewPager = remember { ViewPager2(context) }
//    val pagerState = rememberPagerState(pageCount = images.size)

    AndroidView(
        factory = { viewPager },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // Ajuste a altura conforme necessário
    ) { view ->
        view.adapter = ViewPagerAdapter(images = images)
        TabLayout(view.context).apply {
            TabLayoutMediator(this, viewPager) { _, _ -> }.attach()
        }
    }
}

class ViewPagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        imageView.load(images[position]) // Use uma biblioteca de carregamento de imagens aqui
    }

    override fun getItemCount(): Int = images.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
