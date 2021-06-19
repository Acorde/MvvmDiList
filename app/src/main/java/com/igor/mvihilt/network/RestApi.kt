package com.igor.mvihilt.network

import com.igor.mvihilt.modules.Country
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("all")
    suspend fun getAllCities(): List<Country>

    //https://restcountries.eu/rest/v2/all

    //https://restcountries.eu/rest/v2/alpha?codes=col;no;irn
    @GET("alpha")
    suspend fun getBordersCities(@Query("codes") codes : String) : List<Country>
}