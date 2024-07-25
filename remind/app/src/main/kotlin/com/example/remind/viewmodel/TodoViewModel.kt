package com.example.remind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remind.model.Day
import com.example.remind.model.TodoItem
import com.example.remind.model.Week
import com.example.remind.repository.JournalRepository
import com.example.remind.util.FakeDataGenerator

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
        val fakerWeeks =FakeDataGenerator.generateFakeWeeks()
        weeks.addAll(fakerWeeks)
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
    fun addTodoItem(todoItem: TodoItem) {
        _selectedDay.value?.todos?.add(todoItem)
        _selectedDay.postValue(_selectedDay.value) // Update the LiveData to notify observers
    }
}
