package com.alessandrofarandagancio.fitnessstudios.ui.fitness

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.alessandrofarandagancio.fitnessstudios.constant.defLatitude
import com.alessandrofarandagancio.fitnessstudios.constant.defLongitude
import com.alessandrofarandagancio.fitnessstudios.models.yelp.Business
import com.alessandrofarandagancio.fitnessstudios.models.yelp.ErrorResponse
import com.alessandrofarandagancio.fitnessstudios.models.yelp.SearchBusinessResponse
import com.alessandrofarandagancio.fitnessstudios.repository.AppSettingsRepository
import com.alessandrofarandagancio.fitnessstudios.repository.BusinessesRepository
import com.alessandrofarandagancio.fitnessstudios.ui.utils.Resource
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FitnessViewModel @Inject constructor(
    private val businessesRepository: BusinessesRepository,
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private var businessList: ArrayList<Business> = ArrayList()
    private var latitude: Double = defLatitude
    private var longitude: Double = defLongitude

    private val _businessResponse = MutableLiveData<Resource<SearchBusinessResponse>>()

    val businessResponse: LiveData<Resource<SearchBusinessResponse>>
        get() = _businessResponse

    private val _locationUpdate = MutableLiveData<LatLng>()

    val locationUpdate: LiveData<LatLng>
        get() = _locationUpdate

    val gson = Gson()

    private fun getFitnessList() = viewModelScope.launch {
        businessesRepository.getBusinesses(latitude.toString(), longitude.toString(), null)
            .enqueue(object : Callback<SearchBusinessResponse> {
                override fun onResponse(
                    call: Call<SearchBusinessResponse>, response: Response<SearchBusinessResponse>
                ) {
                    if (response.isSuccessful) {
                        businessList = response.body().let { response.body()!!.businesses }
                        _businessResponse.postValue(Resource.success(response.body()) as Resource<SearchBusinessResponse>?)
                    } else {
                        _businessResponse.postValue(
                            Resource.error(
                                gson.fromJson(
                                    response.errorBody().let { response.errorBody().toString() },
                                    ErrorResponse::class.java
                                ).error.description, SearchBusinessResponse()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<SearchBusinessResponse>, t: Throwable) {
                    _businessResponse.postValue(
                        Resource.error(
                            t.localizedMessage, SearchBusinessResponse()
                        )
                    )
                }
            })

    }

    fun setNewLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude

        _locationUpdate.postValue(LatLng(latitude, longitude))

        getFitnessList()
    }

    fun getCurrentLocation(): LatLng = LatLng(latitude, longitude)

    fun noLocationProvided() {
        TODO("Not yet implemented - noLocationProvided")
    }

    fun goToDetails(fragment: Fragment, route: Int, business: Business) {
        val bundle: Bundle = bundleOf(
            "destination" to "${business.coordinates?.latitude},${business.coordinates?.longitude}",
            "name" to business.name,
            "url" to business.url,
            "img" to business.imageUrl,
        )
        findNavController(fragment).navigate(route, bundle)
    }

    fun goToDetails(fragment: Fragment, route: Int, businessAlias: String) {
        businessList.find { business -> business.alias == businessAlias }?.let {
            goToDetails(fragment, route, it)
        }
    }

}