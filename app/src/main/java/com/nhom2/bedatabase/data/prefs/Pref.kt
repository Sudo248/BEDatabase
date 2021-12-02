package com.nhom2.bedatabase.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.nhom2.bedatabase.domain.models.User

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

    suspend fun getCurrentUserName(): String?{
        return prefs.getString("user_name", null)
    }

    suspend fun saveCurrentUserName(user_name: String){
        val editor = prefs.edit()
        editor.putString("user_name", user_name)
        editor.apply()
    }

    suspend fun getCurrentUserPathImage(): String?{
        return prefs.getString("path_image", null)
    }

    suspend fun saveCurrentUserPathImage(path_img: String?){
        val editor = prefs.edit()
        editor.putString("path_image", path_img)
        editor.apply()
    }

    suspend fun getCurrentUser(): User?{
        val user_id = getCurrentUserId()
        val user_name = getCurrentUserName()
        val path_img = getCurrentUserPathImage()

        if(user_name != null) {
            return User(
                user_id = user_id,
                user_name =  user_name,
                path_image = path_img
            )
        }else return null
    }

    suspend fun saveCurrentUser(user: User){
        saveCurrentUserId(user.user_id)
        saveCurrentUserName(user.user_name)
        saveCurrentUserPathImage(user.path_image)
    }

}