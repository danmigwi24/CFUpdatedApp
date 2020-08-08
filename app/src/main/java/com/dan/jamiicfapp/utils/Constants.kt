package com.dan.jamiicfapp.utils

import com.twigafoods.daraja.util.Settings


object Constants {

    const val PUSH_NOTIFICATION = "pushNotification"
    const val NOTIFICATION_ID = 100
    const val NOTIFICATION_ID_BIG_IMAGE = 101
    const val CONFIRMATION_URL = "https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query"



    const val PAYBILL_BUSINESSsHORTCODE = "174379"
    const val PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
    val PASSWORD_GENERATED = "{$PAYBILL_BUSINESSsHORTCODE$PASSKEY${Settings.generateTimestamp()}}"

    const val UPDATE_BALANCE_URL: String = ""
    const val CALLBACK_URL = "http://mpesa-requestbin.herokuapp.com/1pnk5xg1"

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
}
