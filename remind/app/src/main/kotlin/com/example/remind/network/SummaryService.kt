package com.example.remind.network

import com.example.remind.model.SummaryResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SummaryService {
    @Multipart
    @POST("audio/summary")
    fun getAudioSummary(
        @Header("Authorization") token: String,
        @Part audioFile: MultipartBody.Part
    ): Call<SummaryResponse>
}
