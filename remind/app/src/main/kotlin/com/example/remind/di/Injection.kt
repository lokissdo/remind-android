package com.example.remind.di

import com.example.remind.network.JournalService
import com.example.remind.repository.JournalRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    private const val BASE_URL = "https://chatgpt.com/"

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideJournalService(): JournalService {
        return provideRetrofit().create(JournalService::class.java)
    }

    fun provideJournalRepository(): JournalRepository {
        return JournalRepository(provideJournalService())
    }
}
