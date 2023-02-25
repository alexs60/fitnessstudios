package com.alessandrofarandagancio.fitnessstudios.models.yelp

import com.google.gson.annotations.SerializedName


data class Coordinates(

    @SerializedName("latitude") var latitude: Double = 0.0,
    @SerializedName("longitude") var longitude: Double = 0.0

)