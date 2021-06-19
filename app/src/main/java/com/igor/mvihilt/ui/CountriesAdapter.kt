package com.igor.mvihilt.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igor.mvihilt.R
import com.igor.mvihilt.modules.Country
import kotlinx.android.synthetic.main.item_country.view.*
import javax.inject.Inject

class CountriesAdapter @Inject constructor() : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {


    private var mCountries: List<Country>? = null

    public fun setData(countries: List<Country>) {
        mCountries = countries
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mCountries?.let {
            holder.bindData(it[position])
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
            .let {
                ViewHolder(it)
            }
    }

    override fun getItemCount(): Int {
        return mCountries?.size ?: 0
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(country: Country) {
            itemView.item_country_native_name.text =
                itemView.context.getString(R.string.item_native_name, country.nativeName)
            itemView.item_country_english_name.text =
                itemView.context.getString(R.string.item_english_name, country.name)
        }
    }

}