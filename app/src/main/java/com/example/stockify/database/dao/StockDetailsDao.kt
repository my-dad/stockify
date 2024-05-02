package com.example.stockify.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockify.model.StockDetails
import com.example.stockify.model.User

@Dao
interface StockDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun buyStock(stock: StockDetails) :Long

    @Query("select * from stockdetails")
    suspend fun getAllBoughtStocks() :List<StockDetails>

    @Delete
    suspend fun deleteBoughtStocks(stock: StockDetails) :Int
}