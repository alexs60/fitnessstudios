package com.alessandrofarandagancio.fitnessstudios.repository

import com.alessandrofarandagancio.fitnessstudios.api.google.DirectionApiHelper
import com.google.gson.JsonObject
import retrofit2.Call
import javax.inject.Inject


class DirectionsRepository @Inject constructor(
    private val directionApiHelper: DirectionApiHelper
) {

    fun getDirections(
        origin: String?,
        destination: String?,
    ): Call<JsonObject> {
        return directionApiHelper.getDirections(
            origin,
            destination,
        )
    }

}