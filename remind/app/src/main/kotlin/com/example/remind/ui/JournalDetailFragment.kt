package com.example.remind.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind.adapter.ImageUploadAdapter
import com.example.remind.databinding.FragmentJournalDetailBinding
import com.example.remind.model.Journal
import com.example.remind.viewmodel.JournalDetailViewModel
import com.example.remind.viewmodel.JournalDetailViewModelFactory

class JournalDetailFragment : Fragment() {

    private lateinit var binding: FragmentJournalDetailBinding
    private lateinit var viewModel: JournalDetailViewModel
    private lateinit var viewModelFactory: JournalDetailViewModelFactory
    private val imageAdapter = ImageUploadAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJournalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val journal = arguments?.getParcelable<Journal>("selected_journal") ?: return

        viewModelFactory = JournalDetailViewModelFactory(journal)
        viewModel = ViewModelProvider(this, viewModelFactory).get(JournalDetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.journal.observe(viewLifecycleOwner) { journal ->
            binding.journal = journal
        }

        binding.journalImagesRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = imageAdapter
        }

        imageAdapter.submitList(journal.getImages())

        binding.journalBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        fun newInstance(journal: Journal): JournalDetailFragment {
            val fragment = JournalDetailFragment()
            val args = Bundle().apply {
                putParcelable("selected_journal", journal)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
