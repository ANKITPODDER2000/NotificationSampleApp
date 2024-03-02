package com.example.notificationsample.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import com.example.notificationsample.ChatDB
import com.example.notificationsample.R
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
            val message = getMessageFromIntent()
            val currentUser = getRandomUser("Test",
                context?.let { it1 -> IconCompat.createWithResource(it1, R.drawable.user2) })
            val currentUser2 = getRandomUser("Ankit")

            ChatDB.storeNewChat(currentUser2, Pair(currentUser, message))
            messageNotificationService.showMessageNotification(
                currentUser,
                currentUser2,
                System.currentTimeMillis()
            )
        }
    }

    private fun getMessageFromIntent(): String {
        // return RemoteInput.getResultsFromIntent(intent)?.getCharSequence("KEY_TEXT_REPLY").toString()
        return "Default Test message...,."
    }

    private fun getRandomUser(userName: String, icon: IconCompat? = null): Person {
        return Person.Builder()
            .setIcon(icon)
            .setName(userName)
            .build()
    }
}