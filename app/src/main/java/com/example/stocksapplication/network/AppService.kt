package com.example.stocksapplication.network


import com.example.stocksapplication.model.Login
import com.example.stocksapplication.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AppService {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body users:Login): Call<User>

    @POST("register")
    @Headers("Content-Type: application/json")
    fun register(@Body params : Login) : Call<ResponseBody>
}