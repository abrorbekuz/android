package com.example.notification_otp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MFetcher : Service() {

    private val baseUrl = "http://192.168.1.106/"
    private lateinit var apiService: ApiService

    private val intervalMillis = 10000L
    private val handler = android.os.Handler()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MFetcher", "Started")
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        startFetchingData()
    }

    private fun startFetchingData() {
        Log.d("MFetcher", "Fetching data started")
        val runnable = object : Runnable {
            override fun run() {
                fetchDataFromRemote()
                handler.postDelayed(this, intervalMillis)
            }
        }

        handler.post(runnable)
    }

    private fun fetchDataFromRemote() {
        val call = apiService.getNotes()
        call.enqueue(object : Callback<NoteResponse> {
            override fun onResponse(call: Call<NoteResponse>, response: Response<NoteResponse>) {
                if (response.isSuccessful) {
                    val noteResponse = response.body()

                    // Check if the response is not null
                    if (noteResponse != null) {
                        val notes = noteResponse.data.values.toList()

                        // Process the fetched notes data as needed
                        for (note in notes) {
                            Log.d("MFetcher", "Subject: ${note.subject}, Body: ${note.body}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<NoteResponse>, t: Throwable) {
                Log.e("MFetcher", "Network request failed", t)
                // Handle network request failure here (e.g., retry logic)
            }
        })
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
