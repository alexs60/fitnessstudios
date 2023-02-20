package com.alessandrofarandagancio.fitnessstudios.models

import com.google.gson.annotations.SerializedName


data class Region(

    @SerializedName("center")
    var center: Center? = Center()

)