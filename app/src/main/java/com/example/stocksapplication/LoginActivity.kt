package com.example.stocksapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stocksapplication.Application.AppApplication
import com.example.stocksapplication.model.Login
import com.example.stocksapplication.model.User
import com.example.stocksapplication.utils.LoadingItem
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerBtn : Button = findViewById(R.id.registerBtn)
        val loginBtn : Button = findViewById(R.id.loginBtn)
        val loading =   LoadingItem(this)
        val email : TextInputLayout = findViewById(R.id.emailInput)
        val password : TextInputLayout = findViewById(R.id.passwordInput)

        registerBtn.setOnClickListener{
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    loading.isDismiss()
                }
            }, 3000)
            val regScreenIntent = Intent(this, RegisterActivity::class.java)

            startActivity(regScreenIntent)
        }

        loginBtn.setOnClickListener{
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    if(TextUtils.isEmpty(email.editText?.text.toString())){
                        Toast.makeText(applicationContext,"Enter Your Input",Toast.LENGTH_SHORT).show()
                    }else{
                        if(TextUtils.isEmpty(password.editText?.text.toString())){
                            Toast.makeText(applicationContext,"Enter Your Input",Toast.LENGTH_SHORT).show()
                        }else{
                            CoroutineScope(Dispatchers.IO).launch {
                                val user = Login(email.editText?.text.toString(),password.editText?.text.toString())
                                val loginApplication = application as AppApplication
                                val service = loginApplication.service
                                service.login(user).enqueue(object : Callback<User?>{
                                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
                                        if (response.isSuccessful){
                                            val intent = Intent(this@LoginActivity,StockListActivity::class.java)
                                            startActivity(intent)
                                            val sharedPreferences = getSharedPreferences("user",Context.MODE_PRIVATE)
                                            val editor = sharedPreferences.edit()
                                            editor.apply(){
                                                putString("token",response.body()!!.token)
                                            }.apply()
                                        }else{
                                            Toast.makeText(applicationContext,"Invalid Email and Password",Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<User?>, t: Throwable) {
                                        Toast.makeText(applicationContext,"Invalid Email and Password",Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }
                    }
                    loading.isDismiss()
                }
            }, 3000)
        }
    }


}