package br.com.fiap.terracuraplantmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.terracuraplantmanager.R

@Preview(showSystemUi = true)
@Composable
fun ConfirmName(){
    val name = remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()

    )
    {
        Image(
            painter = painterResource(id = R.drawable.emoji),
            contentDescription = "emoji",
            modifier = Modifier.size(50.dp)
        )

        Text(
            text = "Como podemos chamar você ?",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .width(200.dp)
        )

        OutlinedTextField(
            value = name.value,
            onValueChange ={name.value = it},
            placeholder = { Text(text = "Digite seu nome")},
        )

        Button(onClick = { /*TODO*/ },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(50.dp)
                .width(220.dp)
        ) {

           Text(text = "Confirmar")
        }
    }

}