package com.example.stocksapplication.network


import com.example.stocksapplication.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AppService {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body users:Login): Call<User>

    @POST("register")
    @Headers("Content-Type: application/json")
    fun register(@Body params : Login) : Call<ResponseBody>

    @GET("stocks")
    fun getStocks(@Header("Authorization") token : String) : Call<AllStocks>

    @POST("users/me/ownings")
    fun createOwings(@Body data : getStock, @Header("Authorization")token: String) :Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("users/me/email")
    fun ChangeEmail(@Body user: changeEmail, @Header("Authorization") token: String):Call<ResponseBody>

    @GET("users/me/loginHistory")
    fun getLoginHistory(@Header("Authorization") token:String) : Call<AllLoginHistory>

    @DELETE("users/me")
    fun deleteUser(@Header("Authorization") token: String) : Call<ResponseBody>

    @GET("users/me/ownings")
    fun getOwnStock(@Header("Authorization")token: String) : Call<OwnStocks>

    @DELETE("users/me/ownings/{owningId}")
    fun deleteOwning(@Path("owningId") id : Int,@Header("Authorization") token: String) : Call<ResponseBody>

    @GET("users")
    fun getLocalUsers(@Header("Authorization")token: String):Call<OtherUser>
}