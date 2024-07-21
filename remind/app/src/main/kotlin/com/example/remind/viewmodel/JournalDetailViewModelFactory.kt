package com.example.remind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.remind.model.Journal

class JournalDetailViewModelFactory(private val journal: Journal) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalDetailViewModel::class.java)) {
            return JournalDetailViewModel(journal) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
