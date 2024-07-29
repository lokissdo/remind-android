package com.example.remind

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.remind.databinding.ActivitySignupBinding
import com.example.remind.di.Injection
import com.example.remind.util.SharedPreferencesHelper
import com.example.remind.viewmodel.AuthViewModel
import com.example.remind.viewmodel.AuthViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val repository = Injection.provideAuthRepository()
        val sharedPreferencesHelper = SharedPreferencesHelper(this)
        val viewModelFactory = AuthViewModelFactory(repository, sharedPreferencesHelper)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        super.onCreate(savedInstanceState)
        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.signUpResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Sign-up failed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginLink.setOnClickListener {
            viewModel.navigateToLogin(this)
        }
    }
}