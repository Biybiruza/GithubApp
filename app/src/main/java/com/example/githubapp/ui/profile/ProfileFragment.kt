package com.example.githubapp.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentProfileBinding
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.UsersInfo
import com.example.githubapp.ui.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel by viewModels<HomeViewModel>()
    var login = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("gitApp",Context.MODE_PRIVATE)
        login = sharedPreferences.getString("username","").toString()

        loadData()

        viewModel.apply {
            userInfo.observe(requireActivity(),getUserObserver)
        }
    }

    private val getUserObserver = Observer<UsersInfo>{
        binding.progressBar.visibility = View.GONE
        Glide.with(binding.root).load(it.avatar_url).into(binding.ivAvatar)
        binding.tvLogin.text = it.login
        binding.tvFollowersCount.text = it.followers.toString()
        binding.tvFollowingCount.text = it.following.toString()
        binding.tvRepositoriesCount.text = it.public_repos.toString()
        if (it.name != null){
            binding.tvEmail.text = it.name
        } else {binding.tvEmail.text = ""}
        if (it.bio != null){
            binding.tvBio.text = it.bio
        } else {binding.tvBio.text = ""}
        if (it.location != null){
            binding.tvLocation.text = it.location
        } else {binding.tvLocation.text = ""}
        if (it.company !=null){
            binding.tvCompany.text = it.company.toString()
        } else {binding.tvCompany.text = ""}
    }

    private fun loadData(){
        viewModel.loadUserInfo(login)
    }
}