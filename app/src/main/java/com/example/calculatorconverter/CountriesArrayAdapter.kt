package com.example.calculatorconverter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.calculatorconverter.model.Countries
import kotlinx.android.synthetic.main.custom_spinner_row.view.*


class CountriesArrayAdapter(context: Context, countries: List<Countries>):
        ArrayAdapter<Countries>(context,0,countries){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position,convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View{
        val countries = getItem(position)

        val view = convertView?: LayoutInflater.from(context).inflate(
            R.layout.custom_spinner_row,
            parent,
            false
        )

        view.imageView_flag.loadImage(countries!!.image)
        view.textView_country.text = countries.country

        return view
    }

}