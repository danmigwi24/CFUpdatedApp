package com.dan.jamiicfapp.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

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

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}

fun Context.alertDialog() {
    val alertDialog = AlertDialog.Builder(this)
    alertDialog.setTitle("Thanks for Donating to JCF")
    alertDialog.setMessage("Thanks for making this process successful We are proud of you")
    alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
    }
    alertDialog.show()
}

