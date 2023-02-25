package com.alessandrofarandagancio.fitnessstudios.api.google

import com.alessandrofarandagancio.fitnessstudios.constant.googleAPIKey
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// not using https://github.com/googlemaps/google-maps-services-java
interface DirectionApiService {

    @GET("maps/api/directions/json?")
    fun getDirections(
        @Query("origin") origin: String?,
        @Query("destination") destination: String?,
        @Query("key") key: String = googleAPIKey
    ): Call<JsonObject>

}