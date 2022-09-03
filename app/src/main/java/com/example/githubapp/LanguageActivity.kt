package com.example.githubapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.githubapp.databinding.ActivityLanguageBinding
import java.util.*

class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding
//    val languageList = arrayOf("all", "English", "Karakalpak latin", "Karakalpak kirill", "Russion", "Uzbek")

        override fun onCreate(savedInstanceState: Bundle?) {
            loadLocate()
        super.onCreate(savedInstanceState)



        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

            customSpinner()
    }


    private fun customSpinner(){
        val adapter = LanguageArrayAdapter(this, Countries.list)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                if (Countries.countries[position] != "All Language"){

                when(position){
                    1 -> {
                        setLocal("en")
                        recreate()
                        intentSelect()
                    }
                    2 -> {
                        setLocal("kaa")
                        recreate()
                        intentSelect()
                    }
                    3 -> {
                        setLocal("krc")
                        recreate()
                        intentSelect()
                    }
                    4 -> {
                        setLocal("ru")
                        recreate()
                        intentSelect()
                    }
                    5 -> {
                        setLocal("uz")
                        recreate()
                        intentSelect()
                    }
                }
//
//                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setLocal(Lang: String){
        val local = Locale(Lang)
        Locale.setDefault(local)
        val config = Configuration()
        config.locale = local
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("myLang",Lang)
        editor.apply()
    }

    private fun loadLocate(){
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val language = sharedPreferences.getString("MyLang", "")
        setLocal(language!!)
    }

    /*fun simpleSpinner(){
        val arrayAdapter = ArrayAdapter<String>(this,
            R.layout.simple_spinner_dropdown_item, languageList)
        binding.spinner.adapter = arrayAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (languageList[position] != "all"){
                    intentSelect()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }*/

    fun intentSelect(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}