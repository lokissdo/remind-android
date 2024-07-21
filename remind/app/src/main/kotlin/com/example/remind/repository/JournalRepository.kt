package com.example.remind.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remind.model.Journal
import com.example.remind.network.JournalService
import com.example.remind.util.FakeDataGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JournalRepository(private val journalService: JournalService) {

    fun getJournals(): LiveData<List<Journal>> {
        val data = MutableLiveData<List<Journal>>()
        journalService.getJournals().enqueue(object : Callback<List<Journal>> {
            override fun onResponse(call: Call<List<Journal>>, response: Response<List<Journal>>) {
                Log.d("response", response.body().toString())
                if (response.body() == null){

                    val fakeJournals = FakeDataGenerator.generateFakeJournals(10)
                    data.value = fakeJournals
                    return
                }
                data.value = response.body()
            }

            override fun onFailure(call: Call<List<Journal>>, t: Throwable) {
                Log.d("error", t.message.toString() + " ")
            }
        })
        return data
    }

    fun getJournal(id: String): LiveData<Journal> {
        val data = MutableLiveData<Journal>()
        journalService.getJournal(id).enqueue(object : Callback<Journal> {
            override fun onResponse(call: Call<Journal>, response: Response<Journal>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<Journal>, t: Throwable) {
                 Log.d("error", t.message.toString() + " ")
            }
        })
        return data
    }

    fun createJournal(journal: Journal): LiveData<Journal> {
        val data = MutableLiveData<Journal>()
        journalService.createJournal(journal).enqueue(object : Callback<Journal> {
            override fun onResponse(call: Call<Journal>, response: Response<Journal>) {

                data.value = response.body()
            }

            override fun onFailure(call: Call<Journal>, t: Throwable) {
                Log.d("error", t.message.toString() + " ")
            }
        })
        return data
    }
}
