package com.alessandrofarandagancio.fitnessstudios.models

import com.google.gson.annotations.SerializedName


data class SearchBusinessResponse(

    @SerializedName("businesses")
    var businesses: ArrayList<Business> = arrayListOf(),
    @SerializedName("total")
    var total: Int? = null,
    @SerializedName("region")
    var region: Region? = Region()

)