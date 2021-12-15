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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val registerBtn : Button = findViewById(R.id.registerBtn)
        val email : TextInputLayout = findViewById(R.id.emailInput)
        val password : TextInputLayout = findViewById(R.id.passwordInput)
        val loading = LoadingItem(this)
        val intent = Intent(this,StockListActivity::class.java)

        registerBtn.setOnClickListener{
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run() {
                    if(TextUtils.isEmpty(email.editText?.text.toString())){
                        Toast.makeText(applicationContext,"Enter Your Input", Toast.LENGTH_SHORT).show()
                    }else{
                        if(TextUtils.isEmpty(password.editText?.text.toString())){
                            Toast.makeText(applicationContext,"Enter Your Input", Toast.LENGTH_SHORT).show()
                        }else{
                            val userData = Login(email.editText?.text.toString(),password.editText?.text.toString())
                            CoroutineScope(Dispatchers.IO).launch {
                                val registerApplication = application as AppApplication
                                val service = registerApplication.service
                                service.login(userData).enqueue(object : Callback<User?>{
                                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
                                        if(response.isSuccessful){
                                            Toast.makeText(applicationContext,"Email Already Exist",Toast.LENGTH_SHORT).show()
                                        }
                                        else{
                                            service.register(userData).enqueue(object :Callback<ResponseBody>{
                                                override fun onResponse(
                                                    call: Call<ResponseBody>,
                                                    response: Response<ResponseBody>
                                                ) {
                                                    if(response.isSuccessful){
                                                        service.login(userData).enqueue(object  : Callback<User?>{
                                                            override fun onResponse(
                                                                call: Call<User?>,
                                                                response: Response<User?>
                                                            ) {
                                                                if(response.isSuccessful){
                                                                    val intent = Intent(this@RegisterActivity,StockListActivity::class.java)
                                                                    startActivity(intent)
                                                                    val sharedPreferences = getSharedPreferences("user",
                                                                        Context.MODE_PRIVATE)
                                                                    val editor = sharedPreferences.edit()
                                                                    editor.apply(){
                                                                        putString("token",response.body()!!.token)
                                                                    }.apply()
                                                                }
                                                            }

                                                            override fun onFailure(
                                                                call: Call<User?>,
                                                                t: Throwable
                                                            ) {
                                                                TODO("Not yet implemented")
                                                            }

                                                        })
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<ResponseBody>,
                                                    t: Throwable
                                                ) {
                                                    TODO("Not yet implemented")
                                                }

                                            })
                                        }
                                    }

                                    override fun onFailure(call: Call<User?>, t: Throwable) {
                                        Toast.makeText(applicationContext,"Register1",Toast.LENGTH_SHORT).show()
                                    }

                                })
                            }
                        }
                    }
                }
            },3000)

        }
    }




}