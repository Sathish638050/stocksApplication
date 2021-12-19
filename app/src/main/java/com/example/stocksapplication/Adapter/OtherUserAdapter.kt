package com.example.stocksapplication.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapplication.R
import com.example.stocksapplication.model.LocalUsers

class OtherUserAdapter(var listData : List<LocalUsers>) : RecyclerView.Adapter<OtherUserAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.other_user_row,parent,false)
        return OtherUserAdapter.MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.email.text = listData[position].email
        holder.stockOwn.text = "Owns stocks: "+listData[position].ownsStocks
        holder.url.text = listData[position].email[0].toString()
    }

    override fun getItemCount(): Int {
        return listData?.size!!
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view)  {
        val url : TextView = view.findViewById(R.id.url)
        val email : TextView = view.findViewById(R.id.email)
        val stockOwn : TextView = view.findViewById(R.id.stockOwn)
    }

}