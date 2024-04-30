package com.example.stockify

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager (context: Context){

    private val IS_LOGGED_IN : String = "LoggedIn"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("stockify", Context.MODE_PRIVATE)

    fun saveString(data: String) {
        val editor = sharedPreferences.edit()
        editor.putString("savedText", data)
        editor.apply()
    }

    fun setUserLoggedIn(data: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, data)
        editor.apply()
    }

    fun getUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }


}