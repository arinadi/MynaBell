package com.myna.bell.ui.radio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myna.bell.data.model.ChannelItem
import com.myna.bell.data.model.Place
import com.myna.bell.data.repository.RadioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    private val repository: RadioRepository
) : ViewModel() {

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places = _places.asStateFlow()

    private val _channels = MutableStateFlow<List<ChannelItem>>(emptyList())
    val channels = _channels.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _currentStreamUrl = MutableStateFlow<String?>(null)
    val currentStreamUrl = _currentStreamUrl.asStateFlow()

    private val _selectedStation = MutableStateFlow<ChannelItem?>(null)
    val selectedStation = _selectedStation.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _places.value = repository.getPlaces()
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadChannels(placeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _channels.value = emptyList()
            try {
                _channels.value = repository.getPlaceChannels(placeId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun playStation(channel: ChannelItem) {
        viewModelScope.launch {
            _selectedStation.value = channel
            _isLoading.value = true
            try {
                val url = repository.getChannelStreamUrl(channel.channelId)
                _currentStreamUrl.value = url
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
