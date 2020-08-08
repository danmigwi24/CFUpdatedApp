package com.dan.jamiicfapp.utils

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
}

fun ProgressBar.pshow() {
    visibility = View.VISIBLE
}

fun ProgressBar.phide() {
    visibility = View.GONE
}

 fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
        .matches()
}

