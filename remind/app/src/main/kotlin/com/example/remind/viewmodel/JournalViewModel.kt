package com.example.remind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.remind.model.Journal
import com.example.remind.repository.JournalRepository

class JournalViewModel(private val repository: JournalRepository) : ViewModel() {

    fun getJournals(): LiveData<List<Journal>> {
        return repository.getJournals()
    }

    fun getJournal(id: String): LiveData<Journal> {
        return repository.getJournal(id)
    }

    fun createJournal(journal: Journal): LiveData<Journal> {
        return repository.createJournal(journal)
    }
}
