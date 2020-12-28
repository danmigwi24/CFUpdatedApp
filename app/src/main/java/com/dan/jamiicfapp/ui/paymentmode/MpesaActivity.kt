package com.dan.jamiicfapp.ui.paymentmode


import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.ui.jcahome.HomeActivity
import com.dan.jamiicfapp.ui.paymentmode.dviewmodel.MpesaViewModel
import com.dan.jamiicfapp.ui.paymentmode.dviewmodel.MpesaViewModelProvider
import com.dan.jamiicfapp.utils.APIException
import com.dan.jamiicfapp.utils.Connectivity
import com.dan.jamiicfapp.utils.Constants
import com.dan.jamiicfapp.utils.NoInternetException
import com.google.gson.JsonParser
import com.twigafoods.daraja.Daraja
import com.twigafoods.daraja.DarajaListener
import com.twigafoods.daraja.model.AccessToken
import com.twigafoods.daraja.util.Settings
import kotlinx.android.synthetic.main.activity_mpesa.*
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class MpesaActivity : AppCompatActivity(), KodeinAware {
    private lateinit var daraja: Daraja
    private lateinit var ACCESS_TOKEN: String
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var checkoutId: String
    private lateinit var userToken: String
    private var isProcessing = false
    private lateinit var uid: String
    private lateinit var sessionManager: SessionManager
    private lateinit var phonenumber2: String

    override val kodein by kodein()
    val factory by instance<MpesaViewModelProvider>()
    private lateinit var viewModel: MpesaViewModel

    companion object {
        private val TAG = MpesaActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpesa)

        setSupportActionBar(main_toolbar)
        sessionManager = SessionManager(this)
        viewModel = ViewModelProvider(this, factory).get(MpesaViewModel::class.java)

        phonenumber2 = phoneNumber.setText(sessionManager.fetchPhoneNumber()).toString()
        Log.e("phone", "${sessionManager.fetchPhoneNumber()}")

        initDaraja()

        pay.setOnClickListener {
            if (Connectivity.isConnected(this)) {
                dbalanceInquiry()
                Log.e(
                    "ids",
                    "pwd :${sessionManager.fetchPwdId()} user : ${sessionManager.fetchUserId()} "
                )
                //alertDialog()
            } else toast("Please connect to the internet")
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

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @Throws(IOException::class)
    private fun dbalanceInquiry(
    ) {
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


        val jsonArray = JSONArray()
        val jsonObject = JSONObject()
        jsonObject.put("BusinessShortCode", Constants.PAYBILL_BUSINESSsHORTCODE)
        jsonObject.put(
            "Password", Settings.generatePassword(
                Constants.PAYBILL_BUSINESSsHORTCODE,
                Constants.PASSKEY,
                Settings.generateTimestamp()
            )
        )
        jsonObject.put("Timestamp", Settings.generateTimestamp())
        jsonObject.put("TransactionType", "CustomerPayBillOnline")
        jsonObject.put("Amount", amount.text.toString().trim())
        jsonObject.put("PartyA", phoneNumber.text.toString().trim())
        jsonObject.put("PartyB", Constants.PAYBILL_BUSINESSsHORTCODE)
        jsonObject.put("PhoneNumber", phoneNumber.text.toString().trim())
        jsonObject.put("CallBackURL", Constants.CALLBACK_URL)
        jsonObject.put("AccountReference", Constants.ACCOUNTREFERENCE)
        jsonObject.put("TransactionDesc", "Help PWDs")
        jsonArray.put(jsonObject)

        val requestJson = jsonArray.toString().replace("[\\[\\]]".toRegex(), "")
        println(requestJson)
        Log.e("requestJson", requestJson)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = RequestBody.create(mediaType, requestJson)
        val request = Request.Builder()
            .url(Constants.CONFIRMATION_URL2)
            .post(requestBody)
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer $ACCESS_TOKEN")
            .addHeader("cache-control", "no-cache")
            .addHeader("postman-token", "2aa448be-7d56-a796-065f-b378ede8b136")
            .build()


        val response = client.newCall(request)
        Log.e("MPESA IS  3", response.request().toString())
        Log.e("MPESA IS  4", response.toString())


        response.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                if (response.isSuccessful) {
                    Log.e("MPESA IS READY", response.body.toString())
                    isProcessing = false

                    val res = response.body!!.string()
                    Log.e("CONFIRMATION RESPONSE 1", res)

                    val jsonObject = JsonParser().parse(res).asJsonObject

                    val responseCode = jsonObject.get("ResponseCode").asInt

                    runOnUiThread {
                        when (responseCode) {
                            0 -> {
                                toast("Payment Successful ${response.message}")
                                Handler(Looper.getMainLooper()).postDelayed({
                                    alertDialog()
                                }, 5000)

                            }
                            else -> toast("Payment failed. Please try again")
                        }
                    }

                } else {
                    toast("${response.message} cause by ${response.code.toString()}")
                    /*Log.e("MPESA ERROR 1", response.networkResponse.toString())
                    Log.e("MPESA ERROR 2", response.code.toString())
                    Log.e("MPESA ERROR 3", response.message.toString())*/
                    Log.e("MPESA ERROR 4", response.body!!.string())

                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, e.toString())
                isProcessing = false
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


    private fun alertDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Thanks for Donating to JCF")
        alertDialog.setMessage("Thanks for making this process successful We are proud of you")
        alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
            this.toast("Thanks for supporting JCF")
            startActivity(Intent(this, HomeActivity::class.java))
            saveInDatabase()
        }
        alertDialog.setNegativeButton(android.R.string.no) { dialog, which -> }
        alertDialog.show()
    }

    private fun saveInDatabase() {
        lifecycleScope.launch {
            val pwdId = sessionManager.fetchPwdId()
            val userId = sessionManager.fetchUserId()
            Log.e(
                "ids",
                "pwd :${sessionManager.fetchPwdId()} user : ${sessionManager.fetchUserId()} "
            )
            val usedPhonenumber = phoneNumber.text.toString()
            val amountTopay = amount.text.toString()
            try {
                val response =
                    viewModel.donatePwdVm(
                        pwdId.toString(),
                        userId.toString(),
                        usedPhonenumber,
                        amountTopay
                    )
                toast(response.message)
                clearFields(phoneNumber, amount)
            } catch (e: NoInternetException) {
                toast(e.message.toString())
            } catch (e: APIException) {
                toast(e.message.toString())
            }


        }
    }

}
