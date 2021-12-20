package com.example.stocksapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapplication.Adapter.OtherUserAdapter
import com.example.stocksapplication.Application.AppApplication
import com.example.stocksapplication.model.LocalUsers
import com.example.stocksapplication.model.OtherUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherUserActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("token","")

        if(token != null){
            CoroutineScope(Dispatchers.IO).launch {
                val otheruser = application as AppApplication
                val service = otheruser.service
                service.getLocalUsers("Bearer $token").enqueue(object :Callback<OtherUser>{
                    override fun onResponse(
                        call: Call<OtherUser>,
                        response: Response<OtherUser>
                    ) {
                        if(response.isSuccessful){
                            val recycle : RecyclerView = findViewById(R.id.recycler)
                            val adapter = OtherUserAdapter(response.body()!!.users)
                            recycle.adapter = adapter
                            recycle.layoutManager = LinearLayoutManager(this@OtherUserActivity)
                        }
                    }

                    override fun onFailure(call: Call<OtherUser>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
    }
}