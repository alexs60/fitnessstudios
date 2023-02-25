package com.alessandrofarandagancio.fitnessstudios.models.yelp

import com.google.gson.annotations.SerializedName


data class Center(

    @SerializedName("longitude")
    var longitude: Double = 0.0,
    @SerializedName("latitude")
    var latitude: Double = 0.0

)