package com.igor.mvihilt.ui


import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.igor.mvihilt.R
import com.igor.mvihilt.enum.SortTypeEnum

abstract class BaseCountryFragment : Fragment() {


    override fun onStart() {
        super.onStart()
        setGestures()
    }

    private fun setGestures() {
        (activity as CountriesActivity).findViewById<Toolbar>(R.id.topAppBar)
            .setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.filter_abc -> {
                        sortData(SortTypeEnum.SORT_BY_ABC)
                        true
                    }
                    R.id.filter_area -> {
                        sortData(SortTypeEnum.SORT_BY_AREA)
                        true
                    }

                    else -> false
                }

            }
    }

    abstract fun sortData(sortType: SortTypeEnum)
}