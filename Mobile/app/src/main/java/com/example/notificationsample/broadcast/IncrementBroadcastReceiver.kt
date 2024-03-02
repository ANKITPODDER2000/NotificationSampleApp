package com.example.notificationsample.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.notificationsample.notification.BasicNotificationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IncrementBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var basicNotificationService: BasicNotificationService
    companion object {
        private const val TAG = "IncrementBroadcastReceiver"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: is called....")
        basicNotificationService.showIncrementCounterNotification(++BasicNotificationService.incrementCounter)
    }
}