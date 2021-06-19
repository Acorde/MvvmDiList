package com.igor.mvihilt.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.igor.mvihilt.R
import com.igor.mvihilt.modules.Country
import com.igor.mvihilt.utils.DataState
import com.igor.mvihilt.utils.showWithView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_countries.*
import javax.inject.Inject


@AndroidEntryPoint
class CountriesFragment : Fragment() {

    @Inject
    lateinit var adapter: CountriesAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetCountrieEvent)
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
                is DataState.Loading -> (activity as MainActivity).main_progress_bar.showWithView(
                    true
                )

                is DataState.Success -> {
                    (activity as MainActivity).main_progress_bar.showWithView(false)
                    showCountriesRecyclerView(dateState.data)
                }
                is DataState.Error -> {
                    (activity as MainActivity).main_progress_bar.showWithView(false)
                }
            }


        })
    }

    private fun showCountriesRecyclerView(countries: List<Country>) {
        context?.let { context ->
            countries_rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            countries_rv.adapter = adapter
            adapter.setData(countries)

            adapter.setOnItemClick {
                Log.d("IgorTest", "Selected county is: ${it.name}")
            }
        }

    }


}