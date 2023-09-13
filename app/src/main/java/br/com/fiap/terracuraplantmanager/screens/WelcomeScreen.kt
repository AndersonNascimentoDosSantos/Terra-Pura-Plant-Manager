package br.com.fiap.terracuraplantmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.terracuraplantmanager.R

@Composable
fun Welcome(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Gerencie suas plantas de forma facil",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 1.5.em,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(198.dp)
                .padding(top = 50.dp),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row {
            Image(
                painter = painterResource(id = R.drawable.ilustra),
                contentDescription = "ilustra",
                modifier = Modifier.size(300.dp),
            )
            Spacer(modifier = Modifier.width(100.dp))
        }

        Column {

        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Não esqueça mais de regar suas plantas, Nós cuidamos de lembrar você sempre que precisar",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.width(300.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Button(
                onClick = { navController.navigate("confirmName") },
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green_plant))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "arrow",
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(25.dp))
            Button(
                onClick = { navController.navigate("camera") },
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green_plant))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_camera_94),
                    contentDescription = "arrow",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

    }

}