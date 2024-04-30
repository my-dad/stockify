package com.example.stockify.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stockify.Application
import com.example.stockify.BaseActivity
import com.example.stockify.R
import com.example.stockify.database.dao.UserDao
import com.example.stockify.databinding.ActivityRegistrationBinding
import com.example.stockify.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationActivity : BaseActivity<ActivityRegistrationBinding>() {
    private lateinit var userDao: UserDao
    var name : String = ""
    var email : String = ""
    var password : String = ""
    var confirmPassword : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDao = (application as Application).database.UserDao()

        binding.materialButtonRegistration.setOnClickListener {
            if (submitForm()){
                CoroutineScope(Dispatchers.IO).launch {
                    userDao.insertUser(User(name, email, password))
                }
                moveToNextActivity()
            }
        }
    }

    private fun submitForm() : Boolean{
        name = binding.textInputEditTextName.text.toString()
        email = binding.textInputEditTextEmail.text.toString()
        password = binding.textInputEditTextPassword.text.toString()
        confirmPassword = binding.textInputEditTextConfirmPassword.text.toString()

        return if (name.isNotEmpty()){
            true
        }else if (email.isNotEmpty()){
            true
        }else if (password.isNotEmpty()){
            true
        }else if (confirmPassword.isNotEmpty()){
            true
        }else if (password == confirmPassword){
            true
        }else{
            false
        }

    }

    fun moveToNextActivity(){
        val intent = Intent(this@RegistrationActivity, StockHomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun createBinding(): ActivityRegistrationBinding {
        return ActivityRegistrationBinding.inflate(LayoutInflater.from(this))
    }
}