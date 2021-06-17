package com.igor.mvihilt.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igor.mvihilt.modules.Country
import com.igor.mvihilt.repository.Repository
import com.igor.mvihilt.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mRepository: Repository) : ViewModel() {


    private val mCountries: MutableLiveData<DataState<List<Country>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Country>>>
        get() = mCountries

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetCountrieEvent -> mRepository.getCountries()
                    .onEach { dataState -> mCountries.value = dataState }
                    .launchIn(viewModelScope)
                is MainStateEvent.None -> {}
            }
        }
    }

}


sealed class MainStateEvent {
    object GetCountrieEvent : MainStateEvent()
    object None : MainStateEvent()
}