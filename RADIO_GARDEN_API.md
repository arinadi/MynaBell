# Radio Garden API Integration Guide

## Overview

MynaBell uses the **Radio Garden API** to provide access to over 10,000+ radio stations worldwide. This document explains how to integrate and use the API effectively.

## API Specification

**Base URL:** `https://radio.garden/api`

**Documentation:** See `api_radio.json` for complete OpenAPI 3.0 specification

## Key Endpoints

### 1. Get All Places

```http
GET /ara/content/places
```

**Response:** List of places (cities/regions) with radio stations

```json
{
  "apiVersion": 1,
  "version": "9bd5454",
  "data": {
    "list": [
      {
        "id": "Aq7xeIiB",
        "geo": [-97.74306, 30.267153],
        "title": "Austin TX",
        "country": "United States",
        "size": 21
      }
    ]
  }
}
```

### 2. Get Place Details

```http
GET /ara/content/page/{placeId}
```

**Response:** Place details with content (stations, nearby places, etc.)

### 3. Get Place Channels (Radio Stations)

```http
GET /ara/content/page/{placeId}/channels
```

**Response:** List of radio stations in the specified place

```json
{
  "apiVersion": 1,
  "version": "9bd5454",
  "data": {
    "title": "Austin TX",
    "content": [
      {
        "title": "Selected Stations",
        "type": "list",
        "itemsType": "channel",
        "items": [
          {
            "title": "KUTX FM 98.9",
            "href": "/listen/kutx-98-9/vbFsCngB",
            "subtitle": "Austin TX"
          }
        ]
      }
    ]
  }
}
```

### 4. Get Channel Details

```http
GET /ara/content/channel/{channelId}
```

**Response:** Detailed information about a radio station

```json
{
  "apiVersion": 1,
  "data": {
    "id": "vbFsCngB",
    "title": "KUTX FM 98.9",
    "website": "https://www.kutx.org",
    "secure": true,
    "place": {
      "id": "Aq7xeIiB",
      "title": "Austin TX"
    }
  }
}
```

### 5. Get Stream URL

```http
GET /ara/content/listen/{channelId}/channel.mp3
```

**Response:** HTTP 302 redirect to actual stream URL

**Headers:**
```
Location: https://kut2.streamguys1.com/kut2?listening-from-radio-garden=1638918264
```

### 6. Search Stations

```http
GET /search?q={query}
```

**Example:**
```http
GET /search?q=jazz
```

**Response:** Search results for stations, places, and countries

```json
{
  "apiVersion": 1,
  "data": {
    "query": "jazz",
    "hits": {
      "hits": [
        {
          "_id": "602749",
          "_source": {
            "title": "KUTX FM 98.9 - Jazz",
            "subtitle": "Austin TX, United States",
            "type": "channel"
          }
        }
      ]
    }
  }
}
```

### 7. Get Geolocation

```http
GET /geo
```

**Response:** Client's geolocation information

```json
{
  "apiVersion": 1,
  "data": {
    "country_code": "US",
    "country_name": "United States",
    "city": "Austin",
    "latitude": 30.26715,
    "longitude": -97.74306,
    "time_zone": "America/Chicago"
  }
}
```

## Implementation in MynaBell

### API Client (Retrofit)

```kotlin
interface RadioGardenApi {

    @GET("ara/content/places")
    suspend fun getPlaces(): RadioGardenResponse<PlaceList>

    @GET("ara/content/page/{placeId}")
    suspend fun getPlaceDetails(@Path("placeId") placeId: String): RadioGardenResponse<PlaceContent>

    @GET("ara/content/page/{placeId}/channels")
    suspend fun getPlaceChannels(@Path("placeId") placeId: String): RadioGardenResponse<PlaceContent>

    @GET("ara/content/channel/{channelId}")
    suspend fun getChannel(@Path("channelId") channelId: String): RadioGardenResponse<Channel>

    @GET("ara/content/listen/{channelId}/channel.mp3")
    suspend fun getChannelStream(@Path("channelId") channelId: String): Response<Unit>

    @GET("search")
    suspend fun search(@Query("q") query: String): RadioGardenResponse<SearchResults>

    @GET("geo")
    suspend fun getGeolocation(): RadioGardenResponse<Geolocation>
}
```

### Repository Usage

```kotlin
class RadioGardenRepository(private val api: RadioGardenApi) {

    // Get all places
    suspend fun getPlaces(): List<Place> {
        val response = api.getPlaces()
        return response.data.list
    }

    // Get stations for a place
    suspend fun getPlaceChannels(placeId: String): List<Item> {
        val response = api.getPlaceChannels(placeId)
        return response.data.content.flatMap { it.items }
    }

    // Get stream URL for a channel
    suspend fun getChannelStreamUrl(channelId: String): String? {
        val response = api.getChannelStream(channelId)
        return if (response.code() == 302) {
            response.headers().get("Location")
        } else {
            null
        }
    }

    // Search for stations
    suspend fun search(query: String): List<SearchHit> {
        val response = api.search(query)
        return response.data.hits.hits
    }
}
```

