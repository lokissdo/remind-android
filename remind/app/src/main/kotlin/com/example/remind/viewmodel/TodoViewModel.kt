package com.example.remind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remind.model.Day
import com.example.remind.model.TodoItem
import com.example.remind.model.Week
import com.example.remind.repository.JournalRepository

class TodoViewModel(private val repository: JournalRepository) : ViewModel() {

    private val _currentWeek = MutableLiveData<Week>()
    val currentWeek: LiveData<Week> get() = _currentWeek

    private val _selectedDay = MutableLiveData<Day>()
    val selectedDay: LiveData<Day> get() = _selectedDay

    private val weeks = mutableListOf<Week>()
    private var currentWeekIndex = 0

    init {
        loadWeeks()
        _currentWeek.value = weeks[currentWeekIndex]
    }

    private fun loadWeeks() {
        // Load or generate the weeks data
        weeks.add(
            Week("Jan 29", "Feb 05", listOf(
                Day("Mon, Jan 29", listOf(TodoItem("Title1", "Description1"), TodoItem("Title2", "Description2"))),
                Day("Tue, Jan 30", listOf(TodoItem("Title3", "Description3"))),
                // Add other days
            ))
        )
        weeks.add(
            Week("Feb 05", "Feb 12", listOf(
                Day("Mon, Feb 05", listOf(TodoItem("Title4", "Description4"))),
                Day("Tue, Feb 06", listOf(TodoItem("Title5", "Description5"))),
                // Add other days
            ))
        )
        // Add more weeks
    }

    fun showPreviousWeek() {
        if (currentWeekIndex > 0) {
            currentWeekIndex--
            _currentWeek.value = weeks[currentWeekIndex]
        }
    }

    fun showNextWeek() {
        if (currentWeekIndex < weeks.size - 1) {
            currentWeekIndex++
            _currentWeek.value = weeks[currentWeekIndex]
        }
    }

    fun selectDay(day: Day) {
        _selectedDay.value = day
    }
}
