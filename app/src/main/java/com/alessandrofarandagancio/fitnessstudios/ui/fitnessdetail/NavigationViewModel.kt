package com.alessandrofarandagancio.fitnessstudios.ui.fitnessdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessandrofarandagancio.fitnessstudios.models.yelp.ErrorResponse
import com.alessandrofarandagancio.fitnessstudios.repository.DirectionsRepository
import com.alessandrofarandagancio.fitnessstudios.ui.utils.Resource
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(private val directionsRepository: DirectionsRepository) :
    ViewModel() {

    private val _directionsResponsePair = MutableLiveData<Resource<List<String>>>()

    val directionsResponsePair: LiveData<Resource<List<String>>>
        get() = _directionsResponsePair

    val gson = Gson()

    fun getDirections(latlon: LatLng, business: String) =
        viewModelScope.launch {
            directionsRepository.getDirections(
                "${latlon.latitude},${latlon.longitude}",
                business
            ).enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    if (response.isSuccessful) {
                        val jsonObject = response.body()
                        val pointsList: ArrayList<String> = ArrayList()
                        jsonObject?.let {
                            jsonObject.get("routes").asJsonArray[0].asJsonObject
                                .get("legs").asJsonArray[0].asJsonObject
                                .get("steps").asJsonArray.forEach() {
                                    val points = it.asJsonObject.get("polyline").asJsonObject.get("points").asString
                                    pointsList.add(points)
                                }
                        }
                        _directionsResponsePair.postValue(Resource.success(pointsList))
                    } else {
                        val errorResponse = gson.fromJson(
                            response.errorBody().let { response.errorBody().toString() },
                            ErrorResponse::class.java
                        )
                        _directionsResponsePair.postValue(
                            Resource.error(
                                errorResponse.error.description,
                                listOf()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    _directionsResponsePair.postValue(Resource.error(t.localizedMessage, listOf()))
                }
            })

        }

}