package com.myna.bell.ui.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myna.bell.data.local.Alarm
import com.myna.bell.data.local.AlarmDao
import com.myna.bell.domain.AlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    // Ideally we subscribe to the Flow from DAO
    // For now, let's just fetch manually or expose the flow if we want real-time updates
    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms = _alarms.asStateFlow()

    init {
        loadAlarms()
    }

    private fun loadAlarms() {
        viewModelScope.launch {
            // Collecting the flow to update state
            alarmDao.getAllAlarms().collect { 
                _alarms.value = it
            }
        }
    }

    fun addAlarm(alarm: Alarm) {
        viewModelScope.launch {
            val id = alarmDao.insertAlarm(alarm)
            val newAlarm = alarm.copy(id = id.toInt())
            if (newAlarm.isEnabled) {
                alarmScheduler.schedule(newAlarm)
            }
        }
    }

    fun toggleAlarm(alarm: Alarm) {
        viewModelScope.launch {
            val newStatus = !alarm.isEnabled
            val updatedAlarm = alarm.copy(isEnabled = newStatus)
            alarmDao.updateAlarm(updatedAlarm)
            
            if (newStatus) {
                alarmScheduler.schedule(updatedAlarm)
            } else {
                alarmScheduler.cancel(updatedAlarm)
            }
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmScheduler.cancel(alarm)
            alarmDao.deleteAlarm(alarm)
        }
    }
    
    fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmDao.updateAlarm(alarm)
            if (alarm.isEnabled) {
                alarmScheduler.schedule(alarm)
            } else {
                alarmScheduler.cancel(alarm)
            }
        }
    }
}
