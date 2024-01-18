package com.example.notificationsample.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.notificationsample.MainActivity
import com.example.notificationsample.R
import com.example.notificationsample.broadcast.IncrementBroadcastReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class BasicNotificationService @Inject constructor(@ApplicationContext val context: Context) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    var countForShowBasicNotification = 1
        private set

    private var newNotificationId = 4

    companion object {
        const val notificationId = 1
        const val incrementCounterNotificationId = 2
        const val progressBarNotificationId = 3
        const val channelId = "BasicTestNotification"
        const val name = "Basic notification"
        private const val TAG = "BasicNotificationService"
        var incrementCounter = 1
    }

    fun showBasicNotification(
        title: String,
        content: String,
        isAutoCancel: Boolean = false,
        isBigTextNotification: Boolean = false,
    ) {
        Log.d(TAG, "showBasicNotification: is called..")
        val notification = getBasicNotificationBuilder(
            title,
            content,
            11256987,
            isAutoCancel,
            isBigTextNotification
        ).build()
        notificationManager.notify(notificationId, notification)
        countForShowBasicNotification++
    }

    fun showNewNotification(
        title: String,
        content: String,
        isAutoCancel: Boolean = false,
        isBigTextNotification: Boolean = false,
    ) {
        val notification = getBasicNotificationBuilder(
            title,
            content,
            995512,
            isAutoCancel,
            isBigTextNotification
        ).build()
        notificationManager.notify(newNotificationId, notification)
        newNotificationId++
    }

    fun showIncrementCounterNotification(count: Int) {
        val notificationBuilder = getBasicNotificationBuilder(
            "Basic Test Counter", "Value current counter value is : $count", 44785965,
            autoCancel = true,
            isBigTextNotification = false
        )
        notificationBuilder.addAction(
            R.drawable.calculate_24, "Increment", PendingIntent.getBroadcast(
                context,
                2,
                Intent(context, IncrementBroadcastReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )

        val notification = notificationBuilder.build()
        notificationManager.notify(incrementCounterNotificationId, notification)
    }

    fun showProgressBarNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val notificationBuilder = getBasicNotificationBuilder(
                "Test download", "Fake downloading the content", 47895236,
                autoCancel = true,
                isBigTextNotification = false
            )
            notificationBuilder.setOnlyAlertOnce(true)
            notificationBuilder.setVisibility(VISIBILITY_PRIVATE)
            var curProgress = 0
            postProgressBarNotification(curProgress, notificationBuilder)
            for (i in 1..10) {
                delay(2000)
                curProgress += 10
                postProgressBarNotification(curProgress, notificationBuilder)
            }
            delay(5000)
            notificationManager.cancel(progressBarNotificationId)
        }
    }

    private fun postProgressBarNotification(
        progress: Int,
        notificationBuilder: NotificationCompat.Builder,
    ) {
        notificationBuilder.setPriority(PRIORITY_HIGH)
            .setProgress(100, progress, false)
        notificationBuilder.setVibrate(null)
        notificationBuilder.setSound(Uri.EMPTY)
        val notification = notificationBuilder.build()
        notificationManager.notify(progressBarNotificationId, notification)
    }

    fun showImageNotification(title: String, content: String) {
        val builder = getBasicNotificationBuilder(
            title, content, 5555669,
            autoCancel = true,
            isBigTextNotification = true
        ).setPriority(PRIORITY_HIGH)


        Log.d(TAG, "showImageNotification: ------------------------ 1")
        val largeIcon = (ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.calculate_24,
            null
        ) as VectorDrawable).toBitmap()
        val largeImage = (ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.kolkata,
            null
        ) as Drawable).toBitmap()
        builder.setLargeIcon(largeIcon).setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(largeImage).bigLargeIcon(null)
        )
        Log.d(TAG, "showImageNotification: ------------------------ 2")

        notificationManager.notify(999, builder.build())
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
            .setContentIntent(getPendingIntentForMainActivity(1))
            .setAutoCancel(autoCancel) // Cancel on clicking Notification
            .setColor(color)
        if (isBigTextNotification) {
            notificationBuilder.setStyle(
                NotificationCompat.BigTextStyle().bigText(content)
            )
        }
        return notificationBuilder
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