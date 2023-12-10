package com.example.githubapp.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentHomeBinding
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.Owner
import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: UsersAdapter
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("gitApp", Context.MODE_PRIVATE)
        val service = ApiClient.getRetrofit().create(ApiService::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.containerView)
        adapter = UsersAdapter()

        val login = sharedPreferences.getString("username", "")

        loadUserInfo(login, service)
        loadUsers(service)
        adapter.itemClickListener {
            navController.navigate(
                R.id.action_mainFragment_to_webViewFragment,
                bundleOf("url" to it)
            )
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchList = arrayListOf<Owner>()
                service.getSearchList(p0.toString()).enqueue(object : Callback<List<Owner>> {
                    override fun onResponse(
                        call: Call<List<Owner>>,
                        response: Response<List<Owner>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            searchList.addAll(response.body()!!)
                            adapter.usersList = searchList
                            binding.rvUsers.adapter = adapter
                        }
                    }

                    override fun onFailure(call: Call<List<Owner>>, t: Throwable) {
                        Toast.makeText(requireActivity(), t.toString(), Toast.LENGTH_LONG).show()
                    }

                })
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun loadUserInfo(login: String?, service: ApiService) {
        service.getUsersInfo(login!!).enqueue(object : Callback<UsersInfo> {
            override fun onResponse(call: Call<UsersInfo>, response: Response<UsersInfo>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    Glide.with(binding.root).load(response.body()!!.avatar_url)
                        .into(binding.ivAvatar)
                    binding.username.text = response.body()!!.login
                }
            }

            override fun onFailure(call: Call<UsersInfo>, t: Throwable) {

            }

        })
    }


    private fun loadUsers(service: ApiService) {
        service.getUsers().enqueue(object : Callback<List<Owner>> {
            override fun onResponse(call: Call<List<Owner>>, response: Response<List<Owner>>) {
                binding.progressBar2.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    adapter.usersList = response.body()!!
                    binding.rvUsers.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Owner>>, t: Throwable) {

            }

        })
    }
}
