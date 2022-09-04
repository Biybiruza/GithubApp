package com.example.githubapp.language

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.githubapp.MainActivity
import com.example.githubapp.databinding.ActivityLanguageBinding
import com.example.githubapp.signup.WelcomeActivity

class LanguageActivity : AppCompatActivity() {

    lateinit var languageCountry: LanguageCountry
    private lateinit var binding: ActivityLanguageBinding
    val languageList = arrayOf("all", "English", "Karakalpak latin", "Karakalpak kirill", "Russion", "Uzbek")

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnNext.setOnClickListener{
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }

            customSpinner()
    }


    private fun customSpinner(){
        val adapter = LanguageArrayAdapter(this, Countries.list)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    1 -> {}
                }
//                languageCountry = LanguageCountry(position,Countries.countries[position])
                if (Countries.countries[position] != "All Language")
                    intentSelect()
//                binding.spinner.adapter.apply {
//                    if (binding.text.text == "All Language"){
//                        intentSelect()
//                    }
//                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun simpleSpinner(){
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, languageList)
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
    }

    fun intentSelect(){
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}