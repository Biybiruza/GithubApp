package com.example.githubapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.Owner
import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val service = ApiClient.getRetrofit().create(ApiService::class.java)

    val error = MutableLiveData<String>()
    val userData = MutableLiveData<List<Owner>>()
    val userInfo = MutableLiveData<UsersInfo>()

    fun loadUserInfo(login: String?) {
        service.getUsersInfo(login!!).enqueue(object : Callback<UsersInfo> {
            override fun onResponse(call: Call<UsersInfo>, response: Response<UsersInfo>) {
                if (response.isSuccessful && response.body() != null) {
                    userInfo.value = response.body()!!
                } else {
                    error.value = response.message()
                }
            }

            override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                error.value = t.localizedMessage
            }
        })
    }

    fun getUser() {
        ApiClient.getRetrofit().create(ApiService::class.java)
            .getUsers().enqueue(object : Callback<List<Owner>> {
            override fun onResponse(call: Call<List<Owner>>, response: Response<List<Owner>>) {
                if (response.isSuccessful && response.body() != null) {
                    userData.value = response.body()!!
                } else {
                    error.value = response.message()
                }
            }

            override fun onFailure(call: Call<List<Owner>>, t: Throwable) {
                error.value = t.localizedMessage
            }

        })
    }
}