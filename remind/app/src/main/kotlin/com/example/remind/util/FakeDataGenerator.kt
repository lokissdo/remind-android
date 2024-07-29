package com.example.remind.util

import com.example.remind.model.Journal
import java.util.Date
import com.example.remind.model.*
import com.github.javafaker.Faker
import java.text.SimpleDateFormat
import java.util.*

object FakeDataGenerator {

    private val faker = Faker()
    private val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())


//    fun generateFakeJournals(count: Int): List<Journal> {
//        val journals = mutableListOf<Journal>()
//        repeat(count) {
//            val journal = Journal(
//                content = faker.lorem().paragraphs(3).joinToString("\n\n"),
//                title = faker.book().title(),
//                updatedAt = Date(),
//                status = if (it % 2 == 0) "public" else "private",
//                images = listOf(
//                    "https://via.placeholder.com/150",
//                    "https://via.placeholder.com/300",
//                    "https://via.placeholder.com/300",
//                    "https://via.placeholder.com/300",
//                    "https://via.placeholder.com/300",
//                    "https://via.placeholder.com/300",
//                    "https://via.placeholder.com/300",
//                    "https://via.placeholder.com/300",
//
//
//                )
//            )
//            journals.add(journal)
//        }
//        return journals
//    }



    private fun generateFakeTodoItem(date: String): TodoItem {
        val startTime = "09:00"
        val endTime = "10:00"

        return TodoItem(
            title = faker.book().title(),
            description = faker.lorem().sentence(),
            date = date,
            status = if (faker.random().nextBoolean()) "Planned" else "Completed",
            category = if (faker.random().nextBoolean()) "FOOD" else "HEALTH",
            startTime = startTime,
            endTime = endTime
        )
    }


    private fun generateFakeDay(date: String): Day {
        val todos = MutableList(faker.random().nextInt(1, 5)) { generateFakeTodoItem(date) }
        return Day(
            date = date,
            todos = todos
        )
    }

    fun generateFakeWeeks(): List<Week> {
        val weeks = mutableListOf<Week>()
        val calendar = Calendar.getInstance()

        repeat(2) { // Generate data for 2 weeks
            val startDate = dateFormat.format(calendar.time)
            calendar.add(Calendar.DATE, 6)
            val endDate = dateFormat.format(calendar.time)

            val days = mutableListOf<Day>()
            calendar.add(Calendar.DATE, -6) // Reset to start of the week
            repeat(7) { // Generate data for each day in the week
                days.add(generateFakeDay(dateFormat.format(calendar.time)))
                calendar.add(Calendar.DATE, 1)
            }

            weeks.add(Week(startDate, endDate, days))
        }

        return weeks
    }
}
