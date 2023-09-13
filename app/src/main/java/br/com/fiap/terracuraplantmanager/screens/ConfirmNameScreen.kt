package br.com.fiap.terracuraplantmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.terracuraplantmanager.R

@Composable
fun ConfirmName(navController: NavController){
    val name = remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .offset(0.dp,130.dp)

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

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange ={ name.value = it },
        )

        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { navController.navigate("myPlants") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(50.dp)
                .width(220.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green_plant))
        ) {

           Text(text = "Confirmar")
        }
    }

}