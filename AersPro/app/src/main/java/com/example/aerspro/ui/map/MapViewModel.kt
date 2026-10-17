package com.example.aerspro.ui.map

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aerspro.data.NavigationRepository
import com.example.aerspro.data.OsrmResponse
import com.example.aerspro.location.AppLocationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MapUiState(
    val userLocation: Location? = null,
    val destinationLat: Double? = null,
    val destinationLng: Double? = null,
    val destinationName: String = "",
    val routeCoordinates: List<Pair<Double, Double>> = emptyList(), // (lat, lng)
    val routeDistanceKm: Double? = null,
    val routeDurationMin: Double? = null,
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val isRouting: Boolean = false,
    val errorMessage: String? = null,
    val locationPermissionGranted: Boolean = false
)

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NavigationRepository()
    private val locationManager = AppLocationManager(application)

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    /**
     * Start listening for GPS location updates.
     * Call this after location permission is granted.
     */
    fun startLocationUpdates() {
        _uiState.update { it.copy(locationPermissionGranted = true) }
        viewModelScope.launch {
            locationManager.locationFlow()
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = "Location error: ${e.message}") }
                }
                .collect { location ->
                    _uiState.update { it.copy(userLocation = location) }
                }
        }
    }

    /** Update the search text field */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query, errorMessage = null) }
    }

    /**
     * Geocode the search query using Nominatim, then fetch the OSRM route
     * from the user's current location to the result.
     */
    fun searchAndRoute() {
        val query = _uiState.value.searchQuery.trim()
        if (query.isEmpty()) return

        val userLocation = _uiState.value.userLocation
        if (userLocation == null) {
            _uiState.update { it.copy(errorMessage = "Waiting for GPS location…") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true, errorMessage = null) }

            val geocoded = withContext(Dispatchers.IO) { repository.geocode(query) }

            if (geocoded == null) {
                _uiState.update {
                    it.copy(isSearching = false, errorMessage = "Place not found: $query")
                }
                return@launch
            }

            val destLat = geocoded.lat.toDouble()
            val destLng = geocoded.lon.toDouble()

            _uiState.update {
                it.copy(
                    destinationLat = destLat,
                    destinationLng = destLng,
                    destinationName = geocoded.displayName,
                    isSearching = false,
                    isRouting = true
                )
            }

            val osrm = withContext(Dispatchers.IO) {
                repository.getRoute(
                    originLat = userLocation.latitude,
                    originLng = userLocation.longitude,
                    destLat = destLat,
                    destLng = destLng
                )
            }

            if (osrm == null || osrm.code != "Ok" || osrm.routes.isEmpty()) {
                _uiState.update {
                    it.copy(isRouting = false, errorMessage = "Could not find a route.")
                }
                return@launch
            }

            val route = osrm.routes.first()
            // OSRM geometry coordinates are [lng, lat], we store as (lat, lng) pairs
            val coordPairs = route.geometry.coordinates.map { coord ->
                Pair(coord[1], coord[0]) // (lat, lng)
            }

            _uiState.update {
                it.copy(
                    routeCoordinates = coordPairs,
                    routeDistanceKm = route.distance / 1000.0,
                    routeDurationMin = route.duration / 60.0,
                    isRouting = false
                )
            }
        }
    }

    /** Route to a specific lat/lng without geocoding (e.g. map long-press) */
    fun routeTo(destLat: Double, destLng: Double, name: String = "Destination") {
        val userLocation = _uiState.value.userLocation ?: return
        _uiState.update {
            it.copy(
                destinationLat = destLat,
                destinationLng = destLng,
                destinationName = name,
                isRouting = true,
                errorMessage = null
            )
        }
        viewModelScope.launch {
            val osrm = withContext(Dispatchers.IO) {
                repository.getRoute(
                    originLat = userLocation.latitude,
                    originLng = userLocation.longitude,
                    destLat = destLat,
                    destLng = destLng
                )
            }
            if (osrm == null || osrm.code != "Ok" || osrm.routes.isEmpty()) {
                _uiState.update { it.copy(isRouting = false, errorMessage = "Could not find route.") }
                return@launch
            }
            val route = osrm.routes.first()
            val coordPairs = route.geometry.coordinates.map { Pair(it[1], it[0]) }
            _uiState.update {
                it.copy(
                    routeCoordinates = coordPairs,
                    routeDistanceKm = route.distance / 1000.0,
                    routeDurationMin = route.duration / 60.0,
                    isRouting = false
                )
            }
        }
    }

    /** Clear the current route */
    fun clearRoute() {
        _uiState.update {
            it.copy(
                destinationLat = null,
                destinationLng = null,
                destinationName = "",
                routeCoordinates = emptyList(),
                routeDistanceKm = null,
                routeDurationMin = null
            )
        }
    }
}
