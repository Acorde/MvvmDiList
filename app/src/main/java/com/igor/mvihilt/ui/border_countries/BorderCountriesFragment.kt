package com.igor.mvihilt.ui.border_countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.igor.mvihilt.R
import com.igor.mvihilt.modules.Country
import com.igor.mvihilt.ui.MainActivity
import com.igor.mvihilt.ui.MainStateEvent
import com.igor.mvihilt.ui.border_countries.adapters.BorderCountriesAdapter
import com.igor.mvihilt.utils.DataState
import com.igor.mvihilt.utils.showWithView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_border_countries.*
import javax.inject.Inject

@AndroidEntryPoint
class BorderCountriesFragment : Fragment() {

    private val args: BorderCountriesFragmentArgs by navArgs()

    private val viewModel: BorderCountriesViewModel by viewModels()

    @Inject
    lateinit var adapter: BorderCountriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtraData { selectedCountry ->
            selectedCountry?.let {
                viewModel.getBorderCountries(it, MainStateEvent.GetCountrieEvent)
                subscribeObservers()
            }
        }


    }

    private fun subscribeObservers() {
        viewModel.borderDataSet.observe(this, { borderDataSet ->

            when (borderDataSet) {
                is DataState.Loading -> (activity as MainActivity).main_progress_bar.showWithView(
                    true
                )

                is DataState.Success -> {
                    (activity as MainActivity).main_progress_bar.showWithView(false)
                    showCountriesRecyclerView(borderDataSet.data)
                }
                is DataState.Error -> {
                    (activity as MainActivity).main_progress_bar.showWithView(false)
                    view?.let { Snackbar.make(it, "Error", Snackbar.LENGTH_SHORT).show() }
                }
            }
        })
    }

    private fun showCountriesRecyclerView(data: List<Country>?) {
        data?.let { countries ->
            context?.let { context ->
                boarder_countries_rv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                boarder_countries_rv.adapter = adapter
                adapter.setData(countries)
            }
        }

    }

    private fun getExtraData(listener: (Country?) -> Unit) {
        listener.invoke(args.extraSelectedCountry)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_border_countries, container, false)
    }


}