package com.example.stocksapplication.utils


import android.app.Activity
import android.app.AlertDialog
import com.example.stocksapplication.R

class LoadingItem(private val mActivity: Activity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading(){
        val inflater=mActivity.layoutInflater
        val dialogView=inflater.inflate(R.layout.activity_loading,null)

        val builder= AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isdialog=builder.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}