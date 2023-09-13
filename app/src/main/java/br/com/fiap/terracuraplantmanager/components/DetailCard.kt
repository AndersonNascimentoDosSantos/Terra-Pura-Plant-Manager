package br.com.fiap.terracuraplantmanager.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import org.json.JSONArray

@Composable
fun DetailCard(commonName: JSONArray? = null) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .width( (LocalConfiguration.current.screenWidthDp * 0.95).dp)
//            .fillMaxWidth()// Preenche toda a largura disponível
//            .background(Color.Red)
            .padding(5.dp)

    ) {
        // Preencha esta coluna com o conteúdo desejado
        Text(text = "nomes Comuns: ")

        commonName?.let { array ->
            val names = mutableListOf<String>()

            for (i in 0 until array.length()) {
                val name = array.getString(i)
                names.add(name)
            }

            val chunkedNames = names.chunked(2)

            chunkedNames.forEach { rowNames ->
                Spacer(modifier = Modifier.width(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
//                    rowNames.forEach { name ->
                        Text(text = rowNames.joinToString(","))
                        Spacer(modifier = Modifier.width(5.dp))
//                    }
                }
            }
        }

    }
}