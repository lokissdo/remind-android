package com.example.remind.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remind.databinding.DayItemBinding
import com.example.remind.model.Day

class DayAdapter(
    private var days: List<Day>,
    private val onDayClick: (Day) -> Unit
) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = DayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position], onDayClick)
    }

    override fun getItemCount() = days.size

    fun setDayList(newDays: List<Day>) {
        days = newDays
        notifyDataSetChanged()
    }

    class DayViewHolder(private val binding: DayItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day, onDayClick: (Day) -> Unit) {
            binding.day = day
            binding.root.setOnClickListener { onDayClick(day) }
            binding.executePendingBindings()
        }
    }
}
