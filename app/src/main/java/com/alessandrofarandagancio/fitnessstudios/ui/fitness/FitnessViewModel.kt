package com.alessandrofarandagancio.fitnessstudios.ui.fitness

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessandrofarandagancio.fitnessstudios.models.ErrorResponse
import com.alessandrofarandagancio.fitnessstudios.models.SearchBusinessResponse
import com.alessandrofarandagancio.fitnessstudios.repository.MainRepository
import com.alessandrofarandagancio.fitnessstudios.ui.utils.Resource
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FitnessViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private var latitude: Double = 33.524155
    private var longitude: Double = -111.905792

    private val _businessResponse = MutableLiveData<Resource<SearchBusinessResponse>>()

    val businessResponse: LiveData<Resource<SearchBusinessResponse>>
        get() = _businessResponse

    val gson = Gson()

    private fun getFitnessList() =
        viewModelScope.launch {
            _businessResponse.postValue(Resource.loading(null))
            mainRepository.getBusinesses(latitude.toString(), longitude.toString(), null).enqueue(object : Callback<SearchBusinessResponse> {
                override fun onResponse(
                    call: Call<SearchBusinessResponse>,
                    response: Response<SearchBusinessResponse>
                ) {
                    if (response.isSuccessful) {
                        _businessResponse.postValue(Resource.success(response.body()))
                    } else {
                        _businessResponse.postValue(
                            Resource.error(
                                gson.fromJson(
                                    response.errorBody()!!.string(),
                                    ErrorResponse::class.java
                                ).error.description,
                                null
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<SearchBusinessResponse>, t: Throwable) {
                    _businessResponse.postValue(Resource.error(t.localizedMessage, null))
                }
            })

        }

    fun setNewLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude

        getFitnessList()
    }

    fun noLocationProvided() {
        TODO("Not yet implemented - noLocationProvided")
    }


}