package com.example.notificationsample.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.notificationsample.presentation.notification.BasicNotificationService

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            BasicNotificationService.channelId,
            BasicNotificationService.name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Basic test notification for testing"

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
    }
}