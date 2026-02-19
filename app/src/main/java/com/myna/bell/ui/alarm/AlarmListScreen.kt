package com.myna.bell.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myna.bell.data.local.Alarm
import com.myna.bell.ui.radio.MynaBlack
import com.myna.bell.ui.radio.MynaGold
import com.myna.bell.ui.radio.MynaSurface
import com.myna.bell.ui.radio.MynaWhite
import java.util.Locale

@Composable
fun AlarmListScreen(
    viewModel: AlarmViewModel = hiltViewModel(),
    onAddAlarm: () -> Unit,
    onEditAlarm: (Alarm) -> Unit
) {
    val alarms by viewModel.alarms.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddAlarm,
                containerColor = MynaGold,
                contentColor = MynaBlack
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Alarm")
            }
        },
        containerColor = MynaBlack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "MYNA ALARMS",
                style = MaterialTheme.typography.headlineMedium,
                color = MynaGold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (alarms.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No alarms set", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(alarms) { alarm ->
                        AlarmItem(
                            alarm = alarm,
                            onToggle = { viewModel.toggleAlarm(alarm) },
                            onDelete = { viewModel.deleteAlarm(alarm) },
                            onClick = { onEditAlarm(alarm) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlarmItem(
    alarm: Alarm,
    onToggle: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MynaSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = String.format(Locale.getDefault(), "%02d:%02d", alarm.hour, alarm.minute),
                    style = MaterialTheme.typography.displayMedium,
                    color = if (alarm.isEnabled) MynaWhite else Color.Gray
                )
                Text(
                    text = alarm.stationName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (alarm.isEnabled) MynaGold else Color.Gray
                )
                  Text(
                    text = if (alarm.days.isEmpty()) "Once" else "Weekdays", // Mock logic for display
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Switch(
                    checked = alarm.isEnabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MynaBlack,
                        checkedTrackColor = MynaGold,
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = MynaBlack
                    )
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.DarkGray)
                }
            }
        }
    }
}
