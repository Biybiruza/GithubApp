package com.example.githubapp.networking

import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/")
    fun getUsersInfo(@Query("login") login: String): Call<UsersInfo>
}