// File: viewmodel/AudioRecorderViewModel.kt

package com.example.remind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remind.helpers.AudioRecorderHelper

class AudioRecorderViewModel(application: Application) : AndroidViewModel(application) {

    private val _isRecording = MutableLiveData(false)
    val isRecording: LiveData<Boolean> get() = _isRecording

    private val outputFilePath = "${application.externalCacheDir?.absolutePath}/audiorecordtest.mp4"
    private val audioRecorderHelper = AudioRecorderHelper(outputFilePath)
    fun toggleRecording() {
        if (_isRecording.value == true) {
            audioRecorderHelper.stopRecording()
            _isRecording.value = false
        } else {
            audioRecorderHelper.startRecording()
            _isRecording.value = true
        }
    }
}
