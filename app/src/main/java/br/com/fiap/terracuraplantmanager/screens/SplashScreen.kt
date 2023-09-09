package br.com.fiap.terracuraplantmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.terracuraplantmanager.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Neste exemplo, a SplashScreen aguardará por 2 segundos antes de navegar para a próxima tela.
    // Você pode substituir isso por sua própria lógica de inicialização, como carregamento de dados ou autenticação.

    // Este LaunchedEffect será acionado apenas na primeira criação da tela.
    LaunchedEffect(true) {
        // Aguarde por 2 segundos (tempo simulado para SplashScreen)
        delay(2000)

        // Navegue para a próxima tela, por exemplo, a tela de login.
        navController.navigate("welcome")
    }

    // Layout da SplashScreen
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        // Conteúdo da SplashScreen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logotype),
                contentDescription ="Splash logo",
                modifier = Modifier.size(250.dp)
            )
        }
    }
}
