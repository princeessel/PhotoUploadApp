package com.example.photouploadapp.utils

import android.content.Context

object PrefManager {

    fun saveUserId(context: Context, userId: Long) {
        val sharedPref = context.getSharedPreferences("loggedInUserId", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putLong("USER_ID", userId)
            putBoolean("IS_LOGIN", true)
            apply()
        }

    }

    fun retrieveUserId(context: Context): Long {
        val sharedPref = context.getSharedPreferences("loggedInUserId", Context.MODE_PRIVATE)
        return sharedPref.getLong("USER_ID", 0L)
    }

    fun isUserLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("loggedInUserId", Context.MODE_PRIVATE)
        return sharedPref.getLong("USER_ID", 0L) != 0L
    }

    fun clearUserId(context: Context) {
        val sharedPref = context.getSharedPreferences("loggedInUserId", Context.MODE_PRIVATE) ?: return
        sharedPref.edit().clear().apply()  //sharedPref.edit().remove("USER_ID").apply()
    }
}
