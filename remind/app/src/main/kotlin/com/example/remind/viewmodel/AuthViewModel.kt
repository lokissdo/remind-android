package com.example.remind.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remind.LoginActivity
import com.example.remind.SignUpActivity
import com.example.remind.network.authservice.CreateUserRequest
import com.example.remind.repository.AuthRepository

import com.example.remind.network.authservice.LoginUserRequest
import com.example.remind.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class AuthViewModel (
    private val authRepository: AuthRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {
    val username = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginResult = MutableLiveData<Boolean>()
    val signUpResult = MutableLiveData<Boolean>()

    fun login() {
        val user = LoginUserRequest(username.value ?: "", password.value ?: "")
        viewModelScope.launch {
            val response = authRepository.loginUser(user)
            if (response.isSuccessful) {
                response.body()?.let {
                    sharedPreferencesHelper.saveAccessToken(it.accessToken)
                    sharedPreferencesHelper.saveUsername(it.user.username)
                    loginResult.postValue(true)
                }
            } else {
                loginResult.postValue(false)
            }
        }
    }

    fun signUp() {
        val user = CreateUserRequest(username.value ?: "", fullName.value ?: "", email.value ?: "", password.value ?: "")
        viewModelScope.launch {
            try {
                authRepository.createUser(user)
                signUpResult.postValue(true)
            } catch (e: Exception) {
                signUpResult.postValue(false)
            }
        }
    }

    fun navigateToSignUp(context: Context) {
        val intent = Intent(context, SignUpActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToLogin(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}