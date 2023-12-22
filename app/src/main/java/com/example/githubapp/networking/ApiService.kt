package com.example.githubapp.networking

import com.example.githubapp.networking.data.Owner
import com.example.githubapp.networking.data.ResponseData
import com.example.githubapp.networking.data.UsersInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{login}")
    fun getUsersInfo(@Path("login") login: String): Call<UsersInfo>

    @GET("users/{login}/repos")
    fun getRepositoryList(@Path("login") login: String): Call<List<ResponseData>>

    @GET("search/users?")
    fun getSearchList(@Query("q") login: String): Call<List<Owner>>

    @GET("users")
    fun getUsers(): Call<List<Owner>>

}