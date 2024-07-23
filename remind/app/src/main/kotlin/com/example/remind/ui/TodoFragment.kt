package com.example.remind.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.remind.adapter.DayAdapter
import com.example.remind.adapter.TodoAdapter
import com.example.remind.databinding.FragmentTodoBinding
import com.example.remind.di.Injection
import com.example.remind.model.Day
import com.example.remind.viewmodel.TodoViewModel
import com.example.remind.viewmodel.TodoViewModelFactory

class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TodoViewModel
    private lateinit var dayAdapter: DayAdapter
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Injection.provideJournalRepository()
        viewModel = ViewModelProvider(this, TodoViewModelFactory(repository))
            .get(TodoViewModel::class.java)

        dayAdapter = DayAdapter(emptyList()) { day ->
            onDaySelected(day)
        }
        todoAdapter = TodoAdapter(emptyList())

        binding.dayRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = dayAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        binding.todoRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }

        viewModel.currentWeek.observe(viewLifecycleOwner) { week ->
            week?.let {
                binding.currentWeek.text = "${week.startDate} - ${week.endDate}"
                dayAdapter.setDayList(week.days)

                todoAdapter.setTodoList(emptyList())
                if (week.days.isNotEmpty()) {
                    onDaySelected(week.days[0])
                }
            }
        }

        viewModel.selectedDay.observe(viewLifecycleOwner) { day ->
            day?.let {
                todoAdapter.setTodoList(day.todos)
            }
        }

        binding.previousWeek.setOnClickListener {
            viewModel.showPreviousWeek()

        }

        binding.nextWeek.setOnClickListener {
            viewModel.showNextWeek()
        }
    }

    private fun onDaySelected(day: Day) {
        viewModel.currentWeek.value?.days?.forEach {
            it.isChosen = it == day
        }
        dayAdapter.notifyDataSetChanged()
        viewModel.selectDay(day)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
