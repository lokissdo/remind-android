package com.example.remind.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.remind.di.Injection
import com.example.remind.helpers.TokenManager
import com.example.remind.model.SummaryResponse
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

class AudioSummaryViewModel : ViewModel() {
    private val summaryRepository = Injection.provideSummaryRepository()

    fun getAudioSummary(context: Context, filePath: String): LiveData<SummaryResponse?> = liveData(Dispatchers.IO) {
        val token = TokenManager.getToken(context)
        if (token != null) {
            val file = File(filePath)
            val requestBody = RequestBody.create(MediaType.parse("audio/m4a"), file)
            val multipartBody = MultipartBody.Part.createFormData("audioFile", file.name, requestBody)

            // Try to get the summary and emit the result
            val response: Response<SummaryResponse>
            try {
                response = summaryRepository.getAudioSummary("Bearer $token", multipartBody).execute()
                if (response.isSuccessful) {
                    emit(response.body())
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                emit(null)
            }
        } else {
            emit(null)
        }
    }
}
