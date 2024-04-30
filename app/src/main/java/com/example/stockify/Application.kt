package com.example.stockify

import android.app.Application
import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.room.Room
import com.example.stockify.database.StockDatabase
import java.util.regex.Pattern

class Application  : Application() {
    lateinit var database : StockDatabase

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            StockDatabase::class.java,
            "my_database"
        ).build()
    }

}