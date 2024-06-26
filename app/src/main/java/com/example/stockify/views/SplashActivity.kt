package com.example.stockify.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stockify.BaseActivity
import com.example.stockify.PreferenceManager
import com.example.stockify.R
import com.example.stockify.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (!preferenceManager.getUserLoggedIn()){
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@SplashActivity, StockHomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1500)

    }

    override fun createBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this))
    }
}