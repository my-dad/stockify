package com.example.stockify.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.stockify.BaseActivity
import com.example.stockify.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.textViewClickToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun createBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(LayoutInflater.from(this))
    }
}