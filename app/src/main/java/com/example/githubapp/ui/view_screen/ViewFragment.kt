package com.example.githubapp.ui.view_screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.githubapp.R
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewFragment : Fragment(R.layout.fragment_view) {

    private val TAG = "ViewFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = arguments?.getString("username") ?: ""

        val apiService = ApiClient.getRetrofit().create(ApiService::class.java)

        apiService.getUsersInfo()
            .enqueue(object : Callback<List<UsersInfo>> {
                override fun onResponse(
                    call: Call<List<UsersInfo>>,
                    response: Response<List<UsersInfo>>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<List<UsersInfo>>, t: Throwable) {
                    Log.d(TAG, "onFailure: $t")
                    Toast.makeText(requireActivity(), "$t", Toast.LENGTH_LONG).show()
                }

            })

    }
}