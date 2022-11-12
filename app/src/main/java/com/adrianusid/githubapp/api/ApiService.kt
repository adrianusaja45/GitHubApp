package com.adrianusid.githubapp.api

import com.adrianusid.githubapp.BuildConfig
import com.adrianusid.githubapp.DetailUser
import com.adrianusid.githubapp.Follow
import com.adrianusid.githubapp.Response
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("search/users")
    fun getUser(
        @Query("q") id: String
    ): Call<Response>


    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>


    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<Follow>>


    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<Follow>>
}

