package com.example.remind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remind.model.Journal

class JournalDetailViewModel(journal: Journal) : ViewModel() {

    private val _journal = MutableLiveData<Journal>()
    val journal: LiveData<Journal>
        get() = _journal

    init {
        _journal.value = journal
    }
}
