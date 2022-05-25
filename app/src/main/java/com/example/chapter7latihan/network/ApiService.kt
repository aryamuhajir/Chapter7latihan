package com.example.chapter7latihan.network

import com.example.chapter7latihan.model.GetAllUserResponseItem
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    fun getUsers(
        @Query("username") username: String) : Call<List<GetAllUserResponseItem>>

    @POST("user")
    fun register(
        @Field("name") name : String,
        @Field("address") username: String,
        @Field("umur") password: String,
        @Field("image") address: String,
        @Field("username") umur: String,
        @Field("password") image: String
    ) : Call<GetAllUserResponseItem>
}