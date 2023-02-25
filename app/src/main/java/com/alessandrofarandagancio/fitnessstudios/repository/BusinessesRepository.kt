package com.alessandrofarandagancio.fitnessstudios.repository

import com.alessandrofarandagancio.fitnessstudios.api.yelp.ApiHelper
import com.alessandrofarandagancio.fitnessstudios.constant.categoryList
import com.alessandrofarandagancio.fitnessstudios.constant.resultLimit
import com.alessandrofarandagancio.fitnessstudios.constant.searchRadius
import com.alessandrofarandagancio.fitnessstudios.constant.sortRule
import com.alessandrofarandagancio.fitnessstudios.models.yelp.SearchBusinessResponse
import retrofit2.Call
import javax.inject.Inject


class BusinessesRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    fun getBusinesses(
        latitude: String?,
        longitude: String?,
        location: String?,
    ): Call<SearchBusinessResponse> {
        return apiHelper.getBusinesses(latitude, longitude,location, searchRadius, categoryList, sortRule, resultLimit)
    }

}