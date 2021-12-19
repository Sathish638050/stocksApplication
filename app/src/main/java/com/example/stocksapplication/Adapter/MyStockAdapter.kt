package com.example.stocksapplication.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapplication.R
import com.example.stocksapplication.model.MyStocks
import com.example.stocksapplication.model.Stocks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_stocklist_row.view.*
import kotlinx.android.synthetic.main.ownstock_row_list.view.*


class MyStockAdapter(var listData: List<MyStocks>,var listData2:List<Stocks>) : RecyclerView.Adapter<MyStockAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener : MyStockAdapter.onItemClickListener){
        mListener = listener
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStockAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ownstock_row_list,parent,false)
        return MyViewHolder(view,mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameTxt.text = listData2[listData[position].stockId].name
        holder.countTxt.text = listData[position].count.toString()+"stocks"
        holder.priceTxt.text = (listData2[listData[position].stockId].price * listData[position].count).toString()
        Picasso.get().load(listData2[listData[position].stockId].url).into(holder.urlTxt)
    }

    override fun getItemCount(): Int {
        return listData?.size!!
    }

    class MyViewHolder(view : View,listener: MyStockAdapter.onItemClickListener):RecyclerView.ViewHolder(view) {
        val urlTxt : ImageView = view.findViewById(R.id.urlTxt)
        val nameTxt : TextView = view.findViewById(R.id.nameTxt)
        val priceTxt : TextView = view.findViewById(R.id.priceTxt)
        val countTxt : TextView = view.findViewById(R.id.countTxt)
        val sellbtn : Button = view.findViewById(R.id.sellbtn)

        init {
            view.sellbtn.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }

        }
    }

}