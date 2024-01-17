package com.example.notificationsample.notification

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import com.example.notificationsample.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MessageNotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager,
) {
    companion object {
        const val channelId = "BasicTestMessageNotification"
        const val name = "Test Message notification"
        private const val TAG = "MessageNotificationService"
    }

    fun showMessageNotification(
        person1: Person,
        person2: Person,
        messageTexts: Array<String>,
        timeStamp: Long,
    ) {
        Log.d(TAG, "showMessageNotification: is called to show message")
        val notificationBuilder =
            getBasicMessageNotificationBuilder(person1, person2, messageTexts, timeStamp)
        val notification = notificationBuilder.build()

        notificationManager.notify(person2.hashCode(), notification)
    }

    private fun getBasicMessageNotificationBuilder(
        person1: Person,
        person2: Person,
        messageTexts: Array<String>,
        timeStamp: Long,
    ): NotificationCompat.Builder {
        val messageStyle = NotificationCompat.MessagingStyle(person1)
        for (messageText in messageTexts) {
            messageStyle.addMessage(messageText, timeStamp, person2)
        }
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification_icon_24)
            .setColor(555887)
            .setContentTitle("Message from UserName")
            .setStyle(messageStyle)

    }
}