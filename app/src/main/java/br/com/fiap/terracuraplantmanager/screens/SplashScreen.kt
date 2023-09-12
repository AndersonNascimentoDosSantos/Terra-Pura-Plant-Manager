package br.com.fiap.terracuraplantmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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
        val navOptions = NavOptions.Builder()
            .setPopUpTo("welcome", inclusive = true)
            .build()
        // Navegue para a próxima tela, por exemplo, a tela de login.
        navController.navigate("welcome", navOptions)
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
