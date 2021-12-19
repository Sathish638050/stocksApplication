package com.example.stocksapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.stocksapplication.Application.AppApplication
import com.example.stocksapplication.model.Stocks
import com.example.stocksapplication.model.getStock
import com.example.stocksapplication.utils.LoadingItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_get_stocks.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockPurchaseActivity : AppCompatActivity() {
    lateinit var sharedPreferences : SharedPreferences
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_purchase)

        val stock = intent.getParcelableExtra<Stocks>(GetStocksActivity.INTENT_PARCELABLE1)

        val image : ImageView = findViewById(R.id.proImage)
        val minus : Button = findViewById(R.id.minus)
        val plus : Button = findViewById(R.id.plus)
        val count : TextView = findViewById(R.id.count)
        val total : TextView = findViewById(R.id.totalprice)
        val purchase : Button = findViewById(R.id.stockBtn)
        var i = 0;
        val loading =   LoadingItem(this)
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")


        if (stock != null) {
            Picasso.get().load(stock.url).into(image)
        }

        minus.setOnClickListener {
            if(i >0){
                i -= 1
            }
            count.text = "Items: $i"
            if (stock != null) {
                total.text = "Price: ${stock.price.toInt() * i}$"
            }
            if(i == 0){
                minus.setBackgroundColor(Color.parseColor("#b3b3b3"))
                minus.isEnabled = false
            }else{
                minus.setBackgroundColor(Color.parseColor("#358686"))
            }
        }
        plus.setOnClickListener {
            i += 1
            minus.isEnabled = true
            count.text = "Items: $i"
            if (stock != null) {
                total.text = "Price: ${stock.price.toInt() * i}$"
            }
            if(i == 0){
                minus.setBackgroundColor(Color.parseColor("#b3b3b3"))
            }else{
                minus.setBackgroundColor(Color.parseColor("#358686"))
            }
        }
        stockBtn.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val data = stock?.id?.let { it1 -> getStock(it1,i) }
                    if (data != null) {
                        //Toast.makeText(this@StockPurchaseActivity,"${data.stockId},${data.count}",Toast.LENGTH_SHORT).show()
                        CoroutineScope(Dispatchers.IO).launch {
                            val stockApplication = application as AppApplication
                            val service = stockApplication.service
                            service.createOwings(data,"Bearer "+token.toString()).enqueue(object:Callback<ResponseBody>{
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    if(response.isSuccessful){
                                        val intent = Intent(this@StockPurchaseActivity,StockListActivity::class.java)
                                        startActivity(intent)
                                        Toast.makeText(this@StockPurchaseActivity,"Successfully Added!...",Toast.LENGTH_SHORT).show()
                                    }else{
                                        Toast.makeText(this@StockPurchaseActivity,"Failed Added!...",Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Toast.makeText(this@StockPurchaseActivity,"Failed Connection!...",Toast.LENGTH_SHORT).show()
                                }

                            })
                        }
                    }
                    loading.isDismiss()
                }
            }, 3000)

        }



    }
}