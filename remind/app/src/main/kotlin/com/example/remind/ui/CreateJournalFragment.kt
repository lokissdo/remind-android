package com.example.remind.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind.adapter.ImageUploadAdapter
import com.example.remind.databinding.FragmentCreateJournalBinding
import com.example.remind.di.Injection
import com.example.remind.viewmodel.AudioRecorderViewModel
import com.example.remind.viewmodel.AudioSummaryViewModel
import com.example.remind.viewmodel.CreateJournalViewModel
import com.example.remind.viewmodel.CreateJournalViewModelFactory

class CreateJournalFragment : Fragment() {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private lateinit var binding: FragmentCreateJournalBinding
    private lateinit var viewModel: CreateJournalViewModel
    private lateinit var audioViewModel: AudioRecorderViewModel
    private lateinit var audioSummaryViewModel: AudioSummaryViewModel
    private val imageAdapter = ImageUploadAdapter()
    private var hasRecordingStarted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Injection.provideJournalRepository(requireContext())
        val viewModelFactory = CreateJournalViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CreateJournalViewModel::class.java]
        audioViewModel = ViewModelProvider(this)[AudioRecorderViewModel::class.java]
        audioSummaryViewModel = ViewModelProvider(this)[AudioSummaryViewModel::class.java]
        binding.viewModel = viewModel
        binding.audioViewModel = audioViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = imageAdapter
        }

        viewModel.images.observe(viewLifecycleOwner) { images ->
            imageAdapter.submitList(images)
        }

        viewModel.journalSaved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                viewModel.saveJournal()
                findNavController().navigateUp()
            }
        }

        viewModel.onAddImageClick.observe(viewLifecycleOwner) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            addImageLauncher.launch(intent)
        }

        setUpAudioRecorder()
    }

    private fun showRecordingStoppedDialog() {
        val dialog = RecordingStoppedDialog(requireContext(),
            onPlayback = { audioViewModel.startPlayback() },
            onSummary = {
                val filePath = "path/to/audio/file.m4a"  // Replace with the actual file path
                audioSummaryViewModel.getAudioSummary(requireContext(), filePath).observe(viewLifecycleOwner) { summaryResponse ->
                    if (summaryResponse != null) {
                        // Handle the summary response
                    } else {
                        // Handle the error
                    }
                }
            },
            onStopPlayback = { audioViewModel.stopPlayback() }
        )
        dialog.show()
    }

    fun setUpAudioRecorder() {
        audioViewModel.isRecording.observe(viewLifecycleOwner) { isRecording ->
//            binding.toggleRecordingButton.text = if (isRecording) "Stop Recording" else "Start Recording"
//            binding.statusTextView.text = if (isRecording) "Recording..." else "Not Recording"
//            binding.playbackButton.visibility = if (isRecording) View.GONE else View.VISIBLE
            if (hasRecordingStarted && !isRecording) {
                showRecordingStoppedDialog()
            }
            if (isRecording) {
                hasRecordingStarted = true
            }
        }

        audioViewModel.isPlaying.observe(viewLifecycleOwner) { isPlaying ->
//            binding.playbackButton.text = if (isPlaying) "Stop Playback" else "Play Recording"
//            if (!isPlaying) {
//                binding.statusTextView.text = "Not Recording"
//            }
        }

        binding.toggleRecordingButton.setOnClickListener {
            if (checkAudioPermissions()) {
                audioViewModel.toggleRecording()
            } else {
                requestAudioPermissions()
            }
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
//        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                audioViewModel.toggleRecording()
            } else {
//                binding.statusTextView.text = "Permission to record denied"
                    Log.d("error", "Permission to record denied")
            }
        }
    }

    private val addImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null && data.data != null) {
                viewModel.addImage(data.data!!)
            }
        }
    }
}

