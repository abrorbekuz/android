package com.example.notification_otp

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("notes/")
    fun getNotes(): Call<NoteResponse>
}
