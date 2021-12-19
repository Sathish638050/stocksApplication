package com.example.stocksapplication.network

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppInstance {
    companion object{
        val BashURL = "https://android-kanini-course.cloud/priceapp-secure-backend/"

        fun getAppInstance():Retrofit{
            return Retrofit.Builder()
                .baseUrl(BashURL)
                .addConverterFactory(GsonConverterFactory.create(Gson())).build()
        }
    }
}