package com.example.ryuji_mvvm_architecture.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

object Utility {

    @SuppressLint("MissingPermission")
    fun isOnline(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnectedOrConnecting
    }
}