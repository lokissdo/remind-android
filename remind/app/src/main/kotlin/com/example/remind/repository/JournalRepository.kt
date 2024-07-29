package com.example.remind.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remind.model.Journal
import com.example.remind.network.journalservice.CreateJournalRequest
import com.example.remind.network.journalservice.CreateJournalResponse
import com.example.remind.network.journalservice.GetAllJournalsResponse
import com.example.remind.network.journalservice.GetJournalsResponse
import com.example.remind.network.journalservice.JournalService
import com.example.remind.network.journalservice.logRequestPayload
import com.example.remind.network.journalservice.toJournal
import com.example.remind.util.saveByteArrayToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class JournalRepository(
    private val context: Context,
    private val journalService: JournalService) {

    suspend fun createJournal(token: String, journal: Journal): LiveData<Journal?> {
        val result = MutableLiveData<Journal?>()
        withContext(Dispatchers.IO) {
            try {
                val createJournalRequest = CreateJournalRequest(
                    username = journal.getUsername(),
                    title = journal.getTitle(),
                    content = journal.getContent(),
                    status = journal.getStatus(),
                    images = journal.getBytesOfImages(context) ?: emptyList(),
                    audios = journal.getAudios() ?: emptyList()
                )

                logRequestPayload(createJournalRequest)

                val response: Response<CreateJournalResponse> = journalService.createJournal(token, createJournalRequest)

                // Log the raw response body
                val rawResponseBody = response.errorBody()?.string() ?: response.body().toString()
                Log.d("JournalRepository", "Raw Response Body: $rawResponseBody")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("JournalRepository", "Mapped Response Body: $responseBody")

                    val journalResponse = responseBody?.toJournal(context)
                    result.postValue(journalResponse)
                } else {
                    Log.e("JournalRepository", "Error: ${response.errorBody()?.string()}")
                    result.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result.postValue(null)
            }
        }
        return result
    }

    suspend fun getAllJournals(token: String, username: String, limit: Int, offset: Int): LiveData<List<Journal>?> {
        val result = MutableLiveData<List<Journal>?>()
        withContext(Dispatchers.IO) {
            try {
                val response: Response<GetAllJournalsResponse> = journalService.getAllJournals(token, username, limit, offset)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val journals = responseBody?.journals?.map { journalDetails ->
                        Journal(
                            id = journalDetails.id,
                            username = journalDetails.username,
                            title = journalDetails.title,
                            content = journalDetails.content,
                            status = journalDetails.status,
                            updatedAt = journalDetails.updatedAt,
                            images = responseBody.images.filter { it.journalId == journalDetails.id }.map { image ->
                                saveByteArrayToFile(context, image.content, "${image.id}.png")
                            },
                            audios = null
                        )
                    }
                    result.postValue(journals)
                } else {
                    Log.e("JournalRepository", "Error: ${response.errorBody()?.string()}")
                    result.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result.postValue(null)
            }
        }
        return result
    }

    suspend fun searchJournals(token: String, username: String, search: String, limit: Int, offset: Int) : LiveData<List<Long>?> {
        val result = MutableLiveData<List<Long>?>()
        withContext(Dispatchers.IO) {
            try {
                val response: Response<GetJournalsResponse> = journalService.searchJournals(token, username, search, limit, offset)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("JournalRepository", "Response Body: $responseBody")
                    result.postValue(responseBody?.journalId)
                } else {
                    Log.e("JournalRepository", "Error: ${response.errorBody()?.string()}")
                    result.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result.postValue(null)
            }
        }
        return result
    }
}