package com.example.remind.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        searchView = binding.searchViewAll

        val repository = Injection.provideJournalRepository(requireContext())
        viewModel = ViewModelProvider(this, JournalViewModelFactory(repository))[JournalViewModel::class.java]

//        CoroutineScope(Dispatchers.Main).launch {
//            val journalsLiveData = withContext(Dispatchers.IO) {
//                viewModel.getAllJournals("", "JohnDoe", 5, 0)
//            }
//
//            journalsLiveData.observe(viewLifecycleOwner, Observer { journals ->
//                if (journals.isNullOrEmpty()) {
//                    Log.d("error", "No journals found")
//                } else {
//                    Log.d("info", "Journals found: ${journals.size}")
//                    myJournalAdapter.setJournalList(journals)
//                    myJournalAdapter.notifyDataSetChanged()
//                }
//            })
//        }
        viewModel.getAllJournals("", "JohnDoe", 5, 0)

        viewModel.journalsLiveData.observe(viewLifecycleOwner, Observer { journals ->
            if (journals.isNullOrEmpty()) {
                Log.d("error", "No journals found")
            } else {
                Log.d("info", "Journals found: ${journals.size}")
                myJournalAdapter.setJournalList(journals)
                myJournalAdapter.notifyDataSetChanged()
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchJournals("", "JohnDoe", it, 1, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewModel.searchResults.observe(viewLifecycleOwner, Observer { journals ->
            if (journals.isNullOrEmpty()) {
                Log.d("error", "No search results found")
            } else {
                Log.d("info", "Search results found: ${journals.size}")
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
