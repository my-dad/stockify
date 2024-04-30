package com.example.stockify.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stockify.Application
import com.example.stockify.BaseActivity
import com.example.stockify.Constants.API_KEY
import com.example.stockify.PreferenceManager
import com.example.stockify.R
import com.example.stockify.databinding.ActivityStockHomeBinding
import com.example.stockify.model.StockDetails
import com.example.stockify.model.StockPayload
import com.example.stockify.previousDate
import com.example.stockify.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class StockHomeActivity : BaseActivity<ActivityStockHomeBinding>() {
    private lateinit var preferenceManager: PreferenceManager

    private val stockList : List<StockDetails> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)
        preferenceManager.setUserLoggedIn(true)

        val call = RetrofitClient.instance.getStockData("Bearer $API_KEY", Calendar.getInstance().previousDate().toString())
        call.enqueue(object : Callback<StockPayload> {
            override fun onResponse(call: Call<StockPayload>, response: Response<StockPayload>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    // Process the data
                } else {
                    // Handle error
                    Toast.makeText(this@StockHomeActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StockPayload>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@StockHomeActivity, "Failed to fetch data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun createBinding(): ActivityStockHomeBinding {
        return ActivityStockHomeBinding.inflate(LayoutInflater.from(this))
    }
}