package com.example.githubapp.ui.repo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("gitApp",Context.MODE_PRIVATE)

        navController = Navigation.findNavController(requireActivity(),R.id.containerView)

        adapter = RepositoryAdapter()

        val login = sharedPreferences.getString("username","")
        val apiService = ApiClient.getRetrofit().create(ApiService::class.java)

        loadRepositories(login,apiService)
    }

    private fun loadRepositories(login: String?, apiService: ApiService) {
        apiService.getRepositoryList(login!!).enqueue(object : Callback<List<ResponseData>>{
            override fun onResponse(
                call: Call<List<ResponseData>>,
                response: Response<List<ResponseData>>
            ) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null){
                    adapter.list = response.body()!!
                    binding.rvRepository.adapter = adapter
                    adapter.itemClickListener {
                        val bundle = bundleOf("url" to it)
                        navController.navigate(R.id.action_mainFragment_to_webViewFragment, bundle)
                    }
                }
            }

            override fun onFailure(call: Call<List<ResponseData>>, t: Throwable) {

            }

        })
    }
}