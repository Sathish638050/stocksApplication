package com.example.stocksapplication.model

import android.provider.ContactsContract

data class User(
    val id:Int,
    val email:String,
    val password:String,
    val token:String,
    val memberSince:Long
)

