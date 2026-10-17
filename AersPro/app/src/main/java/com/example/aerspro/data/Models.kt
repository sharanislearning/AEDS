package com.example.aerspro.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ─── Nominatim Geocoding ──────────────────────────────────────────────────────

@Serializable
data class NominatimResult(
    @SerialName("lat") val lat: String,
    @SerialName("lon") val lon: String,
    @SerialName("display_name") val displayName: String
)

// ─── OSRM Routing ────────────────────────────────────────────────────────────

@Serializable
data class OsrmResponse(
    @SerialName("code") val code: String,
    @SerialName("routes") val routes: List<OsrmRoute> = emptyList()
)

@Serializable
data class OsrmRoute(
    @SerialName("geometry") val geometry: OsrmGeometry,
    @SerialName("distance") val distance: Double,
    @SerialName("duration") val duration: Double,
    @SerialName("legs") val legs: List<OsrmLeg> = emptyList()
)

@Serializable
data class OsrmGeometry(
    @SerialName("type") val type: String,
    @SerialName("coordinates") val coordinates: List<List<Double>>
)

@Serializable
data class OsrmLeg(
    @SerialName("steps") val steps: List<OsrmStep> = emptyList(),
    @SerialName("distance") val distance: Double,
    @SerialName("duration") val duration: Double
)

@Serializable
data class OsrmStep(
    @SerialName("maneuver") val maneuver: OsrmManeuver,
    @SerialName("name") val name: String,
    @SerialName("distance") val distance: Double,
    @SerialName("duration") val duration: Double
)

@Serializable
data class OsrmManeuver(
    @SerialName("type") val type: String,
    @SerialName("modifier") val modifier: String? = null,
    @SerialName("instruction") val instruction: String? = null
)
