package com.dan.jamiicfapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.dan.jamiicfapp.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(context: Context) : Interceptor {
    val appcontext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Please Check your internet connection")

        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            appcontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}