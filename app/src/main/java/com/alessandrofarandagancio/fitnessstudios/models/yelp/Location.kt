package com.alessandrofarandagancio.fitnessstudios.models.yelp

import com.google.gson.annotations.SerializedName


data class Location(

    @SerializedName("address1")
    var address1: String,
    @SerializedName("address2")
    var address2: String,
    @SerializedName("address3")
    var address3: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("zip_code")
    var zipCode: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("state")
    var state: String,
    @SerializedName("display_address")
    var displayAddress: ArrayList<String> = arrayListOf()

)