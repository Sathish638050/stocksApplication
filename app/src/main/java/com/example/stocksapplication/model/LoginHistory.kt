package com.example.stocksapplication.model

data class AllLoginHistory(val loginEntries:List<LoginHistory>)
data class LoginHistory(val loginTimestamp : Long)
