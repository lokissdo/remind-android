// File: ui/AudioRecorderFragment.kt

package com.example.remind.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.remind.databinding.FragmentAudioRecorderBinding
import com.example.remind.viewmodel.AudioRecorderViewModel

class AudioRecorderFragment : Fragment() {

    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private val viewModel: AudioRecorderViewModel by viewModels()
    private var _binding: FragmentAudioRecorderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioRecorderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toggleRecordingButton.setOnClickListener {
            if (checkAudioPermissions()) {
                Log.d("Check","Have right")
                viewModel.toggleRecording()
            } else {
                Log.d("Check","Not Have right")
                requestAudioPermissions()
            }
        }

        viewModel.isRecording.observe(viewLifecycleOwner) { isRecording ->
            binding.toggleRecordingButton.text = if (isRecording) "Stop Recording" else "Start Recording"
            binding.statusTextView.text = if (isRecording) "Recording..." else "Not Recording"
        }
    }

    private fun requestAudioPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_RECORD_AUDIO_PERMISSION
        )
    }

    private fun checkAudioPermissions(): Boolean {
        Log.d("right for audio", (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED).toString() );
        Log.d("right for audio", ( ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED).toString() );
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                viewModel.toggleRecording()
            } else {
                binding.statusTextView.text = "Permission to record denied"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
