package com.example.remind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remind.model.Journal
import com.example.remind.repository.JournalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JournalViewModel(private val repository: JournalRepository) : ViewModel() {

    private val _journalsLiveData = MutableLiveData<List<Journal>>()
    val journalsLiveData: LiveData<List<Journal>> get() = _journalsLiveData

    private val _searchResults = MutableLiveData<List<Journal>>()
    val searchResults: LiveData<List<Journal>> get() = _searchResults

//    suspend fun getAllJournals(token: String, username: String, limit: Int, offset: Int): LiveData<List<Journal>?> {
//        return repository.getAllJournals(token, username, limit, offset)
//    }

    fun getAllJournals(token: String, username: String, limit: Int, offset: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getAllJournals(token, username, limit, offset)
            }
            _journalsLiveData.postValue(result.value)
        }
    }

    suspend fun createJournal(journal: Journal): LiveData<Journal?> {
        return repository.createJournal("", journal)
    }

//    suspend fun searchJournals(token: String, username: String, search: String, limit: Int, offset: Int): LiveData<List<Long>?> {
//        return repository.searchJournals(token, username, search, limit, offset)
//    }

    fun searchJournals(token: String, username: String, query: String, limit: Int, offset: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.searchJournals(token, username, query, limit, offset)
            }
            val journalIds = result.value ?: emptyList()
            val filteredJournals = _journalsLiveData.value?.filter { it.getId() in journalIds } ?: emptyList()
            _searchResults.postValue(filteredJournals)
        }
    }
}
