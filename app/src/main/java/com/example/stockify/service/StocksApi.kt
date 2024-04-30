package com.example.stockify.service


import com.example.stockify.Constants.API_BASE_URL
import com.example.stockify.Constants.API_GROUPED_DATA
import com.example.stockify.model.StockPayload
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface StocksApi {

    @GET(API_GROUPED_DATA)
    fun getStockData(@Header("Authorization") string: String, @Path("date") date: String): Call<StockPayload>
}