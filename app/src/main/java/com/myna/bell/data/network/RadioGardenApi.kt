package com.myna.bell.data.network

import com.myna.bell.data.model.ChannelDetail
import com.myna.bell.data.model.PlaceContent
import com.myna.bell.data.model.PlaceList
import com.myna.bell.data.model.RadioGardenResponse
import com.myna.bell.data.model.SearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RadioGardenApi {

    @GET("ara/content/places")
    suspend fun getPlaces(): RadioGardenResponse<PlaceList>

    @GET("ara/content/page/{placeId}")
    suspend fun getPlaceDetails(@Path("placeId") placeId: String): RadioGardenResponse<PlaceContent>

    @GET("ara/content/page/{placeId}/channels")
    suspend fun getPlaceChannels(@Path("placeId") placeId: String): RadioGardenResponse<PlaceContent>

    @GET("ara/content/channel/{channelId}")
    suspend fun getChannel(@Path("channelId") channelId: String): RadioGardenResponse<ChannelDetail>

    @GET("ara/content/listen/{channelId}/channel.mp3")
    suspend fun getChannelStream(@Path("channelId") channelId: String): Response<Unit>

    @GET("search")
    suspend fun search(@Query("q") query: String): RadioGardenResponse<SearchResults>
}
