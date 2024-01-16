package com.example.notificationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel by viewModels<MainViewModel>()
        setContent {
            NotificationSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainViewModel)
                }
            }
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
        MainScreen(MainViewModel(BasicNotificationService(LocalContext.current)))
    }
}