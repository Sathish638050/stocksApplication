package com.example.stocksapplication.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stocksapplication.R
import com.example.stocksapplication.model.Stocks
import kotlinx.android.synthetic.main.activity_stocklist_row.view.*

class StackViewAdapter(var listData: List<Stocks>) : RecyclerView.Adapter<StackViewAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_stocklist_row,parent,false)
        return MyViewHolder(view,mListener)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = listData[position].name
        holder.price.text = listData[position].price.toString()+" per stock"
        Glide.with(holder.url).load(listData[position].url).into(holder.url)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
    class MyViewHolder(view : View,listener:onItemClickListener):RecyclerView.ViewHolder(view) {
        val url: ImageView = view.findViewById(R.id.url)
        val name: TextView = view.findViewById(R.id.name)
        val price: TextView = view.findViewById(R.id.price)

        init {
            view.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
//            view.url.setOnClickListener {
//                listener.onItemClick(absoluteAdapterPosition)
//            }
        }
    }


}