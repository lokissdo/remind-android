package com.example.remind.viewmodel

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.remind.R
import com.example.remind.databinding.FragmentJournalItemBinding
import com.example.remind.model.Journal

class JournalAdapter : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    private var journalList: MutableList<Journal> = mutableListOf()

    fun setJournalList(journalList: List<Journal>) {
        this.journalList.clear()
        if (journalList != null) {
            this.journalList.addAll(journalList)
        }
        notifyDataSetChanged()
    }

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
        val journal = journalList[position]
        holder.bind(journal)
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    inner class JournalViewHolder(private val binding: FragmentJournalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: Journal) {
            binding.journal = journal
            binding.executePendingBindings()

            binding.journalDetail.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("selected_journal", journal as Parcelable)
                }
                Navigation.findNavController(binding.root).navigate(R.id.navigation_journal_detail, bundle)
            }
        }
    }
}
