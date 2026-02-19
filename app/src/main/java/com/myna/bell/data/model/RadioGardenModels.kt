package com.myna.bell.data.model

import com.google.gson.annotations.SerializedName

// --- Generic Responses ---

data class RadioGardenResponse<T>(
    @SerializedName("apiVersion") val apiVersion: Int,
    @SerializedName("version") val version: String?,
    @SerializedName("data") val data: T
)

// --- Places ---

data class PlaceList(
    @SerializedName("list") val list: List<Place>
)

data class Place(
    @SerializedName("id") val id: String,
    @SerializedName("geo") val geo: List<Float>, // [lon, lat]
    @SerializedName("title") val title: String,
    @SerializedName("country") val country: String,
    @SerializedName("size") val size: Int
)

// --- Content (Place Details / Channels) ---

data class PlaceContent(
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: List<ContentSection>
)

data class ContentSection(
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String,
    @SerializedName("itemsType") val itemsType: String?,
    @SerializedName("items") val items: List<ChannelItem>
)

data class ChannelItem(
    @SerializedName("id") val id: String?, // Sometimes ID is not in the list item directly, mapped from href usually, but let's see. 
    // Wait, API doc says href: "/listen/kutx-98-9/vbFsCngB". ID is the last part.
    // The list item shows: title, href, subtitle.
    @SerializedName("title") val title: String,
    @SerializedName("href") val href: String,
    @SerializedName("subtitle") val subtitle: String?
) {
    // Helper to extract ID from href
    val channelId: String
        get() = href.substringAfterLast("/")
}

// --- Channel Detail ---

data class ChannelDetail(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("website") val website: String?,
    @SerializedName("secure") val secure: Boolean,
    @SerializedName("place") val place: PlaceInfo?,
    @SerializedName("country") val country: CountryInfo?
)

data class PlaceInfo(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String
)

data class CountryInfo(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String
)

// --- Search ---

data class SearchResults(
    @SerializedName("hits") val hits: SearchHits
)

data class SearchHits(
    @SerializedName("hits") val hits: List<SearchHit>
)

data class SearchHit(
    @SerializedName("_id") val id: String,
    @SerializedName("_source") val source: SearchSource
)

data class SearchSource(
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("type") val type: String, // "channel", "place", "country"
    @SerializedName("url") val url: String
)
