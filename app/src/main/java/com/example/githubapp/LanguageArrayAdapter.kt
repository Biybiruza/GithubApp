package com.example.githubapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.githubapp.databinding.SpinnerItemBinding

class LanguageArrayAdapter(context: Context, countrylist: ArrayList<LanguageCountry>?): ArrayAdapter<LanguageCountry>(context, 0,
    countrylist!!
) {

    private lateinit var binding: SpinnerItemBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val country = getItem(position)

        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        binding = SpinnerItemBinding.bind(itemView)
        binding.apply {
            countryImage.setImageResource(country!!.image)
            countryName.text = country.name
        }
        return itemView
    }

}