package com.example.notificationsample.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.example.notificationsample.ChatDB
import com.example.notificationsample.R
import com.example.notificationsample.broadcast.MessageReceiveReceiver
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
        timeStamp: Long,
    ) {
        Log.d(TAG, "showMessageNotification: is called to show message person1 : ${person1.name} & person2 : ${person2.name}")

        val KEY_TEXT_REPLY = "key_text_reply"
        val replyLabel = "Enter the reply"
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        val replyPendingIntent =
            PendingIntent.getBroadcast(
                context,
                10,
                Intent(context, MessageReceiveReceiver::class.java),
                PendingIntent.FLAG_MUTABLE
            )

        val action = NotificationCompat.Action.Builder(
            R.drawable.calculate_24,
            "Give Reply..", replyPendingIntent
        ).addRemoteInput(remoteInput).build()

        val chats = ChatDB.getChat(person2)

        val notificationBuilder =
            getBasicMessageNotificationBuilder(person1, chats, timeStamp)
        notificationBuilder.addAction(action)
        val notification = notificationBuilder.build()

        notificationManager.notify(5555, notification)
    }

    private fun getBasicMessageNotificationBuilder(
        person1: Person,
        messageTexts: ArrayList<Pair<Person, String>>?,
        timeStamp: Long,
    ): NotificationCompat.Builder {
        val messageStyle = NotificationCompat.MessagingStyle(person1)
        if (!messageTexts.isNullOrEmpty()) {
            for (chat in messageTexts) {
                messageStyle.addMessage(chat.second, timeStamp, chat.first)
            }
        } else {
            Log.e(TAG, "getBasicMessageNotificationBuilder: Empty chats...")
        }
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification_icon_24)
            .setColor(555887)
            .setContentTitle("Message from UserName")
            .setStyle(messageStyle)

    }
}