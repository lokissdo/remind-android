package com.example.remind.di

import android.content.Context
import com.example.remind.network.ByteArrayDeserializer
import com.example.remind.network.ByteArraySerializer
import com.example.remind.network.journalservice.JournalService
import com.example.remind.network.authservice.AuthService
import com.example.remind.network.SummaryService
import com.example.remind.repository.JournalRepository
import com.example.remind.repository.SummaryRepository
import com.example.remind.repository.AuthRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    private const val BASE_URL = "http://10.0.2.2:5001"

    private val customGson: Gson = GsonBuilder()
        .registerTypeAdapter(ByteArray::class.java, ByteArraySerializer())
        .registerTypeAdapter(ByteArray::class.java, ByteArrayDeserializer())
        .create()

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .build()
    }

    private fun provideJournalService(): JournalService {
        return provideRetrofit().create(JournalService::class.java)
    }

    private fun provideAuthService(): AuthService {
        return provideRetrofit().create(AuthService::class.java)
    }

    private fun provideSummaryService(): SummaryService {
        return provideRetrofit().create(SummaryService::class.java)
    }

    fun provideJournalRepository(context: Context): JournalRepository {
        return JournalRepository(context, provideJournalService())
    }

    fun provideSummaryRepository(): SummaryRepository {
        return SummaryRepository(provideSummaryService())
    }

    fun provideAuthRepository(): AuthRepository {
        return AuthRepository(provideAuthService())
    }
}