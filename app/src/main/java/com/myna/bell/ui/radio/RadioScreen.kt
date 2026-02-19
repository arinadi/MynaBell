package com.myna.bell.ui.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myna.bell.data.model.ChannelItem
import com.myna.bell.data.model.Place
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh

// Theme Colors
val MynaBlack = Color(0xFF121212)
val MynaSurface = Color(0xFF1E1E1E)
val MynaGold = Color(0xFFC5A059)
val MynaWhite = Color(0xFFE0E0E0)

@Composable
fun RadioScreen(
    viewModel: RadioViewModel = hiltViewModel(),
    onPlay: (String) -> Unit // Callback to play URL
) {
    val places by viewModel.places.collectAsState()
    val channels by viewModel.channels.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedStation by viewModel.selectedStation.collectAsState()
    val currentStreamUrl by viewModel.currentStreamUrl.collectAsState()

    // Trigger playback when URL changes
    LaunchedEffect(currentStreamUrl) {
        currentStreamUrl?.let { onPlay(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MynaBlack)
            .padding(16.dp)
    ) {
        Text(
            text = "MYNA BELL RADIO",
            style = MaterialTheme.typography.headlineMedium,
            color = MynaGold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = MynaGold)
        }

        // Preview Player
        selectedStation?.let { station ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MynaSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Now Selected", style = MaterialTheme.typography.labelSmall, color = MynaGold)
                    Text(text = station.title, style = MaterialTheme.typography.titleMedium, color = MynaWhite)
                    Text(text = station.subtitle ?: "Unknown", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
             Text(
                text = if (channels.isEmpty()) "Explore Places" else "Select Channel",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
            if (channels.isNotEmpty()) {
                Text(
                    text = "Back to Places",
                    color = MynaGold,
                    modifier = Modifier.clickable { viewModel.loadPlaces() } // Reload places implies clearing channels mechanism needed in VM
                )
            }
        }
       
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            if (channels.isNotEmpty()) {
                items(channels) { channel ->
                    ChannelRow(channel) { viewModel.playStation(channel) }
                }
            } else {
                items(places) { place ->
                    PlaceRow(place) { viewModel.loadChannels(place.id) }
                }
            }
        }
    }
}

@Composable
fun PlaceRow(place: Place, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MynaSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = place.title, color = MynaWhite, style = MaterialTheme.typography.bodyLarge)
                Text(text = place.country, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            Text(text = "${place.size} stations", color = MynaGold, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun ChannelRow(channel: ChannelItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MynaSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = channel.title, color = MynaWhite, style = MaterialTheme.typography.bodyLarge)
                Text(text = channel.subtitle ?: "", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = MynaGold)
        }
    }
}
