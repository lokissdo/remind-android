package com.example.remind.util

import com.example.remind.model.Journal
import com.github.javafaker.Faker
import java.util.Date

object FakeDataGenerator {

    private val faker = Faker()

    fun generateFakeJournals(count: Int): List<Journal> {
        val journals = mutableListOf<Journal>()
        repeat(count) {
            val journal = Journal(
                content = faker.lorem().paragraphs(3).joinToString("\n\n"),
                title = faker.book().title(),
                updatedAt = Date(),
                status = if (it % 2 == 0) "public" else "private",
                images = listOf(
                    "https://via.placeholder.com/150",
                    "https://via.placeholder.com/300",
                    "https://via.placeholder.com/300",
                    "https://via.placeholder.com/300",
                    "https://via.placeholder.com/300",
                    "https://via.placeholder.com/300",
                    "https://via.placeholder.com/300",
                    "https://via.placeholder.com/300",


                )
            )
            journals.add(journal)
        }
        return journals
    }
}
