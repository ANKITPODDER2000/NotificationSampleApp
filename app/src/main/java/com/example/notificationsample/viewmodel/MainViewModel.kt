package com.example.notificationsample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.notificationsample.notification.BasicNotificationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val basicNotificationService: BasicNotificationService) :
    ViewModel() {

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
}