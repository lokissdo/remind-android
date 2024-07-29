package com.example.remind

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.remind.databinding.ActivityLoginBinding
import com.example.remind.di.Injection
import com.example.remind.util.SharedPreferencesHelper
import com.example.remind.viewmodel.AuthViewModel
import com.example.remind.viewmodel.AuthViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val repository = Injection.provideAuthRepository()
        val sharedPreferencesHelper = SharedPreferencesHelper(this)
        val viewModelFactory = AuthViewModelFactory(repository, sharedPreferencesHelper)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loginResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signupLink.setOnClickListener {
            viewModel.navigateToSignUp(this)
        }
    }
}