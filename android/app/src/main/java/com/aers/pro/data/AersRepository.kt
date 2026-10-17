package com.aers.pro.data

import com.aers.pro.model.DispatchHistoryItem
import com.aers.pro.model.Hospital
import com.aers.pro.model.Incident
import com.aers.pro.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AersRepository {

    private val _hospitals = MutableStateFlow<List<Hospital>>(MockData.hospitals)
    val hospitals: Flow<List<Hospital>> = _hospitals.asStateFlow()

    private val _history = MutableStateFlow<List<DispatchHistoryItem>>(MockData.history)
    val history: Flow<List<DispatchHistoryItem>> = _history.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile>(MockData.userProfile)
    val userProfile: Flow<UserProfile> = _userProfile.asStateFlow()

    private val _activeIncident = MutableStateFlow<Incident?>(null)
    val activeIncident: Flow<Incident?> = _activeIncident.asStateFlow()

    fun triggerSos(
        address: String = "1245 Main St",
        targetHospitalId: String = "mercy-gen",
        incidentType: String = "Life-Threatening Emergency SOS"
    ): Incident {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val newIncident = Incident(
            id = "sos-${System.currentTimeMillis()}",
            status = "en_route",
            address = address,
            targetHospitalId = targetHospitalId,
            unit = MockData.primaryParamedic,
            progressPercent = 65,
            initialEtaMins = 4,
            currentEtaMins = 4,
            sosActivatedAt = timeFormat.format(Date()),
            incidentType = incidentType
        )
        _activeIncident.value = newIncident

        // Prepend to history log
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val historyEntry = DispatchHistoryItem(
            id = "disp-${System.currentTimeMillis()}",
            date = dateFormat.format(Date()).uppercase(),
            time = "${timeFormat.format(Date())} PST",
            type = "emergency_sos",
            title = "Priority Code 3 SOS Intercept",
            location = address,
            destinationHospital = _hospitals.value.find { it.id == targetHospitalId }?.name ?: "Mercy General Hospital",
            status = "In Progress",
            unitAssigned = "${MockData.primaryParamedic.unitNumber} (Medic 1)",
            responseTime = "4m Est Intercept"
        )
        _history.value = listOf(historyEntry) + _history.value

        return newIncident
    }

    fun cancelIncident(incidentId: String) {
        if (_activeIncident.value?.id == incidentId) {
            _activeIncident.value = null
            // Update history entry if exists
            _history.value = _history.value.map { item ->
                if (item.status == "In Progress") item.copy(status = "Cancelled") else item
            }
        }
    }

    fun updateProfile(updated: UserProfile) {
        _userProfile.value = updated
    }
}
