package com.example.githubapp.ui.repo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryBinding
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.ResponseData
import com.example.githubapp.ui.view_screen.RepositoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private lateinit var binding: FragmentRepositoryBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: RepositoryAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<RepositoryViewModel>()
    var login = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("gitApp", Context.MODE_PRIVATE)
        navController = Navigation.findNavController(requireActivity(), R.id.containerView)
        adapter = RepositoryAdapter()

        login = sharedPreferences.getString("username", "").toString()

        loadData()

        viewModel.apply {
            repositoryObserver.observe(requireActivity(),repositoriesObserver)
        }
    }

    private fun loadData() {
        viewModel.getRepository(login)
    }

    private val repositoriesObserver = Observer<List<ResponseData>> {
        binding.progressBar.visibility = View.GONE
        adapter.list = it
        binding.rvRepository.adapter = adapter
        adapter.itemClickListener {url ->
            val bundle = bundleOf("url" to url)
            navController.navigate(R.id.action_mainFragment_to_webViewFragment, bundle)
        }
    }
}