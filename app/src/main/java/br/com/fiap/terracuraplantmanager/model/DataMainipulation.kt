package br.com.fiap.terracuraplantmanager.model


import android.location.Location
import android.net.Uri
import android.util.Base64
import android.util.Log
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

//import androidx.activity.


class DataMainipulation {

    private fun getImageBase64(photoUri: Uri): String? {
        val file = photoUri.path?.let { File(it) }
        val imageBytes = FileInputStream(file).readBytes()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun getDataFromApi(
        photoUri: Uri,
        location: Location,
        navController: NavController,
        viewModel: PlantIdentificationViewModel
    ) {
        val jsonObject = JSONObject()
        val latitude = location.latitude
        val longitude = location.longitude

        // Agora você pode usar as coordenadas de latitude e longitude no seu JSON

        jsonObject.put("images", "[${getImageBase64(photoUri)}]")
        jsonObject.put("latitude", latitude)
        jsonObject.put("longitude", longitude)
        jsonObject.put("similar_images", true)
        val request = Request.Builder()
            .url("https://plant.id/api/v3/identification?details=common_names,url,description,taxonomy,rank,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering,propagation_methods&language=pt,en")
            .post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString()))
            .addHeader("Api-Key", "1f2W94Nswj35wC8YXUAPNL7Fmi8cBFXbszk6JVyVt7F6GLeVH3")
            //.addHeader("Api-Key", "GJA2ik5Ir2PDmPm3SogNxMp3nt7wcNkQvfEcG3Su46gxeKB3YX")
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
                        val jsonObject = JSONObject(jsonResponse)
                        Log.e("response", jsonResponse)
                        viewModel.updatePlantInfo(jsonObject)
                        viewModel.navigateTo(to = "plantInfo", navController = navController)

                    } else {
                        Log.e("kilo", "Empty response body")
                    }
                } else {
                    Log.e("kilo", "Failed to send image: ${response.code()}")
                }
            }
        })


    }


    fun extractData(viewModel: PlantIdentificationViewModel) {
        val plantInfo = viewModel.plantInfo.value

        val cardViewer  = JSONObject()
        val interImages = mutableListOf<String>()

        plantInfo?.let { info ->
            val suggestions = info.getJSONObject("result").getJSONObject("classification")
                .getJSONArray("suggestions")
            for (i in 0 until suggestions.length()) {
                val suggestion = suggestions.getJSONObject(i)
                val similarImages = suggestion.optJSONArray("similar_images")
                cardViewer.put("name", suggestion.optString("name"))

                if (similarImages != null && similarImages.length() > 0) {
                    val imageUrl = similarImages.getJSONObject(0).optString("url")
                    if (imageUrl.isNotEmpty()) {
//                        viewModel.setSugestionsImages(imageUrl)
                        cardViewer.put("image", imageUrl)


                    }
                    for (i in 0 until similarImages.length()) {
                        interImages.add(imageUrl)

                    }


                } else {
                    Log.e("imageUrl:", "nao tem imagem similar")
                }

            }


            viewModel.updateCardViewer(cardViewer)

//            jsonObject.put("images",)
        }
    }
    
    fun getDetails(accessToken: String): Response {
        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType: MediaType? = MediaType.parse("application/json")
        val body = RequestBody.create(mediaType, "")
        val request: Request = Request.Builder()
            .url("https://plant.id/api/v3/identification/${accessToken}?details=common_names,url,description,taxonomy,rank,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering &language=pt")
            .method("GET", body)
            .addHeader("Api-Key", "your_api_key")
            .addHeader("Content-Type", "application/json")
            .build()
        return client.newCall(request).execute()
    }
    
}