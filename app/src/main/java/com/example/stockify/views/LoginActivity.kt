package com.example.stockify.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.stockify.Application
import com.example.stockify.BaseActivity
import com.example.stockify.database.dao.UserDao
import com.example.stockify.databinding.ActivityLoginBinding
import com.example.stockify.isValidEmail
import com.example.stockify.isValidPassword
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDao = (application as Application).database.UserDao()

        binding.textViewClickToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.materialButtonLogin.setOnClickListener {
            var textEmail = binding.textInputEditTextEmail.text.toString()
            var textPassword = binding.textInputEditTextPassword.text.toString().isEmpty()
            val email = binding.textInputEditTextEmail.text.toString().isValidEmail()
            val password = binding.textInputEditTextPassword.text.toString().isNotEmpty() || binding.textInputEditTextPassword.text.toString()
                .isNotBlank()

            if (email && password){
                CoroutineScope(Dispatchers.IO).launch {
                    if (doesUserExist(binding.textInputEditTextEmail.text.toString())) {
                        val intent = Intent(this@LoginActivity, StockHomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        withContext(Dispatchers.Main){
                            showSnackBar("User doesn't exist")
                        }
                    }
                }
            }else{
                showSnackBar("Validation Error")
            }
        }
    }

    override fun createBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(LayoutInflater.from(this))
    }

    fun showSnackBar(string: String){
        Snackbar.make(binding.root, string, Snackbar.LENGTH_SHORT).show()
    }



    suspend fun doesUserExist(email: String): Boolean {
        val userCountByUsername = userDao.getUserCountByEmail(email)
        val userCountByEmail = userDao.getUserCountByEmail(email)
        return userCountByUsername > 0 || userCountByEmail > 0
    }




}