package br.com.fiap.terracuraplantmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.terracuraplantmanager.R

@Composable
fun MyPlants(navController: NavController) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        Row {
        }
        Text(
            text = "Minhas",
            modifier = Modifier
                .width(150.dp)
                .padding(start = 10.dp),
            fontSize = 34.sp,
            color = MaterialTheme.colorScheme.secondary
        )

       Image(
           painter = painterResource(id = R.drawable.profile_pic_1),
           contentDescription ="profile pic",
           modifier = Modifier
               .padding(end = 40.dp)
               .size(80.dp)
               .clip(CircleShape)
               .align(Alignment.TopEnd)
       )

        Text(
            text = "Plantinhas",
            modifier = Modifier
                .padding(top = 45.dp, start = 10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp
        )

    }

    Box(modifier = Modifier
        .offset(50.dp, 220.dp)
        .fillMaxWidth()
    ) {
        Row {
            Box(modifier = Modifier
                .background(
                    color = colorResource(id = R.color.new_blue),
                    shape = RoundedCornerShape(50.dp)
                )
                .size(50.dp)

            ){
                Image(
                    painter = painterResource(id = R.drawable.gota),
                    contentDescription = "gota",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "Regue sua aningapora daqui a 2 horas",
                fontSize = 18.sp,
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 10.dp),
                color = colorResource(id = R.color.teal_700)
            )

        }
    }
    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
        .offset(10.dp, 310.dp)
    ){
        Text(
            text = "Próximas regadas",
            fontSize = 25.sp
        )
    }
}