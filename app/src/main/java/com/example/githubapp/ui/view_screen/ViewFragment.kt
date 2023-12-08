package com.example.githubapp.ui.view_screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentViewBinding
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.ResponseData
import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewFragment : Fragment(R.layout.fragment_view) {

    private val TAG = "ViewFragment"
    private lateinit var binding: FragmentViewBinding
    private var username = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewBinding.bind(view)

        val apiService = ApiClient.getRetrofit().create(ApiService::class.java)

        username = arguments?.getString("username") ?: ""

        Toast.makeText(requireActivity(), username+"", Toast.LENGTH_LONG).show()

        getUser(apiService)
        getRepository(apiService)

    }

    private fun getRepository(apiService: ApiService) {
        apiService.getRepositoryList(username)
            .enqueue(object: Callback<List<ResponseData>> {
                override fun onResponse(
                    call: Call<List<ResponseData>>,
                    response: Response<List<ResponseData>>
                ) {
                    if(response.isSuccessful && response.body() != null) {
                        val adapter = RepositoryAdapter()
                        adapter.list = response.body()!!
                        binding.rvRepository.layoutManager =
                            LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
                        binding.rvRepository.adapter = adapter

                        adapter.itemClickListener {
                            val bundle = Bundle()
                            bundle.putString("url", it)
                            findNavController().navigate(R.id.action_viewFragment_to_webViewFragment, bundle)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ResponseData>>, t: Throwable) {
                    Toast.makeText(requireActivity(), "onFailure ",Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun getUser(apiService: ApiService) {
        apiService.getUsersInfo(username)
            .enqueue(object : Callback<UsersInfo> {
                override fun onResponse(
                    call: Call<UsersInfo>,
                    response: Response<UsersInfo>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        Log.d(TAG, "onResponse: ${response.body()}")
                        loadData(response.body())
                    } else {
                        Toast.makeText(requireActivity(), "Response's code: "+response.code(),Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                    Log.d(TAG, "onFailure: $t")
                    Toast.makeText(requireActivity(), "$t", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun loadData(usersInfo: UsersInfo?) {
        binding.apply {
            Glide.with(ivPhoto).load(usersInfo?.avatar_url).into(ivPhoto)
            tvName.text = usersInfo?.name
            tvBio.text = usersInfo?.bio
            tvLocation.text = usersInfo?.location
            tvFollowersCount.text = usersInfo?.followers.toString()
            tvFollowingCount.text = usersInfo?.following.toString()
            tvRepositoriesCount.text = usersInfo?.public_repos.toString()
        }
    }
}