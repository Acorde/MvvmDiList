package com.igor.mvihilt.ui.counties

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.igor.mvihilt.R
import com.igor.mvihilt.enum.SortTypeEnum
import com.igor.mvihilt.modules.Country
import com.igor.mvihilt.ui.BaseCountryFragment
import com.igor.mvihilt.ui.CountriesActivity
import com.igor.mvihilt.ui.counties.adapters.CountriesAdapter
import com.igor.mvihilt.utils.DataState
import com.igor.mvihilt.utils.showWithView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_countries.*
import javax.inject.Inject


@AndroidEntryPoint
class CountriesFragment : BaseCountryFragment() {

    @Inject
    lateinit var adapter: CountriesAdapter

    private val viewModel: CountriesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setStateEvent(MainStateEvent.GetCountrieEvent)

    }

    override fun onResume() {
        super.onResume()
        subscribeObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, { dateState ->

            when (dateState) {
                is DataState.Loading -> (activity as CountriesActivity).main_progress_bar.showWithView(
                    true
                )

                is DataState.Success -> {
                    (activity as CountriesActivity).main_progress_bar.showWithView(false)
                    showCountriesRecyclerView(dateState.data)
                }
                is DataState.Error -> {
                    (activity as CountriesActivity).main_progress_bar.showWithView(false)
                    view?.let {
                        Snackbar.make(
                            it,
                            dateState.exception.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        })
    }

    override fun sortData(sortType: SortTypeEnum) {
        if (viewModel.dataState.value != DataState.Loading) {
            viewModel.sortData(sortType)
        }
    }

    private fun showCountriesRecyclerView(countries: List<Country>?) {
        context?.let { context ->
            countries_rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            countries_rv.adapter = adapter
            adapter.setData(countries)

            adapter.setOnItemClick { selectedCountry ->
                Log.d("IgorTest", "Selected county is: ${selectedCountry.name}")
                activity?.let {
                    val navHostFragment =
                        it.supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
                    navHostFragment.navController.let { navController ->
                        navController.navigate(
                            CountriesFragmentDirections.actionCountriesFragmentToBorderCountriesFragment(
                                selectedCountry
                            )
                        )
                    }
                }
            }
        }

    }

}