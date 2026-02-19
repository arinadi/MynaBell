package com.myna.bell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.content.Intent
import com.myna.bell.service.MediaService
import com.myna.bell.ui.radio.RadioScreen
import com.myna.bell.ui.theme.MynaBellTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MynaBellTheme {
                RadioScreen(
                    onPlay = { url ->
                        val intent = Intent(this, MediaService::class.java).apply {
                            action = "ACTION_PLAY"
                            putExtra("URL", url)
                        }
                        startService(intent) // Start Foreground Service
                    }
                )
            }
        }
    }
}


