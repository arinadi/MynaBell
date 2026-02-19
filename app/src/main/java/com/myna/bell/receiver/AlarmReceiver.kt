package com.myna.bell.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.myna.bell.service.MediaService
import com.myna.bell.ui.radio.RadioViewModel 
// Note: We might need a repo here to fetch the station URL if not passed in intent.
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getIntExtra("ALARM_ID", -1)
        val stationId = intent.getStringExtra("STATION_ID")

        // Start MediaService
        // Ideally we resolve the URL here or in the Service.
        // For now, let's assume the Service or a fresh logic handles the station ID -> URL lookup.
        // Or we pass the Play intent.
        
        val serviceIntent = Intent(context, MediaService::class.java).apply {
            action = "ACTION_ALARM_TRIGGER"
            putExtra("ALARM_ID", alarmId)
            putExtra("STATION_ID", stationId)
        }
        context.startForegroundService(serviceIntent)
        
        val activityIntent = Intent(context, com.myna.bell.ui.wakeup.WakeUpActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("ALARM_ID", alarmId)
        }
        context.startActivity(activityIntent)
    }
}
