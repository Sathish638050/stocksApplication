package com.example.stocksapplication.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapplication.R
import com.example.stocksapplication.model.LoginHistory
import java.text.SimpleDateFormat
import java.util.*

class ReviewAdapter(var listDate : List<LoginHistory>) : RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ReviewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_row_activity,parent,false)
        return ReviewAdapter.MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val data : Date = Date(listDate[position].loginTimestamp)
        val date :String =  sdf.format(data)
        holder.timeline.text = "Logged in: $date"

    }

    override fun getItemCount(): Int {
        return listDate?.size!!
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val timeline : TextView = view.findViewById(R.id.timeline)
    }
}