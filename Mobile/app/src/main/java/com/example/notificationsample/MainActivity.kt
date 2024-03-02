package com.example.notificationsample

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notificationsample.notification.BasicNotificationService
import com.example.notificationsample.ui.theme.NotificationSampleTheme
import com.example.notificationsample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val launcher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    mainViewModel.updatePermissionGranted(MainViewModel.PermissionStatus.GRANTED)
                } else {
                    mainViewModel.updatePermissionGranted(MainViewModel.PermissionStatus.NOT_GRANTED)
                }
            }

        mainViewModel.checkNotificationPermission(this, launcher)

        setContent {
            NotificationSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val permissionStatus by mainViewModel.permissionStatus.collectAsState()
                    when (permissionStatus) {
                        MainViewModel.PermissionStatus.GRANTED -> MainScreen(mainViewModel)
                        MainViewModel.PermissionStatus.NOT_GRANTED -> PermissionNotGranted()

                        else -> {}
                    }
                }
            }
        }

    }
}

@Composable
fun PermissionNotGranted() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {

        Text(
            text = "Please allow the permission to use the application...",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CommonButton(btnTitle = "Check Permission") {
            // mainViewModel.checkNotificationPermission(context, )
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        CommonButton(btnTitle = "Post Or Update Current Notification") {
            mainViewModel.postAndUpdateCurrentNotification()
        }
        CommonButton(btnTitle = "Post New Notification") {
            mainViewModel.postNewNotification()
        }

        CommonButton(btnTitle = "Post increment counter Notification") {
            mainViewModel.postIncrementCounterNotification()
        }

        CommonButton(btnTitle = "Post progress bar notification") {
            mainViewModel.postProgressBarNotification()
        }

        CommonButton(btnTitle = "Large Icon Notification") {
            mainViewModel.showLargeIconNotification()
        }

        CommonButton(btnTitle = "Post a message") {
            mainViewModel.postMessage()
        }

        CommonButton(btnTitle = "Post a Notification with Dismiss id") {
            mainViewModel.postNotificationWithDismissId()
        }
    }
}

@Composable
fun CommonButton(btnTitle: String, onClickListener: () -> Unit) {
    Button(
        onClick = { onClickListener() },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 8.dp)
    ) {
        Text(text = btnTitle)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotificationSampleTheme {
        // MainScreen(MainViewModel(BasicNotificationService(LocalContext.current)))
    }
}