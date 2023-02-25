package com.alessandrofarandagancio.fitnessstudios.models.yelp

import com.google.gson.annotations.SerializedName


data class SearchBusinessResponse(

    @SerializedName("businesses")
    var businesses: ArrayList<Business> = arrayListOf(),
    @SerializedName("total")
    var total: Int = 0,
    @SerializedName("region")
    var region: Region = Region()

)