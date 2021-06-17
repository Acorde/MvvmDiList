package com.igor.mvihilt.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.igor.mvihilt.R
import com.igor.mvihilt.utils.DataState
import com.igor.mvihilt.utils.showWithView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetCountrieEvent)
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(this, { dateState ->

            when (dateState) {
                is DataState.Loading -> main_progress_bar.showWithView(true)

                is DataState.Success -> {
                    main_progress_bar.showWithView(false)
                }
                is DataState.Error -> {
                    main_progress_bar.showWithView(false)
                }
            }


        })
    }
}