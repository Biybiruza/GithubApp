package com.example.githubapp.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentProfileBinding
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("gitApp",Context.MODE_PRIVATE)
        val login = sharedPreferences.getString("username","")
        val apiService = ApiClient.getRetrofit().create(ApiService::class.java)

        loadUserInfo(login, apiService)
    }

    private fun loadUserInfo(login: String?, apiService: ApiService) {
        apiService.getUsersInfo(login!!).enqueue(object : Callback<UsersInfo> {
            override fun onResponse(call: Call<UsersInfo>, response: Response<UsersInfo>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null){
                    Glide.with(binding.root).load(response.body()!!.avatar_url).into(binding.ivAvatar)
                    binding.tvLogin.text = response.body()!!.login
                    binding.tvFollowersCount.text = response.body()!!.followers.toString()
                    binding.tvFollowingCount.text = response.body()!!.following.toString()
                    binding.tvRepositoriesCount.text = response.body()!!.public_repos.toString()
                    if (response.body()!!.name != null){
                        binding.tvEmail.text = response.body()!!.name
                    } else {binding.tvEmail.text = ""}
                    if (response.body()!!.bio != null){
                        binding.tvBio.text = response.body()!!.bio
                    } else {binding.tvBio.text = ""}
                    if (response.body()!!.location != null){
                        binding.tvLocation.text = response.body()!!.location
                    } else {binding.tvLocation.text = ""}
                    if (response.body()!!.company !=null){
                        binding.tvCompany.text = response.body()!!.company.toString()
                    } else {binding.tvCompany.text = ""}

                }
            }

            override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}