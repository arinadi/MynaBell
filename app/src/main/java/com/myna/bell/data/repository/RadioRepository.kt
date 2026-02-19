package com.myna.bell.data.repository

import com.myna.bell.data.model.ChannelItem
import com.myna.bell.data.model.Place
import com.myna.bell.data.model.SearchHit
import com.myna.bell.data.network.RadioGardenApi
import javax.inject.Inject

class RadioRepository @Inject constructor(
    private val api: RadioGardenApi
) {

    suspend fun getPlaces(): List<Place> {
        return api.getPlaces().data.list
    }

    suspend fun getPlaceChannels(placeId: String): List<ChannelItem> {
        val response = api.getPlaceChannels(placeId)
        // Extract channels from all content sections
        return response.data.content.flatMap { it.items }
    }

    suspend fun getChannelStreamUrl(channelId: String): String? {
        val response = api.getChannelStream(channelId)
        // Radio Garden returns 302 with Location header for the stream
        // Retrofit follows redirects by default, so we might get the final 200 OK from the stream URL directly?
        // Actually, for audio streams, we usually want the URL itself to pass to ExoPlayer, not the content.
        // We should configure OkHttp to NOT follow redirects for this specific call, or generic client.
        // However, if Retrofit follows it, response.raw().request.url might be the final URL.
        
        // Let's implement robustly:
        // If 302/301/303/307, get "Location". 
        // If 200, it means it followed redirect, so use response.raw().request.url.
        
        return if (response.isSuccessful) {
             response.raw().request.url.toString()
        } else if (response.code() in 300..399) {
            response.headers()["Location"]
        } else {
            null
        }
    }

    suspend fun search(query: String): List<SearchHit> {
        return api.search(query).data.hits.hits
    }
}
