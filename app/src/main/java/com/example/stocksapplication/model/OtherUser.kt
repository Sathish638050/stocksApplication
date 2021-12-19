package com.example.stocksapplication.model

data class OtherUser(val users : List<LocalUsers>)
data class LocalUsers(val email : String,val ownsStocks : String)
