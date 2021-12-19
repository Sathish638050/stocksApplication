package com.example.stocksapplication.Application

import android.app.Application
import com.example.stocksapplication.network.AppService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

public open class AppApplication:Application() {
    lateinit var service : AppService
    override fun onCreate() {
        super.onCreate()
        service = loginApi()
    }

    private fun loginApi(): AppService {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/priceapp-secure-backend/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AppService::class.java)
    }
}


