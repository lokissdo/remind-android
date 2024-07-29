package com.example.remind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.remind.repository.AuthRepository
import com.example.remind.util.SharedPreferencesHelper

class AuthViewModelFactory(
    private val repository: AuthRepository,
    private val sharedPreferenceHelper: SharedPreferencesHelper
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository, sharedPreferenceHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}