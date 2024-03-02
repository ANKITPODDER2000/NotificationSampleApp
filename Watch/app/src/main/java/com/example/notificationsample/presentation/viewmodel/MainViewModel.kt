package com.example.notificationsample.presentation.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.notificationsample.presentation.notification.BasicNotificationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    enum class PermissionStatus {
        GRANTED,
        REJECTED,
        CHECKING
    }

    private val _permissionGranted = MutableStateFlow(PermissionStatus.CHECKING)
    val permissionGranted = _permissionGranted.asStateFlow()

    fun postBasicNotificationWithDismissalId(context: Context) {
        BasicNotificationService(context).postBasicNotificationWithDismissId(
            "Notification with dismiss id",
            "Large Content.........."
        )
    }

    fun checkAndRequestPermission(
        context: Context,
        permissionManager: ActivityResultLauncher<String>,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PERMISSION_GRANTED
            ) {
                _permissionGranted.value = PermissionStatus.REJECTED
                permissionManager.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                _permissionGranted.value = PermissionStatus.GRANTED
            }
        } else {
            _permissionGranted.value = PermissionStatus.GRANTED
        }
    }

    fun updatePostNotificationPermission(isGranted: Boolean) {
        if (isGranted) _permissionGranted.value = PermissionStatus.GRANTED
        else _permissionGranted.value = PermissionStatus.REJECTED
    }
}