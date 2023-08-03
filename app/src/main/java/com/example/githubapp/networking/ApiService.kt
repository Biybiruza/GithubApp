package com.example.githubapp.networking

import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("users/username")
    fun getUsersInfo(): Call<List<UsersInfo>>
}