package com.example.notificationsample.viewmodel

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.ViewModel
import com.example.notificationsample.ChatDB
import com.example.notificationsample.R
import com.example.notificationsample.notification.BasicNotificationService
import com.example.notificationsample.notification.MessageNotificationService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val basicNotificationService: BasicNotificationService,
    private val messageNotificationService: MessageNotificationService,
    @ApplicationContext val context: Context,
) : ViewModel() {

    enum class PermissionStatus {
        CHECKING,
        GRANTED,
        NOT_GRANTED
    }

    private val _permissionStatus = MutableStateFlow(PermissionStatus.CHECKING)
    val permissionStatus = _permissionStatus.asStateFlow()

    private val largeContent =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun postAndUpdateCurrentNotification() {
        Log.d(TAG, "postBasicViewModel: is called")
        basicNotificationService.showBasicNotification(
            "Basic Notification",
            "Count value for current notification is : ${basicNotificationService.countForShowBasicNotification}"
        )
    }

    fun postNewNotification() {
        basicNotificationService.showNewNotification(
            "Basic Notification",
            largeContent,
            isAutoCancel = true,
            isBigTextNotification = true
        )
    }

    fun postIncrementCounterNotification() {
        basicNotificationService.showIncrementCounterNotification(BasicNotificationService.incrementCounter)
    }

    fun postProgressBarNotification() {
        basicNotificationService.showProgressBarNotification()
    }

    fun updatePermissionGranted(permissionStatus: PermissionStatus) {
        _permissionStatus.value = permissionStatus
    }

    fun checkNotificationPermission(context: Activity, launcher: ActivityResultLauncher<String>) {
        if (ActivityCompat.checkSelfPermission(
                context,
                "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "checkNotificationPermission: asking for permission...")
            launcher.launch("android.permission.POST_NOTIFICATIONS")
        } else {
            Log.d(TAG, "checkNotificationPermission: permission granted...")
            _permissionStatus.value = PermissionStatus.GRANTED
        }
    }

    fun postMessage() {
        messageNotificationService.showMessageNotification(
            getRandomUser("Test"),
            createMessage("Ankit"),
            System.currentTimeMillis()
        )
    }

    private fun createMessage(name: String): Person {
        val user = getRandomUser(name)
        ChatDB.storeNewChat(user, Pair(user, "How Are you?"))
        return user
    }

    private fun getRandomUser(userName: String): Person {
        if (userName == "Ankit") {
            return Person.Builder()
                .setIcon(IconCompat.createWithResource(context, R.drawable.kolkata))
                .setName(userName)
                .build()
        }
        return Person.Builder()
            .setIcon(IconCompat.createWithResource(context, R.drawable.user))
            .setName(userName)
            .build()
    }

    fun showLargeIconNotification() {
        basicNotificationService.showImageNotification("Large Icon Notification....", largeContent)
    }

    fun postNotificationWithDismissId() {
        basicNotificationService.postBasicNotificationWithDismissId("Notification with dismiss id", "Large Content..........")
    }
}