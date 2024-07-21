package com.example.remind.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remind.model.Journal
import com.example.remind.repository.JournalRepository
import kotlinx.coroutines.launch
import java.util.Date

class CreateJournalViewModel(private val journalRepository: JournalRepository) : ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    private val _images = MutableLiveData<List<Uri>>(emptyList())
    val images: LiveData<List<Uri>> get() = _images

    private val _journalSaved = MutableLiveData<Boolean>()
    val journalSaved: LiveData<Boolean> get() = _journalSaved

    private val _onAddImageClick = MutableLiveData<Unit>()
    val onAddImageClick: LiveData<Unit> get() = _onAddImageClick

    fun addImage(uri: Uri) {
        val updatedImages = _images.value.orEmpty() + uri
        _images.value = updatedImages
    }

    fun saveJournal() {
        val newJournal = Journal(
            title = title.value ?: "",
            content = content.value ?: "",
            images = _images.value.orEmpty().map { it.toString() },
            status = "private",
            updatedAt = Date()
        )
        viewModelScope.launch {
            try {
                journalRepository.createJournal(newJournal)
                _journalSaved.value = true
            } catch (e: Exception) {
                _journalSaved.value = false
            }
        }
    }

    fun onAddImageClick() {
        _onAddImageClick.value = Unit
    }
}
