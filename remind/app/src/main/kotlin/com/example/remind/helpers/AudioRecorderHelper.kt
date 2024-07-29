package com.example.remind.helpers

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import java.io.IOException

class AudioRecorderHelper(private val outputFilePath: String) {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    var isRecording = false
        private set

    fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFilePath)
            try {
                prepare()
                start()
                isRecording = true
            } catch (e: IOException) {
                Log.e("AudioRecorderHelper", "prepare() failed")
            }
        }
    }

    fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false
        }
    }

    fun startPlayback(onCompletion: () -> Unit) {
        Log.d("file", outputFilePath)
        mediaPlayer = MediaPlayer().apply {
            setDataSource(outputFilePath)
            prepare()
            start()
            setOnCompletionListener {
                onCompletion()
                stopPlayback()
            }
        }
    }

    fun stopPlayback() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
