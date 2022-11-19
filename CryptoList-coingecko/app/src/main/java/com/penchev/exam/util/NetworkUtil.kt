package com.penchev.exam.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
class NetworkUtil (private val  context: Context) {

    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                return if (capabilities.hasTransport(TRANSPORT_CELLULAR)) {
                    true
                } else if (capabilities.hasTransport(TRANSPORT_WIFI)) {
                    true
                } else capabilities.hasTransport(TRANSPORT_ETHERNET)
            }
        }
        return false
    }
}