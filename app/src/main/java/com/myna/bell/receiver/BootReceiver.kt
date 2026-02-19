package com.myna.bell.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.myna.bell.data.local.AlarmDao
import com.myna.bell.domain.AlarmScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmDao: AlarmDao

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            scope.launch {
                val alarms = alarmDao.getEnabledAlarms()
                alarms.forEach { alarm ->
                    alarmScheduler.schedule(alarm)
                }
            }
        }
    }
}
