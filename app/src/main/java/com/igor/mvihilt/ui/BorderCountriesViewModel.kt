package com.igor.mvihilt.ui

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
class BorderCountriesViewModel @Inject constructor(private val mRepository: Repository) :
    ViewModel() {

    private val mBorderedCountries: MutableLiveData<DataState<List<Country>?>> = MutableLiveData()

    val borderDataSet: MutableLiveData<DataState<List<Country>?>>
        get() = mBorderedCountries

    fun getBorderCountries(country: Country, mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetCountrieEvent -> mRepository.getBorderCountries(
                    country.borders!!.joinToString(separator = ";")

                )
                    .onEach { borderDataSet -> mBorderedCountries.value = borderDataSet }
                    .launchIn(viewModelScope)
                is MainStateEvent.None -> {
                }
            }
        }
    }

}