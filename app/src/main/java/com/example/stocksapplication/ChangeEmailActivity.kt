package com.example.stocksapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.example.stocksapplication.Application.AppApplication
import com.example.stocksapplication.model.changeEmail
import com.example.stocksapplication.utils.LoadingItem
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeEmailActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val email : TextInputLayout = findViewById(R.id.changeEmailLayout)

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)

        val token = sharedPreferences.getString("token","")

        val oldEmail = sharedPreferences.getString("email","")
        email.editText?.setText(oldEmail)
        val loading =   LoadingItem(this)
        val confirm : Button = findViewById(R.id.confirmButton)
        confirm.setOnClickListener {
            if(TextUtils.isEmpty(email.editText?.text)){
                Toast.makeText(this@ChangeEmailActivity,"Please Enter Email",Toast.LENGTH_SHORT).show()
            }else{
                loading.startLoading()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        CoroutineScope(Dispatchers.IO).launch {
                            val data = changeEmail(email.editText?.text.toString())
                            val changeApplication = application as AppApplication
                            val service = changeApplication.service
                            service.ChangeEmail(data,"Bearer "+token.toString()).enqueue(object : Callback<ResponseBody>{
                                @SuppressLint("CommitPrefEdits")
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if(response.isSuccessful){
                                        sharedPreferences.edit().clear()
                                        startActivity(Intent(this@ChangeEmailActivity,LoginActivity::class.java))
                                    }else{
                                        Toast.makeText(applicationContext,"Failed1",Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
                                }

                            })
                        }
                        loading.isDismiss()
                    }
                }, 3000)
            }
        }
    }
}