### Playing a Radio Station

```kotlin
suspend fun playRadioStation(channelId: String) {
    // 1. Get stream URL from Radio Garden
    val streamUrl = repository.getChannelStreamUrl(channelId)
    
    if (streamUrl != null) {
        // 2. Play with ExoPlayer
        val mediaItem = MediaItem.fromUri(streamUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    } else {
        // 3. Fallback to local ringtone
        playFallbackRingtone()
    }
}
```

## Common Use Cases

### 1. Browse Stations by Location

```kotlin
// Get user's location
val geo = repository.getGeolocation()

// Find nearby places
val places = repository.getPlaces()

// Get stations for nearest place
val nearestPlace = places.first()
val stations = repository.getPlaceChannels(nearestPlace.id)
```

### 2. Search for Genre

```kotlin
// Search for jazz stations
val jazzStations = repository.search("jazz")
    .filter { it.source.type == "channel" }
```

### 3. Get Popular Stations

```kotlin
// Get place details which includes popular stations
val placeDetails = repository.getPlaceDetails("Aq7xeIiB")
val popularStations = placeDetails?.content
    ?.filter { it.title.contains("Popular") }
    ?.flatMap { it.items }
```

## Data Models

### Core Models

```kotlin
data class Channel(
    val id: String,
    val title: String,
    val href: String,
    val website: String?,
    val secure: Boolean,
    val place: PlaceInfo?,
    val country: CountryInfo?
)

data class Place(
    val id: String,
    val geo: List<Float>,  // [longitude, latitude]
    val title: String,
    val country: String,
    val size: Int  // station count
)

data class SearchHit(
    val id: String,
    val score: Float,
    val source: SearchResultSource
)

data class SearchResultSource(
    val code: String,  // country code
    val subtitle: String,
    val type: String,  // "channel", "place", "country"
    val title: String,
    val url: String
)
```

## Error Handling

### Network Errors

```kotlin
try {
    val stations = repository.getPlaceChannels(placeId)
} catch (e: IOException) {
    // Network error - use cached data or show offline message
} catch (e: HttpException) {
    // API error - log and show user-friendly message
}
```

### Stream Loading Timeout

```kotlin
withTimeoutOrNull(5000) {
    repository.getChannelStreamUrl(channelId)
} ?: run {
    // Timeout - use fallback ringtone
}
```

## Rate Limiting

Radio Garden API doesn't have strict rate limits, but follow best practices:

- Cache places and stations locally
- Don't poll endpoints more than once per minute
- Use search sparingly (cache results)

## Caching Strategy

```kotlin
// Cache places for 24 hours
// Cache station lists for 1 hour
// Cache stream URLs for 5 minutes (they may expire)

class CacheManager {
    private val placesCache = MemoryCache<PlaceList>(ttl = 24.hours)
    private val stationsCache = MemoryCache<List<Item>>(ttl = 1.hour)
    private val streamUrlCache = MemoryCache<String>(ttl = 5.minutes)
}
```

## Testing with Mock Data

Use the mock JSON files in `res/raw/`:

- `stations.json` - Mock places response
- `place_channels.json` - Mock channels response
- `channel_details.json` - Mock channel details
- `search_results.json` - Mock search results
- `geolocation.json` - Mock geolocation

## Troubleshooting

### Stream URL Returns Null

**Problem:** `getChannelStreamUrl()` returns null

**Solutions:**
1. Check if channel ID is valid
2. Verify network connectivity
3. Some channels may be geo-restricted
4. Try alternative channel from same place

### Search Returns No Results

**Problem:** Search query returns empty results

**Solutions:**
1. Try broader search terms
2. Check spelling
3. Search in English for better results
4. Use place browsing instead

### API Response Structure Changes

**Problem:** API response doesn't match expected structure

**Solutions:**
1. Check `apiVersion` in response
2. Update data models to match new structure
3. Use nullable fields for optional data
4. Implement fallback parsing logic

## Best Practices

1. **Always handle redirects:** Stream URLs are always 302 redirects
2. **Cache aggressively:** Places and stations don't change often
3. **Graceful degradation:** Always have offline fallback ready
4. **Respect geo-restrictions:** Some stations may not be available globally
5. **User feedback:** Show loading states and error messages clearly

## Resources

- **API Spec:** `api_radio.json` (OpenAPI 3.0)
- **Radio Garden:** https://radio.garden
- **Mock Data:** `res/raw/*.json`
- **Implementation:** `phase-4-integration.md`

---

*Last Updated: February 19, 2026*
