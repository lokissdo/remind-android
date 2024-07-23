package com.example.remind.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.example.remind.databinding.DialogRecordingStoppedBinding

class RecordingStoppedDialog(context: Context, private val onPlayback: () -> Unit,private val onStopPlayback: () -> Unit, private val onSummary: () -> Unit) : Dialog(context) {

    private lateinit var binding: DialogRecordingStoppedBinding
    private var isPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRecordingStoppedBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.closeButton.setOnClickListener {
            dismiss()
        }
        setCancelable(false)
        binding.playbackButton.setOnClickListener {
            if (isPlaying) {
                onStopPlayback()
                binding.playbackButton.text = "Play Recording"
                isPlaying = false
            } else {
                isPlaying = true
                binding.playbackButton.text = "Stop Playback"
                onPlayback()
            }
        }

        binding.summaryButton.setOnClickListener {
            onSummary()
            dismiss()
        }
    }
}
