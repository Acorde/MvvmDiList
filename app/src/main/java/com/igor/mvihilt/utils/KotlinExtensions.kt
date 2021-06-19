package com.igor.mvihilt.utils

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igor.mvihilt.enum.SortTypeEnum
import com.igor.mvihilt.modules.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun View.showWithView(show: Boolean): Pair<Boolean, View> {
    when (show) {
        true -> this.visibility = View.VISIBLE
        false -> this.visibility = View.GONE
    }
    return Pair(show, this)
}

fun ViewModel.sort(sortType: SortTypeEnum, dataset : MutableLiveData<DataState<List<Country>?>>): Flow<DataState<List<Country>>> = flow {
    try {
        when (sortType) {
            SortTypeEnum.SORT_BY_AREA -> {
                if (dataset.value is DataState.Success) {
                    (dataset.value as DataState.Success<List<Country>>).data.sortedByDescending { it.area }
                        .let {
                            for (c in it){
                                Log.d("IgorTest", "City is ${c.name} Area is ${c.area} \n")
                            }
                            emit(DataState.Success(it))
                        }
                }
            }

            SortTypeEnum.SORT_BY_ABC -> {
                if (dataset.value is DataState.Success) {
                    (dataset.value as DataState.Success<List<Country>>).data.sortedBy { it.name }
                        .let {
                            emit(DataState.Success(it))
                        }

                }
            }
        }
    } catch (ex: Exception) {
        emit(DataState.Error(ex))
    }
}