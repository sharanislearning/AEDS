package com.aers.pro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aers.pro.data.AersRepository
import com.aers.pro.data.MockData
import com.aers.pro.model.DispatchHistoryItem
import com.aers.pro.model.Hospital
import com.aers.pro.model.Incident
import com.aers.pro.model.ScreenDestination
import com.aers.pro.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AersViewModel(
    private val repository: AersRepository = AersRepository()
) : ViewModel() {

    val hospitals: StateFlow<List<Hospital>> = repository.hospitals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MockData.hospitals)

    val history: StateFlow<List<DispatchHistoryItem>> = repository.history
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MockData.history)

    val userProfile: StateFlow<UserProfile> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MockData.userProfile)

    val activeIncident: StateFlow<Incident?> = repository.activeIncident
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Home)
    val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

    private val _isCallOpen = MutableStateFlow(false)
    val isCallOpen: StateFlow<Boolean> = _isCallOpen.asStateFlow()

    private val _isTextOpen = MutableStateFlow(false)
    val isTextOpen: StateFlow<Boolean> = _isTextOpen.asStateFlow()

    fun navigateTo(screen: ScreenDestination) {
        _currentScreen.value = screen
    }

    fun triggerSos(
        address: String = "1245 Main St",
        targetHospitalId: String = "mercy-gen",
        incidentType: String = "Life-Threatening Emergency SOS"
    ) {
        viewModelScope.launch {
            val incident = repository.triggerSos(address, targetHospitalId, incidentType)
            _currentScreen.value = ScreenDestination.IncidentDetails(incident.id)
        }
    }

    fun cancelIncident(incidentId: String) {
        viewModelScope.launch {
            repository.cancelIncident(incidentId)
            _currentScreen.value = ScreenDestination.Home
        }
    }

    fun updateProfile(updated: UserProfile) {
        viewModelScope.launch {
            repository.updateProfile(updated)
        }
    }

    fun openCall() {
        _isCallOpen.value = true
    }

    fun closeCall() {
        _isCallOpen.value = false
    }

    fun openText() {
        _isTextOpen.value = true
    }

    fun closeText() {
        _isTextOpen.value = false
    }
}
