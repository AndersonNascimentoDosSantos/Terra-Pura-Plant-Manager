package br.com.fiap.terracuraplantmanager.screens

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraScreen(
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    navController: NavController
) {
    // 1
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    // 2
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // 3
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

//            IconButton(
//                modifier = Modifier.padding(bottom = 20.dp),
//                onClick = {
//                    Log.i("kilo", "ON CLICK")
//                    takePhoto(
//                        filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
//                        imageCapture = imageCapture,
//                        outputDirectory = outputDirectory,
//                        executor = executor,
//                        onImageCaptured = onImageCaptured,
//                        onError = onError
//                    )
//                },
//                content = {
//                    Icon(
//                        imageVector = Icons.Sharp.ArrowBack,
//                        contentDescription = "voltar",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(100.dp)
//                            .padding(1.dp)
//                            .border(1.dp, Color.White, CircleShape)
//                    )
//                }
//            )
            IconButton(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .size(100.dp),
                onClick = {
                    Log.i("kilo", "ON CLICK")
                    takePhoto(
                        filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                        imageCapture = imageCapture,
                        outputDirectory = outputDirectory,
                        executor = executor,
                        onImageCaptured = onImageCaptured,
                        onError = onError

                    )
                },
                content = {
                    Icon(
                        imageVector = Icons.Sharp.Lens,
                        contentDescription = "Take picture",
                        tint = Color.White,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(1.dp)
                            .border(1.dp, Color.White, CircleShape)
                            .width(150.dp)
                    )
                }
            )

        }
    }
}

private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.e("kilo", "Take photo error:", exception)
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        }
    })
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

//fun handleImageCapture(uri: Uri) {
//    Log.i("kilo", "Image captured: $uri")
////    shouldShowCamera.value = false
//
//    photoUri = uri
////    shouldShowPhoto.value = true
//
//    // Encode the image to base64
//
//    val file = File(photoUri.path)
//    val imageBytes = FileInputStream(file).readBytes()
//    val imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
//
//    // Create the JSON object
//    val jsonObject = JSONObject()
//    jsonObject.put("images", "[${imageBase64}]")
//    jsonObject.put("latitude", 49.207)
//    jsonObject.put("longitude", 16.608)
//    jsonObject.put("similar_images", true)
//
//    // Send the JSON object to the endpoint
//    val request = Request.Builder()
//        .url("https://plant.id/api/v3/identification?common_names,url,description,taxonomy,rank,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering,propagation_methods&language=br,en")
//        .post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString()))
//        .addHeader("Api-Key", "GJA2ik5Ir2PDmPm3SogNxMp3nt7wcNkQvfEcG3Su46gxeKB3YX")
//        .build()
//
//    val client = OkHttpClient()
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            Log.e("kilo", "Failed to send image: ${e.message}")
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            if (response.isSuccessful) {
//                val jsonResponse = response.body()?.string()
//                if (jsonResponse != null) {
////                        val jsonObject = JSONObject(jsonResponse)
//                    Log.e("response", jsonResponse)
//                    processApiResponse(jsonResponse)
//                } else {
//                    Log.e("kilo", "Empty response body")
//                }
//            } else {
//                Log.e("kilo", "Failed to send image: ${response.code()}")
//            }
//        }
//    })
//}

//private fun getOutputDirectory(): File {
//    val mediaDir = externalMediaDirs.firstOrNull()?.let {
//        File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
//    }
//
//    return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
//}