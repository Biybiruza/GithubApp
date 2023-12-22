package com.example.githubapp.ui.start_screen

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentStartBinding

class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("gitApp", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.apply {
            btSubmit.setOnClickListener {
                if (etUsername.text!!.isEmpty()) {
                    Toast.makeText(requireActivity(), "Enter your username, please!!!",Toast.LENGTH_LONG).show()
                } else {
                    editor.putString("username",etUsername.text.toString())
                    editor.apply()
                    val bundle = bundleOf("username" to etUsername.text.toString())
                    findNavController().navigate(R.id.action_startFragment_to_mainFragment, bundle)
                }
            }
        }

    }

}