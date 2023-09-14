package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import org.json.JSONObject

@Composable
fun TaxonomyCard(

    taxonomy: JSONObject? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
            .padding(5.dp),
        shape = RoundedCornerShape(32.dp), // Defina o raio do canto para torná-lo arredondado
//        elevation = 4.dp // Ajuste o valor da elevação para controlar a sombra
    ) {

        val hasTaxonomy = taxonomy != null
        Column(
            modifier = Modifier
                .fillMaxSize()
                .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
                .padding(5.dp)
//                    .blur()
        ) {

            // Pelo menos um dos itens não é nulo ou vazio, então exibimos o card

            // Renderize aqui os itens comuns a todos


            if (hasTaxonomy) {
                // Renderize taxonomia
                Text(
                    text = "Taxonomia:", modifier = Modifier,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
//                horizontalArrangement = Arrangement.Start
                ) {

                    if (taxonomy != null) {
                        Text(
                            text = "classe: ${taxonomy.optString("class")}",
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "genus: ${taxonomy.optString("genus")}", modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "order: ${taxonomy.optString("order")}", modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "familia: ${taxonomy.optString("family")}",
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "phylum: ${taxonomy.optString("phylum")}",
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Reino: ${taxonomy.optString("kingdom")}",
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                }
            } else {
                // Renderize a mensagem "Não Possui Nomes Comuns"
                Text(
                    text = "Não Possui Taxonomia",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

