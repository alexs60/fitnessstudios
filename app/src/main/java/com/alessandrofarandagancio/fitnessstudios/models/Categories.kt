package com.alessandrofarandagancio.fitnessstudios.models

import com.google.gson.annotations.SerializedName


data class Categories(

    @SerializedName("alias")
    var alias: String? = null,
    @SerializedName("title")
    var title: String? = null

)