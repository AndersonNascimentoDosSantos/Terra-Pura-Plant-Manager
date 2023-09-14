package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.json.JSONArray

@Composable
fun CommonNameCard(
    commonName: JSONArray? = null,

    ) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
            .padding(5.dp),
        shape = RoundedCornerShape(32.dp), // Defina o raio do canto para torná-lo arredondado
//        elevation = 4.dp // Ajuste o valor da elevação para controlar a sombra
    ) {
        val hasCommonNames = commonName?.length() ?: 0 > 0

        Column(
            modifier = Modifier
                .fillMaxSize()
                .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
                .padding(5.dp), verticalArrangement = Arrangement.Center
//                    .blur()
        ) {

            // Renderize aqui os itens comuns a todos
            if (hasCommonNames) {
                // Renderize nomes comuns
                val names = mutableListOf<String>()
                Text(
                    text = "Nomes comuns: ", modifier = Modifier,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                if (commonName != null) {
                    for (i in 0 until commonName.length()) {
                        val name = commonName.getString(i)
                        names.add(name)
                    }
                }

                val chunkedNames = names.chunked(2)

                chunkedNames.forEach { rowNames ->
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        .fillMaxSize()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
//                    rowNames.forEach { name ->
                        Text(
                            text = rowNames.joinToString(","), modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
//                    }
                    }
                }
            } else {
                // Renderize a mensagem "Não Possui Nomes Comuns"
                Text(
                    text = "Não Possui Nomes Comuns registrados",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }


//        Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background) // Pode personalizar a cor de fundo aqui
//            .padding(16.dp)
////            .elevation(4.dp)
//
//    ) {
//
//
//
//
//
//        /*
//        commonName?.let { array ->
//            val names = mutableListOf<String>()
//            Text(
//                text = "Nomes comuns: ", modifier = Modifier,
//                style = MaterialTheme.typography.headlineLarge
//            )
//
//            for (i in 0 until array.length()) {
//                val name = array.getString(i)
//                names.add(name)
//            }
//
//            val chunkedNames = names.chunked(2)
//
//            chunkedNames.forEach { rowNames ->
//                Spacer(modifier = Modifier.width(5.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
////                        .fillMaxSize()
//                        .padding(10.dp),
//                    horizontalArrangement = Arrangement.Start
//                ) {
////                    rowNames.forEach { name ->
//                    Text(
//                        text = rowNames.joinToString(","), modifier = Modifier,
//                        style = MaterialTheme.typography.headlineMedium
//                    )
//                    Spacer(modifier = Modifier.width(5.dp))
////                    }
//                }
//            }
//        } */
//        /*descriptions?.let { description ->
//            Text(
//                text = "Descrição:", modifier = Modifier,
//                style = MaterialTheme.typography.headlineLarge
//            )
//            Spacer(modifier = Modifier.width(5.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp)
//                .verticalScroll(rememberScrollState()),
//                horizontalArrangement = Arrangement.Start
//            ) {
//
//                Text(
//                    text = description.optString("value"), modifier = Modifier,
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//
//            }
//        }*/
//        taxonomy?.let { taxo ->
//            Text(
//                text = "Taxonomia:", modifier = Modifier,
//                style = MaterialTheme.typography.headlineLarge
//            )
//            Spacer(modifier = Modifier.width(5.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp),
////                horizontalArrangement = Arrangement.Start
//            ) {
//
//                Text(
//                    text = "classe: ${taxo.optString("class")}", modifier = Modifier,
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//                Text(
//                    text = "genus: ${taxo.optString("genus")}", modifier = Modifier,
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//                Text(
//                    text = "order: ${taxo.optString("order")}", modifier = Modifier,
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//                Text(
//                    text = "familia: ${taxo.optString("family")}", modifier = Modifier,
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//                Text(
//                    text = "phylum: ${taxo.optString("phylum")}", modifier = Modifier,
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//                Text(
//                    text = "Reino: ${taxo.optString("kingdom")}", modifier = Modifier,
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//            }
//        }
//    }}
}

//?:Text(
//text = "Não Possue Nomes comuns: ", modifier = Modifier,
//style = MaterialTheme.typography.headlineLarge
//)