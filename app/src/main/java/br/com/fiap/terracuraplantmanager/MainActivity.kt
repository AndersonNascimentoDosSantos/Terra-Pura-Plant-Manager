package br.com.fiap.terracuraplantmanager

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.terracuraplantmanager.model.PlantIdentificationViewModel
import br.com.fiap.terracuraplantmanager.screens.CameraScreen
import br.com.fiap.terracuraplantmanager.screens.ConfirmName
import br.com.fiap.terracuraplantmanager.screens.ImagePreviewScreen
import br.com.fiap.terracuraplantmanager.screens.MyPlants
import br.com.fiap.terracuraplantmanager.screens.PlantInfoScreen
import br.com.fiap.terracuraplantmanager.screens.SplashScreen
import br.com.fiap.terracuraplantmanager.screens.Welcome
import com.google.android.gms.location.LocationServices
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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private val viewModel: PlantIdentificationViewModel by viewModels()
//    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
//    private var shouldShowPlantInfo: MutableState<Boolean> = mutableStateOf(false)
    private var plantInfo: MutableState<JSONObject> = mutableStateOf(JSONObject("{}"))
    private lateinit var photoUri: Uri
    private val REQUEST_LOCATION_PERMISSION = 123
//    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    private lateinit var navController: NavController
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("kilo", "Permission granted")
            navController.navigate("camera")
        } else {
            Log.i("kilo", "Permission denied")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            requestCameraPermission()
            outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()

            NavHost(navController as NavHostController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(navController)
                }
                composable("welcome"){
                    Welcome(navController = navController)

                }
                composable("confirmName") {
                    ConfirmName(navController = navController)
                }
                composable("myPlants"){
                    MyPlants(navController = navController)
                }
                composable("camera") {
                    CameraScreen(
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor,
                        onImageCaptured = { uri -> handleImageCapture( navController,uri) },
                        onError = { Log.e("kilo", "View error:", it) },
                        navController=navController

                        )
                }
                composable("imagePreview") {
                    ImagePreviewScreen(
                        navController =navController,
                        photoUri = photoUri,
                        onRecognizePlantClick = ::handleRecognizePlantClick
                    ) { navController.navigate("camera") }
                }
                    //                composable("photo/{photoUri}") { backStackEntry ->
                    //                    val arguments = requireNotNull(backStackEntry.arguments)
                    //                    val photoUri = Uri.parse(arguments.getString("photoUri")!!)
                    //                    PhotoScreen(photoUri)
                    //                }
                composable("plantInfo") {
                    //PlantIdentificationScreen(viewModel)
                    PlantInfoScreen(viewModel)
                }
            }
        }

    }

    private fun handleImageCapture(navController: NavController, uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        photoUri = uri
        runOnUiThread {
            navController.navigate("imagePreview")
        }
    }
    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun handleRecognizePlantClick(navController: NavController, photoUri: Uri) 
    {
        Log.i("kilo", "Image captured: $photoUri")

        // Encode the image to base64
        val file = photoUri.path?.let { File(it) }
        val imageBytes = FileInputStream(file).readBytes()
        val imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        val jsonObject = JSONObject()
        // Verificar se a permissão de localização foi concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Configurar o cliente de localização
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            // Solicitar a localização do usuário
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Verificar se a localização foi obtida com sucesso
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude

                        // Agora você pode usar as coordenadas de latitude e longitude no seu JSON

                        jsonObject.put("images", "[${imageBase64}]")
                        jsonObject.put("latitude", latitude)
                        jsonObject.put("longitude", longitude)
                        jsonObject.put("similar_images", true)
                        // Send the JSON object to the endpoint
                        val request = Request.Builder()
                            .url("https://plant.id/api/v3/identification?common_names,url,description,taxonomy,rank,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering,propagation_methods&language=pt")
                            .post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString()))
                            .addHeader("Api-Key", "GJA2ik5Ir2PDmPm3SogNxMp3nt7wcNkQvfEcG3Su46gxeKB3YX")
                            .build()

                        val client = OkHttpClient()
                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                Log.e("kilo", "Failed to send image: ${e.message}")
                            }

                            override fun onResponse(call: Call, response: Response) {
                                if (response.isSuccessful) {
                                    val jsonResponse = response.body()?.string()
                                    if (jsonResponse != null) {
                                        //val jsonObject = JSONObject(jsonResponse)
                                        Log.e("response", jsonResponse)
                                        this@MainActivity.processApiResponse(jsonResponse,navController)
                                    } else {
                                        Log.e("kilo", "Empty response body")
                                    }
                                } else {
                                    Log.e("kilo", "Failed to send image: ${response.code()}")
                                }
                            }
                        })
                        // O restante do seu código para enviar a solicitação à API
                    } else {
                        // Não foi possível obter a localização
                    }
                }
                .addOnFailureListener { e ->
                    // Tratar erros na obtenção da localização
                    Log.e("kilo", "Failed to get location: ${e.message}")
                }
        } else {
            // Se a permissão de localização não foi concedida, solicite-a ao usuário
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }


    }
    private fun processApiResponse(jsonResponse: String,navController: NavController) {
        val jsonObject = JSONObject(jsonResponse)

        viewModel.updatePlantInfo(jsonObject)
        // Atualize o estado com as informações do JSON (substitua as chaves pelos valores reais)
        plantInfo.value = jsonObject

        // Atualize o estado para exibir as informações na tela
        runOnUiThread {
            navController.navigate("plantInfo")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
