package br.com.fiap.terracuraplantmanager.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class PlantIdentificationViewModel : ViewModel() {
    private val _plantInfo = MutableLiveData<JSONObject?>()
    val plantInfo: LiveData<JSONObject?> = _plantInfo

    private val _cardViewer = MutableLiveData<JSONObject?>()
    val cardViewer: LiveData<JSONObject?> = _cardViewer

    val suggestionImages = mutableListOf<String>()
    fun setSugestionsImages(url:String){
        suggestionImages.add(url)
    }
    fun updatePlantInfo(info: JSONObject?) {
        viewModelScope.launch(Dispatchers.Main) {
            _plantInfo.value = info
        }
    }

    fun updateCardViewer(info: JSONObject?) {
        viewModelScope.launch(Dispatchers.Main) {
            _cardViewer.value = info
        }
    }

    fun goback(navController: NavController) {
        viewModelScope.launch {
            navController.popBackStack()
        }
    }

    fun navigateTo(to: String, navController: NavController) {
        viewModelScope.launch {
            navController.navigate(to)
        }
    }
}