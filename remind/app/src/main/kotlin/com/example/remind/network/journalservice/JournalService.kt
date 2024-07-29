package com.example.remind.network.journalservice

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query

interface JournalService {

    @POST("journal/v1/create_journal")
    suspend fun createJournal(
        @Header("Authorization") token: String,
        @Body journal: CreateJournalRequest
    ): Response<CreateJournalResponse>

    @GET("journal/v1/query_journals")
    suspend fun getAllJournals(
        @Header("Authorization") token: String,
        @Query("username") username: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GetAllJournalsResponse>

    @GET("journal/v1/advanced_query_journals")
    suspend fun searchJournals(
        @Header("Authorization") token: String,
        @Query("username") username: String,
        @Query("search") search: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GetJournalsResponse>

    @PATCH("journal/v1/update_journal")
    suspend fun updateJournal(
        @Header("Authorization") token: String,
        @Body request: UpdateJournalRequest
    ) : UpdateJournalResponse
}
