package com.example.notificationsample.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.notificationsample.MainActivity
import com.example.notificationsample.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BasicNotificationService @Inject constructor(@ApplicationContext val context: Context) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    var countForShowBasicNotification = 1
        private set

    private var newNotificationId = 2

    companion object {
        const val notificationId = 1
        const val channelId = "BasicTestNotification"
        const val name = "Basic notification"
        private const val TAG = "BasicNotificationService"
    }

    fun showBasicNotification(
        title: String,
        content: String,
        isAutoCancel: Boolean = false,
        isBigTextNotification: Boolean = false,
    ) {
        Log.d(TAG, "showBasicNotification: is called..")
        val notification = getBasicNotification(title, content, 11256987, isAutoCancel, isBigTextNotification)
        notificationManager.notify(notificationId, notification)
        countForShowBasicNotification++
    }

    fun showNewNotification(
        title: String,
        content: String,
        isAutoCancel: Boolean = false,
        isBigTextNotification: Boolean = false,
    ) {
        val notification = getBasicNotification(title, content, 995512, isAutoCancel, isBigTextNotification)
        notificationManager.notify(newNotificationId, notification)
        newNotificationId++
    }

    private fun getBasicNotification(
        title: String,
        content: String,
        color: Int,
        autoCancel: Boolean,
        isBigTextNotification: Boolean,
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification_icon_24)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(getPendingIntentForMainActivity(1))
            .setAutoCancel(autoCancel) // Cancel on clicking Notification
            .setColor(color)
        if(isBigTextNotification) {
            notificationBuilder.setStyle(
                NotificationCompat.BigTextStyle().bigText(content)
            )
        }
        return notificationBuilder.build()
    }

    private fun getPendingIntentForMainActivity(requestCode: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}