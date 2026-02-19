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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
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
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "radio") {
                    composable("radio") {
                        RadioScreen(
                            onPlay = { url ->
                                val intent = Intent(this@MainActivity, MediaService::class.java).apply {
                                    action = "ACTION_PLAY"
                                    putExtra("URL", url)
                                }
                                startService(intent)
                            }
                        )
                        // Temporary button to go to Alarms
                         androidx.compose.foundation.layout.Box(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
                            androidx.compose.material3.Button(
                                onClick = { navController.navigate("alarms") },
                                modifier = androidx.compose.ui.Modifier.align(androidx.compose.ui.Alignment.BottomEnd).padding(16.dp)
                            ) {
                                androidx.compose.material3.Text("Alarms")
                            }
                        }
                    }
                    composable("alarms") {
                        com.myna.bell.ui.alarm.AlarmListScreen(
                            onAddAlarm = { 
                                // TODO: Navigate to Edit
                            },
                            onEditAlarm = { alarm ->
                                // TODO: Navigate to Edit
                            }
                        )
                    }
                }
            }
        }
    }
}


