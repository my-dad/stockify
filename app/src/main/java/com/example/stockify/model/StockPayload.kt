package com.example.stockify.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StockPayload(
    val adjusted: Boolean,
    val queryCount: Long,
    val results: List<StockDetails>,
)


@Entity(tableName = "StockDetails")
data class StockDetails(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerializedName("T") val symbol: String,
    @SerializedName("c") val closePrice: Double,
    @SerializedName("h") val highestPrice: Double,
    @SerializedName("l") val lowestPrice: Double,
    @SerializedName("n") val numberOfTransactions: Long,
    @SerializedName("o") val openPrice: Double,
    @SerializedName("t") val timestamp: Long,
    @SerializedName("v") val volumeOfSymbol: Long,
    @SerializedName("vw") val weightedAveragePrice: Double,
)
