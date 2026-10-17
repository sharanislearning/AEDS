package com.example.aerspro.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Manages GPS location updates via FusedLocationProviderClient.
 * Emits [Location] objects as a continuous Flow.
 *
 * NOTE: Location permissions (ACCESS_FINE_LOCATION) must be granted before calling [locationFlow].
 */
class AppLocationManager(context: Context) {

    private val fusedClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        UPDATE_INTERVAL_MS
    ).apply {
        setMinUpdateIntervalMillis(FASTEST_INTERVAL_MS)
        setWaitForAccurateLocation(false)
    }.build()

    /**
     * Returns a [Flow] that emits GPS location updates.
     * Requires ACCESS_FINE_LOCATION permission.
     */
    @SuppressLint("MissingPermission")
    fun locationFlow(): Flow<Location> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { trySend(it) }
            }
        }

        fusedClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        )

        awaitClose { fusedClient.removeLocationUpdates(callback) }
    }

    /**
     * Gets the last known location once.
     * Returns null if unavailable.
     */
    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location? {
        return try {
            fusedClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val UPDATE_INTERVAL_MS = 5_000L
        private const val FASTEST_INTERVAL_MS = 2_000L
    }
}
