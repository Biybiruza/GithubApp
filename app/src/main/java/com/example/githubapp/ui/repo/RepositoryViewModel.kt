package com.example.githubapp.ui.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.networking.ApiClient
import com.example.githubapp.networking.ApiService
import com.example.githubapp.networking.data.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel : ViewModel() {

    private val apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    val repositoryObserver = MutableLiveData<List<ResponseData>>()
    val error = MutableLiveData<String>()

    fun getRepository(login: String) {
        apiService.getRepositoryList(login).enqueue(object : Callback<List<ResponseData>>{
            override fun onResponse(
                call: Call<List<ResponseData>>,
                response: Response<List<ResponseData>>
            ) {
                if (response.isSuccessful && response.body() != null){
                    repositoryObserver.value = response.body()!!
                } else {
                    error.value = response.message()
                }
            }

            override fun onFailure(call: Call<List<ResponseData>>, t: Throwable) {
                error.value = t.localizedMessage
            }

        })
    }
}