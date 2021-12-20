package com.example.stocksapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapplication.Adapter.MyStockAdapter
import com.example.stocksapplication.Adapter.StackViewAdapter
import com.example.stocksapplication.Application.AppApplication
import com.example.stocksapplication.model.AllStocks
import com.example.stocksapplication.model.OwnStocks
import com.example.stocksapplication.utils.LoadingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyStocksActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_stocks)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("user",Context.MODE_PRIVATE)
        val loading =   LoadingItem(this)
        val token = sharedPreferences.getString("token","")
        if(token != null){
            CoroutineScope(Dispatchers.IO).launch {
                val app = application as AppApplication
                val service = app.service

                service.getOwnStock("Bearer $token").enqueue(object :Callback<OwnStocks>{
                    override fun onResponse(call: Call<OwnStocks>, response: Response<OwnStocks>) {
                        if(response.isSuccessful){
                            val mystocks = response.body()!!.stockOwnings
                            service.getStocks("Bearer $token").enqueue(object :Callback<AllStocks>{
                                override fun onResponse(
                                    call: Call<AllStocks>,
                                    response: Response<AllStocks>
                                ) {
                                    if(response.isSuccessful){
                                        val stocks = response.body()!!.stocks
                                        val recycle = findViewById<RecyclerView>(R.id.recycler)
                                        val adapter = MyStockAdapter(mystocks,stocks)
                                        recycle.adapter = adapter
                                        recycle.layoutManager = LinearLayoutManager(this@MyStocksActivity)
                                        adapter.setOnItemClickListener(object : MyStockAdapter.onItemClickListener{
                                            override fun onItemClick(position: Int) {
                                                loading.startLoading()
                                                val handler = Handler()
                                                handler.postDelayed(object : Runnable {
                                                    override fun run() {
                                                        service.deleteOwning(mystocks[position].owningId,"Bearer $token").enqueue(object :Callback<ResponseBody>{
                                                            override fun onResponse(
                                                                call: Call<ResponseBody>,
                                                                response: Response<ResponseBody>
                                                            ) {
                                                                if(response.isSuccessful){
                                                                    val intent = Intent(this@MyStocksActivity,StockListActivity::class.java)
                                                                    startActivity(intent)
                                                                }
                                                            }

                                                            override fun onFailure(
                                                                call: Call<ResponseBody>,
                                                                t: Throwable
                                                            ) {
                                                                TODO("Not yet implemented")
                                                            }

                                                        })
                                                        loading.isDismiss()
                                                    }
                                                }, 3000)
                                                Toast.makeText(this@MyStocksActivity,mystocks[position].owningId.toString(),Toast.LENGTH_SHORT).show()
                                            }

                                        })
                                    }
                                }

                                override fun onFailure(call: Call<AllStocks>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }

                            })
                        }else{
                            Toast.makeText(this@MyStocksActivity,response.code().toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<OwnStocks>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }

    }
}