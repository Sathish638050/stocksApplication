package com.example.stocksapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.example.stocksapplication.Application.AppApplication
import com.example.stocksapplication.utils.LoadingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteAccountActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)



        val setemail : TextView = findViewById(R.id.message2Text)
        val loading =   LoadingItem(this)

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("token","")
        val email = sharedPreferences.getString("email","")
        setemail.text = email
        val btn : Button = findViewById(R.id.deleteButton)
        btn.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    CoroutineScope(Dispatchers.IO).launch {
                        val delete = application as AppApplication
                        val service = delete.service
                        service.deleteUser("Bearer $token").enqueue(object : Callback<ResponseBody>{
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                if(response.isSuccessful){
                                    val intent = Intent(this@DeleteAccountActivity,LoginActivity::class.java)
                                    sharedPreferences.edit().clear()
                                    startActivity(intent)
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })
                    }
                    loading.isDismiss()
                }
            }, 3000)

        }


    }
}