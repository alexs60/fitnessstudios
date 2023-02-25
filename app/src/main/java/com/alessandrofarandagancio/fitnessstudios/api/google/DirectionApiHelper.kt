package com.alessandrofarandagancio.fitnessstudios.api.google

import com.google.gson.JsonObject
import retrofit2.Call

interface DirectionApiHelper {

    fun getDirections(
        origin: String?,
        destination: String?,
    ): Call<JsonObject>
}