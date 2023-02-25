package com.alessandrofarandagancio.fitnessstudios.models.yelp

import com.google.gson.annotations.SerializedName


data class Business(

    @SerializedName("id")
    var id: String,
    @SerializedName("alias")
    var alias: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("is_closed")
    var isClosed: Boolean,
    @SerializedName("url")
    var url: String,
    @SerializedName("review_count")
    var reviewCount: Int,
    @SerializedName("categories")
    var categories: ArrayList<Categories> = arrayListOf(),
    @SerializedName("rating")
    var rating: Double,
    @SerializedName("coordinates")
    var coordinates: Coordinates,
    @SerializedName("transactions")
    var transactions: ArrayList<String> = arrayListOf(),
    @SerializedName("location")
    var location: Location,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("display_phone")
    var displayPhone: String,
    @SerializedName("distance")
    var distance: Double

)