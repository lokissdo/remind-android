package com.example.remind.network

import com.example.remind.model.Journal
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.Call

interface JournalService {
    @GET("journals")
    fun getJournals(): Call<List<Journal>>

    @GET("journals/{id}")
    fun getJournal(@Path("id") id: String): Call<Journal>

    @POST("journals")
    fun createJournal(@Body journal: Journal): Call<Journal>
}
