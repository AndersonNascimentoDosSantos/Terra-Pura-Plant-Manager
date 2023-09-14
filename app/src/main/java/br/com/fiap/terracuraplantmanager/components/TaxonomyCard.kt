package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    if (taxonomy != null) {
                        val classString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append("classe:")
                            }
                            append(" ${taxonomy.optString("class")}")
                        }
                        val genusString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append("genus:")
                            }
                            append(" ${taxonomy.optString("genus")}")
                        }
                        val orderString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append("order:")
                            }
                            append(" ${taxonomy.optString("order")}")
                        }
                        val familyString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append("familia:")
                            }
                            append(" ${taxonomy.optString("family")}")
                        }
                        val phylumString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append("phylum:")
                            }
                            append(" ${taxonomy.optString("phylum")}")
                        }
                        val kingdomString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append("Reino:")
                            }
                            append(" ${taxonomy.optString("kingdom")}")
                        }

                        Text(
                            text = classString,
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = genusString, modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = orderString, modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = familyString,
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = phylumString,
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = kingdomString,
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium,

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

