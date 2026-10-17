package com.aers.pro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.aers.pro.data.MockData
import com.aers.pro.model.ScreenDestination
import com.aers.pro.ui.components.AersBottomNav
import com.aers.pro.ui.components.AersTopBar
import com.aers.pro.ui.components.CallDialog
import com.aers.pro.ui.components.TextDialog
import com.aers.pro.ui.screens.HistoryScreen
import com.aers.pro.ui.screens.HomeScreen
import com.aers.pro.ui.screens.HospitalDetailsScreen
import com.aers.pro.ui.screens.HospitalsScreen
import com.aers.pro.ui.screens.IncidentDetailsScreen
import com.aers.pro.ui.screens.NavigationScreen
import com.aers.pro.ui.screens.ProfileScreen
import com.aers.pro.ui.theme.AERSProTheme
import com.aers.pro.ui.theme.Black
import com.aers.pro.viewmodel.AersViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: AersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AERSProTheme {
                AersAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AersAppContent(viewModel: AersViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val hospitals by viewModel.hospitals.collectAsState()
    val history by viewModel.history.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val activeIncident by viewModel.activeIncident.collectAsState()
    val isCallOpen by viewModel.isCallOpen.collectAsState()
    val isTextOpen by viewModel.isTextOpen.collectAsState()

    val title = when (currentScreen) {
        is ScreenDestination.Home -> "Home"
        is ScreenDestination.Hospitals -> "Hospitals"
        is ScreenDestination.History -> "History"
        is ScreenDestination.Profile -> "Profile"
        is ScreenDestination.HospitalDetails -> "Hospital Details"
        is ScreenDestination.IncidentDetails -> "Incident Details"
        is ScreenDestination.Navigation -> "Tactical Navigation"
    }

    val showBack = currentScreen !is ScreenDestination.Home &&
            currentScreen !is ScreenDestination.Hospitals &&
            currentScreen !is ScreenDestination.History &&
            currentScreen !is ScreenDestination.Profile

    val isTopLevelTab = !showBack

    Scaffold(
        topBar = {
            AersTopBar(
                title = title,
                showBack = showBack,
                onBack = {
                    when (currentScreen) {
                        is ScreenDestination.HospitalDetails -> viewModel.navigateTo(ScreenDestination.Hospitals)
                        is ScreenDestination.IncidentDetails -> viewModel.navigateTo(ScreenDestination.Home)
                        is ScreenDestination.Navigation -> {
                            val nav = currentScreen as ScreenDestination.Navigation
                            viewModel.navigateTo(ScreenDestination.HospitalDetails(nav.hospitalId))
                        }
                        else -> viewModel.navigateTo(ScreenDestination.Home)
                    }
                },
                onProfileClick = { viewModel.navigateTo(ScreenDestination.Profile) }
            )
        },
        bottomBar = {
            if (isTopLevelTab) {
                AersBottomNav(
                    currentScreen = currentScreen,
                    onNavigate = { viewModel.navigateTo(it) },
                    hasActiveSos = activeIncident != null
                )
            }
        },
        containerColor = Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black)
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is ScreenDestination.Home -> {
                    HomeScreen(
                        hospitals = hospitals,
                        activeIncident = activeIncident,
                        onTriggerSos = { viewModel.triggerSos() },
                        onFindHospitals = { viewModel.navigateTo(ScreenDestination.Hospitals) },
                        onSelectHospital = { viewModel.navigateTo(ScreenDestination.HospitalDetails(it)) },
                        onSelectIncident = { viewModel.navigateTo(ScreenDestination.IncidentDetails(it)) }
                    )
                }
                is ScreenDestination.Hospitals -> {
                    HospitalsScreen(
                        hospitals = hospitals,
                        onSelectHospital = { viewModel.navigateTo(ScreenDestination.HospitalDetails(it)) },
                        onStartRoute = { viewModel.navigateTo(ScreenDestination.HospitalDetails(it)) }
                    )
                }
                is ScreenDestination.History -> {
                    HistoryScreen(
                        history = history,
                        onSelectHospitalByName = { name ->
                            val match = hospitals.find { it.name.contains(name, ignoreCase = true) }
                            if (match != null) {
                                viewModel.navigateTo(ScreenDestination.HospitalDetails(match.id))
                            }
                        }
                    )
                }
                is ScreenDestination.Profile -> {
                    ProfileScreen(
                        profile = userProfile,
                        onUpdateProfile = { viewModel.updateProfile(it) }
                    )
                }
                is ScreenDestination.HospitalDetails -> {
                    val hospital = hospitals.find { it.id == screen.hospitalId } ?: hospitals.first()
                    HospitalDetailsScreen(
                        hospital = hospital,
                        onStartNavigation = { viewModel.navigateTo(ScreenDestination.Navigation(it)) },
                        onViewOtherHospitals = { viewModel.navigateTo(ScreenDestination.Hospitals) }
                    )
                }
                is ScreenDestination.IncidentDetails -> {
                    val inc = activeIncident ?: com.aers.pro.model.Incident(
                        id = screen.incidentId,
                        status = "en_route",
                        address = "1245 Main St",
                        targetHospitalId = "mercy-gen",
                        unit = MockData.primaryParamedic,
                        progressPercent = 65,
                        initialEtaMins = 4,
                        currentEtaMins = 4,
                        sosActivatedAt = "14:22",
                        incidentType = "Life-Threatening Emergency SOS"
                    )
                    IncidentDetailsScreen(
                        incident = inc,
                        onCallDriver = { viewModel.openCall() },
                        onTextDriver = { viewModel.openText() },
                        onCancelRequest = { viewModel.cancelIncident(it) },
                        onViewLiveNavigation = {
                            viewModel.navigateTo(ScreenDestination.Navigation(inc.targetHospitalId))
                        }
                    )
                }
                is ScreenDestination.Navigation -> {
                    val hospital = hospitals.find { it.id == screen.hospitalId } ?: hospitals.first()
                    NavigationScreen(
                        hospital = hospital,
                        onEndNavigation = { viewModel.navigateTo(ScreenDestination.Home) },
                        onSwitchToHospitalDetails = {
                            viewModel.navigateTo(ScreenDestination.HospitalDetails(hospital.id))
                        }
                    )
                }
            }

            // Encrypted Comms Dialogs
            if (isCallOpen) {
                CallDialog(
                    paramedicName = MockData.primaryParamedic.paramedicName,
                    unitNumber = MockData.primaryParamedic.unitNumber,
                    onDismiss = { viewModel.closeCall() }
                )
            }

            if (isTextOpen) {
                TextDialog(
                    paramedicName = MockData.primaryParamedic.paramedicName,
                    unitNumber = MockData.primaryParamedic.unitNumber,
                    onDismiss = { viewModel.closeText() }
                )
            }
        }
    }
}
