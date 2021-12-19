package com.example.stocksapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stocks(
    val id:Int,
    val url : String,
    val name: String,
    val price : Int,
    val description : String
    ) : Parcelable
