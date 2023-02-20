package com.alessandrofarandagancio.fitnessstudios.api

import com.alessandrofarandagancio.fitnessstudios.constant.baseYelpRestApiUrl
import com.alessandrofarandagancio.fitnessstudios.models.SearchBusinessResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseYelpRestApiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

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