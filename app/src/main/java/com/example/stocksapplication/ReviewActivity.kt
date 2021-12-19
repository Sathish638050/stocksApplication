package com.example.stocksapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapplication.Adapter.ReviewAdapter
import com.example.stocksapplication.Adapter.StackViewAdapter
import com.example.stocksapplication.Application.AppApplication
import com.example.stocksapplication.model.AllLoginHistory
import com.example.stocksapplication.model.AllStocks
import com.example.stocksapplication.network.AppInstance
import com.example.stocksapplication.network.AppService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("token","")

        val member : TextView = findViewById(R.id.member)
        member.text = "Member since : "+intent.getStringExtra("member")

        if(token != null){

            CoroutineScope(Dispatchers.IO).launch {
                val reviewApplication = application as AppApplication
                val service = reviewApplication.service
                service.getLoginHistory("Bearer $token").enqueue(object :Callback<AllLoginHistory?>{
                    override fun onResponse(
                        call: Call<AllLoginHistory?>,
                        response: Response<AllLoginHistory?>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_SHORT).show()
                            val recycle : RecyclerView = findViewById(R.id.recycleView)
                            val adapter = ReviewAdapter(response.body()!!.loginEntries)
                            recycle.adapter = adapter
                            recycle.layoutManager = LinearLayoutManager(this@ReviewActivity)
                        }else{
                            Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AllLoginHistory?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
    }
}