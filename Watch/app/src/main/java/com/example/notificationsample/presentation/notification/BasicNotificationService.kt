package com.example.notificationsample.presentation.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.wear.phone.interactions.notifications.BridgingConfig
import androidx.wear.phone.interactions.notifications.BridgingManager
import com.example.notificationsample.R
import com.example.notificationsample.presentation.MainActivity

class BasicNotificationService(private val context: Context) {
    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    companion object {
        const val channelId = "BasicTestNotification"
        const val name = "Basic notification"
        private const val TAG = "BasicNotificationService"
        private const val DISMISSAL_ID = "MY_DISMISSAL_ID"
        const val BRIDGE_TAG_FOR_DISMISS_NOTIFICATION = "MY_DISMISSAL_NOTIFICATION"
    }

    private fun getBasicNotificationBuilder(
        title: String,
        content: String,
        color: Int,
        autoCancel: Boolean,
        isBigTextNotification: Boolean,
    ): NotificationCompat.Builder {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification_icon_24)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(getPendingIntentForMainActivity())
            .setAutoCancel(autoCancel) // Cancel on clicking Notification
            .setColor(color)
        if (isBigTextNotification) {
            notificationBuilder.setStyle(
                NotificationCompat.BigTextStyle().bigText(content)
            )
        }
        return notificationBuilder
    }

    private fun getPendingIntentForMainActivity(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun postBasicNotificationWithDismissId(title: String, text: String) {
        val notification = getBasicNotificationBuilder(
            title,
            text,
            142536,
            autoCancel = true,
            isBigTextNotification = false
        ).extend(
            NotificationCompat.WearableExtender().setDismissalId(DISMISSAL_ID)
        ).build()

        notificationManager.notify(413, notification)
    }
}