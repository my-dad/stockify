package com.example.stockify.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stockify.model.StockDetails
import com.example.stockify.model.User

@Dao
interface StockDetailsDao {

    @Insert()
    suspend fun buyStock(stock: StockDetails)

    @Query("select * from stockdetails")
    suspend fun getAllBoughtStocks() :List<StockDetails>
}