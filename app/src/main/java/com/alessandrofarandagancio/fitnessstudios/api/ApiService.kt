package com.alessandrofarandagancio.fitnessstudios.api

import com.alessandrofarandagancio.fitnessstudios.constant.yelpAPIKey
import com.alessandrofarandagancio.fitnessstudios.models.SearchBusinessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: Bearer $yelpAPIKey")
    @GET("v3/businesses/search")
    fun getBusinesses(
        @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?,
        @Query("location") location: String?,
        @Query("radius") radius: String,
        @Query("categories") categories: String,
        @Query("sort_by") sortBy: String,
        @Query("limit") limit: String
    ): Call<SearchBusinessResponse>
}