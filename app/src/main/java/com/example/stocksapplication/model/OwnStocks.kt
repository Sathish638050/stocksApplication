package com.example.stocksapplication.model

data class OwnStocks(val stockOwnings: List<MyStocks>)
data class MyStocks(val userId : Int,val stockId : Int,val owningId :Int,val count :Int,val dateTimestamp : Long)
