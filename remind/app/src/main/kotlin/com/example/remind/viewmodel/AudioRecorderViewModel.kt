package com.example.remind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remind.helpers.AudioRecorderHelper

class AudioRecorderViewModel(application: Application) : AndroidViewModel(application) {

    private val _isRecording = MutableLiveData(false)
    val isRecording: LiveData<Boolean> get() = _isRecording

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> get() = _isPlaying

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

    fun startPlayback() {
        audioRecorderHelper.startPlayback {
            _isPlaying.value = false
        }
        _isPlaying.value = true
    }

    fun stopPlayback() {
        audioRecorderHelper.stopPlayback()
        _isPlaying.value = false
    }
}
