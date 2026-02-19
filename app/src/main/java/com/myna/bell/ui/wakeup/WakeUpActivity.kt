package com.myna.bell.ui.wakeup

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myna.bell.service.MediaService
import android.content.Intent
import com.myna.bell.ui.theme.MynaBellTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WakeUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Show on Lock Screen
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        
        // Make full screen
        // window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setContent {
            MynaBellTheme {
                WakeUpScreen(
                    onDismiss = {
                        // Stop Service
                        stopService(Intent(this, MediaService::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
