package br.com.fiap.terracuraplantmanager.mock

import android.content.Context
import org.json.JSONObject
import java.io.IOException

class JsonUtilsMockData {
    companion object {
        fun loadJsonFromAsset(context: Context, fileName: String): JSONObject? {
            var json: String? = null
            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charsets.UTF_8)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return JSONObject(json)
        }
    }
}
