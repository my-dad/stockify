package com.example.stockify.views

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stockify.BaseActivity
import com.example.stockify.R
import com.example.stockify.databinding.ActivityStockHomeBinding

class StockHomeActivity : BaseActivity<ActivityStockHomeBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun createBinding(): ActivityStockHomeBinding {
        return ActivityStockHomeBinding.inflate(LayoutInflater.from(this))
    }
}