package com.example.remind.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.remind.databinding.FragmentJournalBinding
import com.example.remind.di.Injection
import com.example.remind.viewmodel.JournalViewModel
import com.example.remind.adapter.JournalAdapter
import com.example.remind.viewmodel.JournalViewModelFactory

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    private lateinit var journalRecyclerView: RecyclerView
    private lateinit var viewModel: JournalViewModel
    private lateinit var myJournalAdapter: JournalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        journalRecyclerView = binding.journalRecycler
        journalRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        myJournalAdapter = JournalAdapter(emptyList())
        journalRecyclerView.adapter = myJournalAdapter

        val repository = Injection.provideJournalRepository()
        viewModel = ViewModelProvider(this, JournalViewModelFactory(repository))
            .get(JournalViewModel::class.java)


        viewModel.getJournals().observe(viewLifecycleOwner, Observer { journals ->
            if (journals == null || journals.isEmpty()) {
                Log.d("error", "No journals found")

            } else {
                Log.d("info", "Journals found: ${journals.size}")
                myJournalAdapter.setJournalList(journals)
                myJournalAdapter.notifyDataSetChanged()
            }

        })

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(journalRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
