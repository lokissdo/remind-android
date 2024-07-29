package com.example.remind.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remind.R
import com.example.remind.databinding.FragmentJournalItemBinding
import com.example.remind.model.Journal
import kotlin.math.log

class JournalAdapter(journalList: List<Journal>) :
    RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    private val journalListData: MutableList<Journal> = journalList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val binding: FragmentJournalItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fragment_journal_item,
            parent,
            false
        )
        return JournalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val journal = journalListData[position]
        holder.bind(journal)
    }

    override fun getItemCount(): Int {
        return journalListData.size
    }

    fun setJournalList(journals: List<Journal>) {
        Log.d("journals", journals.size.toString())
        journalListData.clear()
        journalListData.addAll(journals)
        Log.d("journalListData", journalListData.size.toString())
        notifyDataSetChanged()
    }

    inner class JournalViewHolder(private val binding: FragmentJournalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: Journal) {
            Log.d("binding-journal", journal.toString())
            binding.journal = journal
            binding.executePendingBindings()

            // Set up the image RecyclerView
            val recyclerView = binding.journalImagesRecyclerview
            val images = journal.getImagesString() ?: emptyList()
            Log.d("images", images.toString())
            val imageAdapter = ImageAdapter(images)
            recyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = imageAdapter
//            binding.journalDetail.setOnClickListener { v ->
//                val bundle = Bundle()
//                bundle.putParcelable("selected_journal", journal)
//                findNavController(binding.root).navigate(
//                    R.id.navigation_journal_detail,
//                    bundle
//                )
//            }

            // Set click listener on the CardView
            binding.root.findViewById<CardView>(R.id.journal_card_view).setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("selected_journal", journal)
                findNavController(binding.root).navigate(
                    R.id.navigation_journal_detail,
                    bundle
                )
            }
        }
    }
}
