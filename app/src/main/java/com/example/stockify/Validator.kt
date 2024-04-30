package com.example.stockify

import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    val emailPattern = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$"
    val pattern = Pattern.compile(emailPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isValidPassword(): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}$"
    val pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}


fun Calendar.previousDate(): String {
    val calendar = this.clone() as Calendar
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(calendar.time)
}