package com.example.remind.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind.adapter.ImageUploadAdapter
import com.example.remind.databinding.FragmentCreateJournalBinding
import com.example.remind.di.Injection
import com.example.remind.viewmodel.CreateJournalViewModel
import com.example.remind.viewmodel.CreateJournalViewModelFactory

class CreateJournalFragment : Fragment() {

    private lateinit var binding: FragmentCreateJournalBinding
    private lateinit var viewModel: CreateJournalViewModel
    private val imageAdapter = ImageUploadAdapter()

    private val addImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null && data.data != null) {
                viewModel.addImage(data.data!!)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Injection.provideJournalRepository()
        val viewModelFactory = CreateJournalViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateJournalViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = imageAdapter
        }

        viewModel.images.observe(viewLifecycleOwner, { images ->
            imageAdapter.submitList(images)
        })

        viewModel.journalSaved.observe(viewLifecycleOwner, { saved ->
            if (saved) {
                findNavController().navigateUp()
            }
        })

        viewModel.onAddImageClick.observe(viewLifecycleOwner, {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            addImageLauncher.launch(intent)
        })
    }
}
