package com.igor.mvihilt.repository

import com.igor.mvihilt.modules.Country
import com.igor.mvihilt.network.RestApi
import com.igor.mvihilt.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val restApi: RestApi) {

    suspend fun getCountries(): Flow<DataState<List<Country>>> = flow {

        emit(DataState.Loading)

        try {
            val data = restApi.getAllCities()
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getBorderCountries(codes : String): Flow<DataState<List<Country>>> = flow {

        emit(DataState.Loading)

        try {
            val data = restApi.getBordersCities(codes)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}