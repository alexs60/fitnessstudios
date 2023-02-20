package com.alessandrofarandagancio.fitnessstudios.api

import com.alessandrofarandagancio.fitnessstudios.models.SearchBusinessResponse
import retrofit2.Call

interface ApiHelper {

    fun getBusinesses(
        latitude: String?,
        longitude: String?,
        location: String?,
        radius: String,
        categories: String,
        sort_by: String,
        limit: String
    ): Call<SearchBusinessResponse>
}