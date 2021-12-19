package com.example.stocksapplication.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.stocksapplication.model.AllStocks
import com.example.stocksapplication.model.Stocks
import androidx.lifecycle.viewModelScope
import com.example.stocksapplication.network.AppInstance
import com.example.stocksapplication.network.AppService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StackListViewModel:ViewModel() {
    var recyclerData : MutableLiveData<AllStocks> = MutableLiveData()

    fun getRecyclerListData(): MutableLiveData<AllStocks>{
        return recyclerData
    }
    fun makeApiCall(token : String){
        CoroutineScope(Dispatchers.IO).launch {
            val instance = AppInstance.getAppInstance().create(AppService::class.java)
            instance.getStocks("Bearer+$token").enqueue(object : Callback<AllStocks>{
                override fun onResponse(call: Call<AllStocks>, response: Response<AllStocks>) {
                    if(response.isSuccessful){
                        recyclerData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<AllStocks>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}