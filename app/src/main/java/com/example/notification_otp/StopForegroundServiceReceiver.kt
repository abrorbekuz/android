package com.example.notification_otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StopForegroundServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "STOP_SERVICE") {
            context?.stopService(Intent(context, ForegroundService::class.java))
        }
    }
}
