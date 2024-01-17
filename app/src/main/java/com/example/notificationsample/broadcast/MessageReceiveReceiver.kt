package com.example.notificationsample.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.example.notificationsample.ChatDB
import com.example.notificationsample.notification.MessageNotificationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MessageReceiveReceiver : BroadcastReceiver() {
    @Inject
    lateinit var messageNotificationService: MessageNotificationService

    companion object {
        private const val TAG = "MessageReceiveReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: is called")
        intent?.let {
            val message = getMessageFromIntent(it).toString()
            val currentUser = getRandomUser("Test")
            val currentUser2 = getRandomUser("Ankit")

            ChatDB.storeNewChat(currentUser2, Pair(currentUser, message))
            messageNotificationService.showMessageNotification(
                currentUser,
                currentUser2,
                System.currentTimeMillis()
            )
        }
    }

    private fun getMessageFromIntent(intent: Intent): String {
        // return RemoteInput.getResultsFromIntent(intent)?.getCharSequence("KEY_TEXT_REPLY").toString()
        return "Default Test message...,."
    }

    private fun getRandomUser(userName: String): Person {
        return Person.Builder()
            .setIcon(null)
            .setName(userName)
            .build()
    }
}