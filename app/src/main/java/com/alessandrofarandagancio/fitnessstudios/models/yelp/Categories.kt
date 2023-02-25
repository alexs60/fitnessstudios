package com.alessandrofarandagancio.fitnessstudios.models.yelp

import com.google.gson.annotations.SerializedName


data class Categories(

    @SerializedName("alias")
    var alias: String,
    @SerializedName("title")
    var title: String

)