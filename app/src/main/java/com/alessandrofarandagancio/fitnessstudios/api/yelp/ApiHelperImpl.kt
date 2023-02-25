package com.alessandrofarandagancio.fitnessstudios.api.yelp

import com.alessandrofarandagancio.fitnessstudios.models.yelp.SearchBusinessResponse
import retrofit2.Call
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override fun getBusinesses(
        latitude: String?,
        longitude: String?,
        location: String?,
        radius: String,
        categories: String,
        sortBy: String,
        limit: String
    ): Call<SearchBusinessResponse> {
        return apiService.getBusinesses(latitude, longitude, location, radius, categories, sortBy, limit)
    }

}