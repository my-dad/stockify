package com.example.stockify.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockify.database.dao.StockDetailsDao
import com.example.stockify.database.dao.UserDao
import com.example.stockify.model.StockDetails
import com.example.stockify.model.User

@Database(entities = [User::class, StockDetails::class], version = 1)
abstract class StockDatabase : RoomDatabase(){
    abstract fun UserDao() : UserDao
    abstract fun StockDetailsDao() : StockDetailsDao

}