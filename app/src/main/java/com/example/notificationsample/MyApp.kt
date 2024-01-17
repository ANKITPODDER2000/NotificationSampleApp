package com.example.notificationsample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.notificationsample.notification.BasicNotificationService
import com.example.notificationsample.notification.MessageNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(BasicNotificationService.channelId, BasicNotificationService.name)
        createNotificationChannel(MessageNotificationService.channelId, MessageNotificationService.name)
    }

    private fun createNotificationChannel(channelId: String, name: String) {
        val notificationChannel = NotificationChannel(
            channelId,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = "Basic test notification for testing"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}