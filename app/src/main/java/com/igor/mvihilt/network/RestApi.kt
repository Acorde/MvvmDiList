package com.igor.mvihilt.network

import com.igor.mvihilt.modules.Country
import retrofit2.http.GET

interface RestApi {

    @GET("all")
    suspend fun getAllCities(): List<Country>

    @GET("all")
    suspend fun getBordersCities(): List<Country>
}