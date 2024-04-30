package com.example.stockify.service

import com.example.stockify.Constants.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {



    val instance: StocksApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(OkHttpClientProvider.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(StocksApi::class.java)
    }
}