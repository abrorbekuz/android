package com.example.notification_otp

data class NoteResponse(
    val data: Map<String, NoteItem>,
    val status: String
)