package com.myna.bell.domain

import com.myna.bell.data.local.Alarm

interface AlarmScheduler {
    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}
