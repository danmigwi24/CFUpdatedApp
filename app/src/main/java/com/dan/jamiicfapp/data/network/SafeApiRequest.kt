package com.dan.jamiicfapp.data.network

import android.util.Log
import com.dan.jamiicfapp.utils.APIException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend (() -> Response<T>)): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            val successful = response.body().toString()
           // Log.e("isSuccessful", successful)
            return response.body()!!
        } else {
            val error = response.errorBody().toString()
            Log.e("error", error)
            val message = StringBuilder()
            error.let {
                try {
                    val jsonObject = JSONObject(it).getString("message")
                    message.append(jsonObject)
                } catch (e: JSONException) {
                    Log.e("JSONException", e.toString())
                }
                message.append("\n")
            }
            message.append("Error code:${response.code()}")
            throw APIException(message.toString())

        }

    }
}