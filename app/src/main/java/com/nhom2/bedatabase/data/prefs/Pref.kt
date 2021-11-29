package com.nhom2.bedatabase.data.prefs

import android.content.Context
import android.content.SharedPreferences

class Pref(context: Context) {
    private val PREF_NAME = "MyPrefs"

    private val prefs: SharedPreferences by lazy{
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    suspend fun getToken(): String?{
        return prefs.getString("token",null)
    }

    suspend fun saveToken(token: String?){
        val editor = prefs.edit()
        editor.putString("token", token)
        editor.apply()
    }

    suspend fun getCurrentUserId(): Int{
        return prefs.getInt("user_id",-1)
    }

    suspend fun saveCurrentUserId(user_id: Int){
        val editor = prefs.edit()
        editor.putInt("user_id", user_id)
        editor.apply()
    }

}