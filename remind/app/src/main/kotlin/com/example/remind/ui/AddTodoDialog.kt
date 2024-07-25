package com.example.remind.ui

import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.remind.R
import com.example.remind.model.TodoItem
import java.text.SimpleDateFormat
import java.util.*

class AddTodoDialog(
    private val context: Context,
    private val selectedDate: String,
    private val callback: (TodoItem) -> Unit
) {

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun show() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_todo, null)
        val startTimeTextView = dialogView.findViewById<TextView>(R.id.textViewStartDate)
        val endTimeTextView = dialogView.findViewById<TextView>(R.id.textViewEndDate)

        var startTime: Calendar? = null
        var endTime: Calendar? = null

        startTimeTextView.setOnClickListener {
            showTimePicker { calendar ->
                startTime = calendar
                startTimeTextView.text = timeFormat.format(calendar.time)
                endTime = Calendar.getInstance().apply { timeInMillis = calendar.timeInMillis + 2 * 60 * 60 * 1000 } // Default 2 hours later
                endTimeTextView.text = timeFormat.format(endTime!!.time)
            }
        }

        endTimeTextView.setOnClickListener {
            showTimePicker { calendar ->
                endTime = calendar
                endTimeTextView.text = timeFormat.format(calendar.time)
            }
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle("Add To-Do")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->
                val title = dialogView.findViewById<EditText>(R.id.editTextTitle).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.editTextDescription).text.toString()
                val category = dialogView.findViewById<Spinner>(R.id.spinnerCategory).selectedItem.toString()

                if (startTime != null && endTime != null) {
                    val todoItem = TodoItem(
                        title = title,
                        description = description,
                        date = selectedDate,
                        status = "Planned",
                        category = category,
                        startTime = timeFormat.format(startTime!!.time),
                        endTime = timeFormat.format(endTime!!.time)
                    )
                    callback(todoItem)
                }
            }
            .create()
        dialog.show()
    }

    private fun showTimePicker(callback: (Calendar) -> Unit) {
        val currentTime = Calendar.getInstance()
        TimePickerDialog(context, { _, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            callback(selectedTime)
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true).show()
    }
}
