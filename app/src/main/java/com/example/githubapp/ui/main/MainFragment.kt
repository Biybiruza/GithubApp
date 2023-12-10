package com.example.githubapp.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("gitApp",Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putBoolean("finished", true)
        editor.apply()

        navController = Navigation.findNavController(requireActivity(),R.id.main_fragment)

        binding.bottomNav.setupWithNavController(navController)
    }
}