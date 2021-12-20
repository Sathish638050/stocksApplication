package com.example.stocksapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stocksapplication.model.Stocks
import com.squareup.picasso.Picasso

class GetStocksActivity : AppCompatActivity() {
    companion object{
        val INTENT_PARCELABLE1 = "OBJECT_INTENT"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_stocks)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val name : TextView = findViewById(R.id.titleTxt)
        val price : TextView = findViewById(R.id.price)
        val description : TextView = findViewById(R.id.description)
        val image : ImageView = findViewById(R.id.proImage)
        val al : TextView = findViewById(R.id.owning)
        val stockBtn : Button = findViewById(R.id.stockBtn)
        //haredPreferences = getSharedPreferences("user",Context.MODE_PRIVATE)

        val obj = intent.getParcelableExtra<Stocks>(StockListActivity.INTENT_PARCELABLE)

        if (obj != null) {
            name.text = obj.name
            //val string : Spanned = Html.fromHtml("Description: <br>${obj.description}</   b>")
            val text = "Description: ${obj.description}"
            val priceTxt = "Price per stock: ${obj.price}"
            val already = "Already owned by 10 people"
            val spannableString = SpannableString(text)
            val boldSpan = StyleSpan(Typeface.BOLD)
            spannableString.setSpan(boldSpan,13,13+obj.description.count(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            description.text = spannableString
            val spannableprice = SpannableString(priceTxt)
            spannableprice.setSpan(boldSpan,17,17+obj.price.toString().count(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            price.text = spannableprice
            Picasso.get().load(obj.url).into(image)
            val alreadyspanneable = SpannableString(already)
            alreadyspanneable.setSpan(boldSpan,17,19,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            al.text = alreadyspanneable

        }
        stockBtn.setOnClickListener {
            val intent = Intent(this@GetStocksActivity,StockPurchaseActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE1,obj)
            startActivity(intent)
        }
    }
}