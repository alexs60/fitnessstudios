package com.alessandrofarandagancio.fitnessstudios.models.yelp

import com.google.gson.annotations.SerializedName


class ErrorResponse(

    @SerializedName("error")
    var error: Error,
) {

    inner class Error(

        @SerializedName("code")
        var code: String,
        @SerializedName("description")
        var description: String

    )
}
