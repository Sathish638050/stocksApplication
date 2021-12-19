package com.example.stocksapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.MessageFormat.format
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat.format
import android.widget.Button
import android.widget.TextView
import com.example.stocksapplication.model.User
import com.example.stocksapplication.utils.LoadingItem
import com.fasterxml.jackson.databind.util.ISO8601Utils.format
import com.google.gson.internal.bind.util.ISO8601Utils.format
import org.w3c.dom.Text
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val email : TextView = findViewById(R.id.email)
        val loading =   LoadingItem(this)
        val member : TextView = findViewById(R.id.member)
        val changeBtn : Button = findViewById(R.id.changebtn)
        val logout : Button = findViewById(R.id.logoutbtn)
        val userObj = intent.getParcelableExtra<User>(LoginActivity.INTENT_PARCELABLE2)
        val reviewBtn : Button = findViewById(R.id.reviewBtn)
        var memberSince : String = ""
        val delete : Button = findViewById(R.id.deletebtn)
        if (userObj != null) {
            email.text = sharedPreferences.getString("email","")
            val sdf = SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH)
            val data : Date = Date(sharedPreferences.getString("memberSince","")!!.toLong())
            val date :String =  sdf.format(data)
            memberSince = date
            member.text = "Member since: "+date.toString()
        }

        reviewBtn.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val intent = Intent(this@ProfileActivity,ReviewActivity::class.java)
                    startActivity(intent)
                    loading.isDismiss()
                }
            }, 3000)
        }



        changeBtn.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val intent = Intent(this@ProfileActivity,ChangeEmailActivity::class.java)
                    startActivity(intent)
                    loading.isDismiss()
                }
            }, 3000)
        }

        delete.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val intent = Intent(this@ProfileActivity,DeleteAccountActivity::class.java)
                    startActivity(intent)
                    loading.isDismiss()
                }
            }, 3000)
        }

        logout.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    sharedPreferences.edit().clear()
                    val intent = Intent(this@ProfileActivity,LoginActivity::class.java)
                    startActivity(intent)
                    loading.isDismiss()
                }
            }, 3000)

        }




    }
}