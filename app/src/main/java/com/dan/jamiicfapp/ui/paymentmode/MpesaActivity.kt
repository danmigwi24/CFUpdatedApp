package com.dan.jamiicfapp.ui.paymentmode


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.utils.Connectivity
import com.dan.jamiicfapp.utils.Constants
import com.dan.jamiicfapp.utils.NotificationUtils
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.twigafoods.daraja.Daraja
import com.twigafoods.daraja.DarajaListener
import com.twigafoods.daraja.model.AccessToken
import com.twigafoods.daraja.model.LNMExpress
import com.twigafoods.daraja.model.LNMResult
import com.twigafoods.daraja.util.Settings
import kotlinx.android.synthetic.main.activity_mpesa.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class MpesaActivity : AppCompatActivity() {
    private lateinit var daraja: Daraja
    private lateinit var ACCESS_TOKEN: String
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var checkoutId: String
    private lateinit var userToken: String
    private var isProcessing = false

    private lateinit var uid: String

    private lateinit var sessionManager: SessionManager

    companion object {
        private val TAG = MpesaActivity::class.java.simpleName
    }

    override fun onStart() {
        super.onStart()
        // clear the notification area when the app is opened
        NotificationUtils(this).clearNotifications()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpesa)

        sessionManager = SessionManager(this)
        setSupportActionBar(main_toolbar)


        initDaraja()
        initBroadcastReceiver()

        pay.setOnClickListener {
            if (Connectivity.isConnected(this))
                makePayment()
            else toast("Please connect to the internet")
        }
    }

    private fun initDaraja() {
        daraja = Daraja.with(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, object :
            DarajaListener<AccessToken> {
            override fun onResult(result: AccessToken) {
                ACCESS_TOKEN = result.access_token
                sessionManager.saveAuthToken(result.access_token)
                Log.e("AccessToken", result.access_token)
            }

            override fun onError(error: String?) {
                Log.e("AccessToken error: ", error)
            }
        })
    }

    private fun initBroadcastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                checkStatus()
//                if (intent!!.action == Constants.PUSH_NOTIFICATION) {
//                    if (intent.hasExtra("type")) {
//                        if (intent.getStringExtra("type") == "topup") {
//                            checkStatus()
//                        }
//                    }
//                }
            }
        }
    }


    private fun makePayment() {
        if (isProcessing) {
            toast("Please wait for current transaction to complete...")
            return
        }

        if (!validated(phoneNumber)) {
            phoneNumber.error = "Please provide a phone number"
            return
        }

        if (!validated(amount)) {
            amount.error = "Please enter amount to pay"
            return
        }

        toast("Please wait...")
        userToken = sessionManager.fetchAuthToken().toString()
        Log.e("userToken1", userToken)

        daraja.requestMPESAExpress(generateLNMExpress(), object : DarajaListener<LNMResult> {
            override fun onResult(result: LNMResult) {
                Log.d(javaClass.simpleName, "LNMResult success: ${result.ResponseDescription}")
                Log.e("userToken2", result.ResponseDescription)

                isProcessing = true
                checkoutId = result.CheckoutRequestID
            }

            override fun onError(error: String?) {
                Log.d(javaClass.simpleName, "LNMResult error: $error")
                Log.e("LNMResult error: ", error)
                isProcessing = false
            }
        })

    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private fun generateLNMExpress(): LNMExpress = LNMExpress(
        Constants.PAYBILL_BUSINESSsHORTCODE,
        Constants.PASSKEY,
        amount.text.toString().trim(),
        phoneNumber.text.toString().trim(),
        Constants.PAYBILL_BUSINESSsHORTCODE,
        phoneNumber.text.toString().trim(),
        Constants.CALLBACK_URL + "token=$userToken",
        Constants.ACCOUNTREFERENCE,
        "HELP PWDs"
    )


    private fun checkStatus() {
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()

        val requestBody =
            ("{BusinessShortCode:${Constants.PAYBILL_BUSINESSsHORTCODE} ,Password:" + Settings.generatePassword(
                Constants.PAYBILL_BUSINESSsHORTCODE,
                Constants.PASSKEY,
                Settings.generateTimestamp()
            ) + ",Timestamp:" + Settings.generateTimestamp() + ",CheckoutRequestID:" + checkoutId + "}"
                    ).toRequestBody(mediaType)

        val request = Request.Builder().url(Constants.CONFIRMATION_URL)
            .post(requestBody)
            .header("authorization", "Bearer $ACCESS_TOKEN")
            .addHeader("content-type", "application/json")
            .build()

        Log.e("LNMP", request.toString())
        val call = client.newCall(request).execute()
        Log.e("LNMP", call.body.toString())
        if (call.isSuccessful){
            updateBalance()
        }

        /*  call.enqueue(object : Callback {
              override fun onResponse(call: Call, response: Response) {

                  if (response.isSuccessful) {
                      Log.e("MPESA IS READY", response.body.toString())
                      isProcessing = false

                      val res = response.body!!.string()
                      Log.e("CONFIRMATION RESPONSE", res)
                      val jsonObject = JsonParser().parse(res).asJsonObject

                      val resultCode = jsonObject.get("ResultCode").asInt

                      runOnUiThread {
                          when (resultCode) {
                              0 -> {
                                  toast("Payment Successful")
                                  updateBalance()
                              }
                              else -> toast("Payment failed. Please try again")
                          }
                      }

                  } else {
                      Log.e("MPESA ERROR", response.message)
                      Log.e("MPESA ERROR", response.code.toString())
                  }
              }

              override fun onFailure(call: Call, e: IOException) {
                  Log.e(TAG, e.toString())
                  isProcessing = false
              }


          })*/
    }

    private fun updateBalance() {
        val client = OkHttpClient()
        val url = Constants.UPDATE_BALANCE_URL.toHttpUrlOrNull()
        val urlBuilder = url!!.newBuilder()

        urlBuilder.addQueryParameter("uid", uid)
        urlBuilder.addQueryParameter("checkoutId", checkoutId)
        urlBuilder.addQueryParameter("time", System.currentTimeMillis().toString())
        urlBuilder.addQueryParameter("description", "Balance top up")
        urlBuilder.addQueryParameter("amount", amount.text.toString().trim())

        Log.e("url", url.toString())

        val transactionObject = urlBuilder.build()
        Log.e("OBJECT", transactionObject.toString())

        val request = Request.Builder().url(transactionObject.toString()).build()

        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val res = response.body!!.string()
                    Log.e("Update balance res:", res)
                    clearFields(phoneNumber, amount)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, e.toString())
            }


        })
    }

    private fun validated(vararg views: View): Boolean {
        var ok = true
        for (v in views) {
            if (v is EditText) {
                if (TextUtils.isEmpty(v.text.toString())) {
                    ok = false
                }
            }
        }
        return ok
    }

    private fun clearFields(vararg views: View) {
        for (v in views) {
            if (v is EditText) {
                v.text = null
            }
        }
    }


    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(Constants.REGISTRATION_COMPLETE))
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(Constants.PUSH_NOTIFICATION))

    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onPause()
    }


}
