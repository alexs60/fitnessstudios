package com.alessandrofarandagancio.fitnessstudios.api.google

import com.google.gson.JsonObject
import retrofit2.Call
import javax.inject.Inject

class DirectionApiHelperImpl @Inject constructor(private val directionApiService: DirectionApiService) :
    DirectionApiHelper {

    override fun getDirections(
        origin: String?,
        destination: String?,
    ): Call<JsonObject> {
        return directionApiService.getDirections(
            origin,
            destination
        )
    }

}