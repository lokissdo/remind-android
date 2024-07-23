package com.example.remind.repository

import com.example.remind.model.SummaryResponse
import com.example.remind.network.SummaryService
import okhttp3.MultipartBody
import retrofit2.Call

class SummaryRepository(private val summaryService: SummaryService) {

    fun getAudioSummary(token: String, audioFile: MultipartBody.Part): Call<SummaryResponse> {
        return summaryService.getAudioSummary(token, audioFile)
    }
}
