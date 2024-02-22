package com.example.notification_otp

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {

    private val channelId = "ForegroundServiceChannel"
    private val notificationId = 1

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notification = createNotification()
        startForeground(notificationId, notification)

        // Start your service's background work here, if applicable.

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Foreground Service Channel", // User-friendly channel name
                NotificationManager.IMPORTANCE_LOW // Adjust importance as needed
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        // Customize notification content, actions, and icon based on your app's requirements.
        // Use dynamic content and actions that reflect your app's functionality.
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, StopForegroundServiceReceiver::class.java)
        stopIntent.action = "STOP_SERVICE"
        val stopPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Foreground Service")
            .setContentText("Customize this content dynamically")
            .setSmallIcon(R.drawable.ic_service_notification) // Use an appropriate icon
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Adjust priority as needed
            .addAction(
                R.drawable.ic_stop,
                "Stop",
                stopPendingIntent
            )
            .setOngoing(true)
            .setNotificationSilent()
            .setContentIntent(pendingIntent)
            .build()
    }
}
