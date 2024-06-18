// File: helpers/SpeechRecognizerHelper.kt

package com.example.remind.helpers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.TextView

object SpeechRecognizerHelper {

    private const val SPEECH_RECOGNIZER_TIMEOUT = 5 * 60 * 1000 // 5 minutes in milliseconds
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var isListening = false
    private var recognizedTextBuilder = StringBuilder()

    fun createSpeechRecognizer(context: Context, textView: TextView): Pair<SpeechRecognizer, Intent> {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        val intentRecognizer = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        val recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                isListening = true
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                isListening = false
                restartListening(speechRecognizer, intentRecognizer)
            }

            override fun onError(error: Int) {
                textView.text = "Error: $error"
                Log.d("Error for listening", error.toString())
                isListening = false
                //restartListening(speechRecognizer, intentRecognizer)
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    val recognizedText = matches.joinToString(separator = "\n")
                    recognizedTextBuilder.append(recognizedText).append("\n")
                    textView.text = recognizedTextBuilder.toString()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    val partialText = matches.joinToString(separator = "\n")
                    textView.text = "${recognizedTextBuilder.toString()}\n$partialText"
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer.setRecognitionListener(recognitionListener)
        startListening(speechRecognizer, intentRecognizer)

        return Pair(speechRecognizer, intentRecognizer)
    }

    private fun startListening(speechRecognizer: SpeechRecognizer, intentRecognizer: Intent) {
        if (!isListening) {
            handler.postDelayed({
                if (!isListening) {
                    speechRecognizer.startListening(intentRecognizer)
                }
            }, SPEECH_RECOGNIZER_TIMEOUT.toLong())
        }
    }

    private fun restartListening(speechRecognizer: SpeechRecognizer, intentRecognizer: Intent) {
        handler.postDelayed({
            if (!isListening) {
                speechRecognizer.startListening(intentRecognizer)
            }
        }, 100) // Short delay before restarting listening
    }
}
