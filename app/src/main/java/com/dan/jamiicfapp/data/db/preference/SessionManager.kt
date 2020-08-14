package com.dan.jamiicfapp.data.db.preference

import android.content.Context
import android.content.SharedPreferences
import com.dan.jamiicfapp.R


class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_TOKEN2 = "user_token"
        const val PHONENUMBER = "phonenumber"
        const val KEY_SAVED_TIME: String = "time"
        const val KEY_SAVED_TIME2: String = "time"
        const val PWD_ID: String = "time2"
    }

    /**
     * Function to save timestamp  when data is loaded
     * Function to fetch timestamp when data was loaded
     */
    fun saveTimeStamp(time: String) {
        val editor = prefs.edit()
        editor.putString(KEY_SAVED_TIME, time)
        editor.apply()
    }

    fun fetchTimeStamp(): String? {
        return prefs.getString(KEY_SAVED_TIME, null)

    }
   fun saveTimeStamp2(time: String) {
        val editor = prefs.edit()
        editor.putString(KEY_SAVED_TIME2, time)
        editor.apply()
    }

    fun fetchTimeStamp2(): String? {
        return prefs.getString(KEY_SAVED_TIME2, null)

    }

    /**
     * Function to save Pwd id
     * Function to fetch pwd id
     */

    fun savePwdId(pwd_id: String) {
        val editor = prefs.edit()
        editor.putString(PWD_ID, pwd_id)
        editor.apply()
    }

    fun fetchPwdId(): String? {
        return prefs.getString(PWD_ID, null)

    }

    /**
     * Function to save UserId
     * Function to fetch UserId
     */

    fun saveUserId(user_id: String) {
        val editor = prefs.edit()
        editor.putString(PWD_ID, user_id)
        editor.apply()
    }

    fun fetchUserId(): String? {
        return prefs.getString(PWD_ID, null)

    }

    /**
     * Function to save auth token
     * Function to fetch auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Function to save auth token
     * Function to fetch auth token
     */
    fun saveAuthToken2(token2: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN2, token2)
        editor.apply()
    }

    fun fetchAuthToken2(): String? {
        return prefs.getString(USER_TOKEN2, null)
    }

    /**
     * Function to save phonenumber of user
     *  Function to fetch phonenumber of user
     */
    fun savePhoneNumber(phonenumber: String) {
        val editor = prefs.edit()
        editor.putString(PHONENUMBER, phonenumber)
        editor.apply()
    }

    fun fetchPhoneNumber(): String? {
        return prefs.getString(PHONENUMBER, null)

    }

    /**
     * Function to fetch auth token
     */


}
