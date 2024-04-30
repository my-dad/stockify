package com.example.stockify.service

import com.example.stockify.Constants.API_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClientProvider {
    val okHttpClient: OkHttpClient by lazy {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set your desired log level
        }
        OkHttpClient.Builder()

            .addInterceptor(loggingInterceptor)
            // Add your custom configurations here
            .build()
    }
}