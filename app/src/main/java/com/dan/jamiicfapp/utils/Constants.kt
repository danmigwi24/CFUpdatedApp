package com.dan.jamiicfapp.utils

import com.twigafoods.daraja.util.Settings


object Constants {

    const val PUSH_NOTIFICATION = "pushNotification"
    const val NOTIFICATION_ID = 100
    const val NOTIFICATION_ID_BIG_IMAGE = 101
    const val CONFIRMATION_URL = "https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query"
    const val CONFIRMATION_URL2 = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest"


    const val PAYBILL_BUSINESSsHORTCODE = "174379"
    const val PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
    val PASSWORD_GENERATED = "{$PAYBILL_BUSINESSsHORTCODE$PASSKEY${Settings.generateTimestamp()}}"

    const val UPDATE_BALANCE_URL: String = ""
    const val CALLBACK_URL = "http://mpesa-requestbin.herokuapp.com/1pnk5xg1"
    const val CALLBACK_URL2 = "http://192.168.43.248/laravel-projects/project3JCA/public/api/getMpesaResponse"
    const val CALLBACK_URL3 = "http://mpesa-requestbin.herokuapp.com/1pnk5xg1"
    const val CONSUMER_KEY = "p5RtiKpODCmAnjWO5WOU8upjALa6vA0v"
    const val CONSUMER_SECRET = "UEgUce04HZtAUuJu"

    const val ACCOUNTREFERENCE = "JamiiCFA"

    // global topic to receive app wide push notifications
    const val TOPIC_GLOBAL = "global"

    // broadcast receiver intent filters
    const val REGISTRATION_COMPLETE = "registrationComplete"


    // id to handle the notification in the notification tray



    const val SHARED_PREF = "ah_firebase"


    const val Timestamp = "20200416133330"
    const val PASSWORD =
        "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjAwNDE2MTMzMzMw"
    const val Amount = "10"
    const val PartyA_PhoneNumber = "254798997948"
    const val PartyB_BUSINESSsHORTCODE = "174379"
    const val PhoneNumber = "254798997948"
    const val TransactionDesc = "myecommerceapp"
 /*{
      "BusinessShortCode": "174379",
      "Password": "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjAwNzE0MTUxMDEw",
      "Timestamp": "20200714151010",
      "TransactionType": "CustomerPayBillOnline",
      "Amount": "10",
      "PartyA": "254769219440",
      "PartyB": "174379",
      "PhoneNumber": "254769219440",
      "CallBackURL": "http://mpesa-requestbin.herokuapp.com/wmg4r8wm",
      "AccountReference": "dkimani",
      "TransactionDesc": "Help PWDs"
    }*/



    ///
    /*private fun makePayment() {
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
       Log.e("userToken", userToken)

       daraja.requestMPESAExpress(generateLNMExpress(), object : DarajaListener<LNMResult> {
           override fun onResult(result: LNMResult) {
               Log.e("ResponseDescription", result.ResponseDescription)
               if (result.ResponseDescription == "Success") {
                   //saveInDatabase()
               } else {
                   Toast.makeText(this@MpesaActivity, result.CustomerMessage, Toast.LENGTH_SHORT)
                       .show()
               }
               isProcessing = true
               checkoutId = result.CheckoutRequestID
           }

           override fun onError(error: String?) {
               Log.e("LNMResult error: ", error)
               isProcessing = false
           }
       })

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

   */
}

