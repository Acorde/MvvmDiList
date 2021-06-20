package com.igor.mvihilt.ui.counties

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igor.mvihilt.enum.SortTypeEnum
import com.igor.mvihilt.modules.Country
import com.igor.mvihilt.repository.Repository
import com.igor.mvihilt.utils.DataState
import com.igor.mvihilt.utils.sort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(private val mRepository: Repository) : ViewModel() {


    private var mCountries: MutableLiveData<DataState<List<Country>?>> = MutableLiveData()

    val dataState: MutableLiveData<DataState<List<Country>?>>
        get() = mCountries


    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetCountrieEvent -> mRepository.getCountries()
                    .onEach { dataState -> mCountries.value = dataState }
                    .launchIn(viewModelScope)

                is MainStateEvent.Sort -> sortData(mainStateEvent.sortType)

            }
        }


    }


    private fun sortData(sortType: SortTypeEnum) {
        viewModelScope.launch {
            sort(sortType, dataState).onEach { borderDataSet ->
                dataState.value = borderDataSet
            }.launchIn(viewModelScope)
        }
    }


}


sealed class MainStateEvent {
    class Sort(val sortType: SortTypeEnum) : MainStateEvent()
    object GetCountrieEvent : MainStateEvent()


}