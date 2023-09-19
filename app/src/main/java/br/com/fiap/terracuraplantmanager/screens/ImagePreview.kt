package br.com.fiap.terracuraplantmanager.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.terracuraplantmanager.R
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePreviewScreen(
    navController: NavController,
    photoUri: Uri,
    onRecognizePlantClick: (NavController, Uri) -> Unit,
    onBackClick: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = rememberImagePainter(photoUri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale *= zoom
                        offset = if (scale > 1f) {
                            Offset(
                                x = (offset.x * zoom + pan.x * 2).coerceIn(
                                    -maxOffsetX(),
                                    maxOffsetX()
                                ),
                                y = (offset.y * zoom + pan.y * 2).coerceIn(
                                    -maxOffsetY(),
                                    maxOffsetY()
                                )
                            )
                        } else {
                            Offset(0f, 0f)
                        }
                    }
                }
                .graphicsLayer(
                    scaleX = maxOf(1f, minOf(3f, scale)),
                    scaleY = maxOf(1f, minOf(3f, scale)),
                    translationX = offset.x,
                    translationY = offset.y
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = { onBackClick() },
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green_plant))
            ) {
                Text(text = "Voltar")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onRecognizePlantClick(navController, photoUri) },
                modifier = Modifier
                    .padding(8.dp)
                    .width(250.dp)
                    .height(65.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green_plant))
            ) {
                Text(
                    text = "Reconhecer Planta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold

                )
            }
        }
    }
}

private fun maxOffsetX() = 0f
private fun maxOffsetY() = 0f
