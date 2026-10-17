package com.example.aerspro.data

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Repository for geocoding (Nominatim) and routing (OSRM) API calls.
 * Both services are free and use OpenStreetMap data.
 */
class NavigationRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    /**
     * Geocode a place name to lat/lng using OpenStreetMap Nominatim.
     * Returns the first result or null if nothing found.
     */
    suspend fun geocode(query: String): NominatimResult? {
        val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
        val url = "https://nominatim.openstreetmap.org/search" +
                "?q=$encodedQuery&format=json&limit=1"

        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "AERS-PRO-App/1.0 (android)")
            .build()

        return try {
            val response = httpClient.newCall(request).execute()
            val body = response.body?.string() ?: return null
            val results = json.decodeFromString<List<NominatimResult>>(body)
            results.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Get a driving route from [origin] to [destination] using the public OSRM demo server.
     * Coordinates are [lng, lat] pairs (OSRM convention).
     */
    suspend fun getRoute(
        originLat: Double,
        originLng: Double,
        destLat: Double,
        destLng: Double
    ): OsrmResponse? {
        // OSRM coordinate format: lng,lat;lng,lat
        val coords = "$originLng,$originLat;$destLng,$destLat"
        val url = "https://router.project-osrm.org/route/v1/driving/$coords" +
                "?overview=full&geometries=geojson&steps=true"

        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "AERS-PRO-App/1.0 (android)")
            .build()

        return try {
            val response = httpClient.newCall(request).execute()
            val body = response.body?.string() ?: return null
            json.decodeFromString<OsrmResponse>(body)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
