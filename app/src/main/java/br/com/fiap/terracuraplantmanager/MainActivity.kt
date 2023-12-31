package br.com.fiap.terracuraplantmanager

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
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
import br.com.fiap.terracuraplantmanager.model.DataMainipulation
import br.com.fiap.terracuraplantmanager.model.PlantIdentificationViewModel
import br.com.fiap.terracuraplantmanager.screens.CameraScreen
import br.com.fiap.terracuraplantmanager.screens.ConfirmName
import br.com.fiap.terracuraplantmanager.screens.ImageDetailScreen
import br.com.fiap.terracuraplantmanager.screens.ImagePreviewScreen
import br.com.fiap.terracuraplantmanager.screens.MyPlants
import br.com.fiap.terracuraplantmanager.screens.PlantInfoScreen
import br.com.fiap.terracuraplantmanager.screens.SplashScreen
import br.com.fiap.terracuraplantmanager.screens.Welcome
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private val viewModel: PlantIdentificationViewModel by viewModels()
    private var plantInfo: MutableState<JSONObject> = mutableStateOf(JSONObject("{}"))
    private lateinit var photoUri: Uri
    private val REQUEST_LOCATION_PERMISSION = 123
    private val STORAGE_PERMISSION_REQUEST_CODE = 123
    private lateinit var navController: NavController
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("kilo", "Permission granted")
            navController.navigate("camera")
        } else {
            showMessage("Permissão negada. Você pode conceder a permissão nas configurações do aplicativo.")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            requestCameraPermission()
            requestStoragePermission()
            outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()

            NavHost(navController as NavHostController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(navController)
                }
                composable("welcome") {
                    Welcome(navController = navController)

                }
                composable("confirmName") {
                    ConfirmName(navController = navController)
                }
                composable("myPlants") {
                    MyPlants(navController = navController)
                }
                composable("camera") {
                    CameraScreen(
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor,
                        onImageCaptured = { uri -> handleImageCapture(navController, uri) },
                        onError = { Log.e("kilo", "View error:", it) },
                    )
                }
                composable("imagePreview") {
                    ImagePreviewScreen(
                        navController = navController,
                        photoUri = photoUri,
                        onRecognizePlantClick = ::handleRecognizePlantClick
                    ) { navController.navigate("camera") }
                }

                composable("plantInfo") {

                    PlantInfoScreen(viewModel, navController)
                }
                composable("imageDetail/{plantId}") { navBackStackEntry ->
                    val plantId = navBackStackEntry.arguments?.getString("plantId")
                    if (plantId != null) {
                        ImageDetailScreen(plantId.toInt(), viewModel, navController)
                    }
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

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    private fun showMessage(message: String) {
        Snackbar.make(findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .show()
    }


    private fun handleRecognizePlantClick(navController: NavController, photoUri: Uri) {
        // Verificar se a permissão de localização foi concedida
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Configurar o cliente de localização
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            // Solicitar a localização do usuário
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Verificar se a localização foi obtida com sucesso
                    if (location != null) {
//                        viewModel.navigateTo("plantInfo")
                        val getData = DataMainipulation();
                        getData.getDataFromApi(
                            photoUri,
                            location,
                            navController,
                            viewModel
                        )
//                        runOnUiThread {
//                            navController.navigate("plantInfo")
//                        }

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
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
