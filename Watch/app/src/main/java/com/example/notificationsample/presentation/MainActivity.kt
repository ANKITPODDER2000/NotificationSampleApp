package com.example.notificationsample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material.Text
import com.example.notificationsample.presentation.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var permissionManager: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            mainViewModel.updatePostNotificationPermission(isGranted)
        }
        setContent {
            Greeting(mainViewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.checkAndRequestPermission(this, permissionManager)
    }
}

@Composable
fun Greeting(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val permissionGranted by mainViewModel.permissionGranted.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (permissionGranted) {
            MainViewModel.PermissionStatus.GRANTED -> Text(
                text = "Post Notification",
                modifier = Modifier.clickable { mainViewModel.postBasicNotificationWithDismissalId(context) })

            MainViewModel.PermissionStatus.REJECTED -> Text(text = "Please allow post notification permission")
            else -> Text(text = "Checking permission.....")
        }
    }
}
