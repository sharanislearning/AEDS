package com.aers.pro.model

data class Hospital(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val distance: String,
    val etaMins: Int,
    val traumaLevel: String,
    val erStatus: String, // "open" | "busy" | "closed"
    val icuStatus: String, // "available" | "full" | "limited"
    val icuBedsAvailable: Int,
    val waitTimeMins: Int,
    val trafficStatus: String, // "low" | "moderate" | "high"
    val trafficText: String,
    val phone: String,
    val isFastest: Boolean = false,
    val specialties: List<String> = emptyList(),
    val coordinates: Pair<Double, Double> = Pair(34.0522, -118.2437),
    val mapImage: String = ""
)

data class AmbulanceUnit(
    val id: String,
    val unitNumber: String,
    val paramedicName: String,
    val paramedicRole: String,
    val paramedicRating: Double,
    val avatar: String = "",
    val phone: String,
    val vehicleModel: String,
    val speedMph: Int = 42,
    val coordinates: Pair<Double, Double> = Pair(34.0505, -118.2465)
)

data class Incident(
    val id: String,
    val status: String, // "dispatching" | "en_route" | "arrived" | "cancelled" | "resolved"
    val address: String,
    val targetHospitalId: String,
    val unit: AmbulanceUnit,
    val progressPercent: Int,
    val initialEtaMins: Int,
    val currentEtaMins: Int,
    val sosActivatedAt: String,
    val incidentType: String
)

data class DispatchHistoryItem(
    val id: String,
    val date: String,
    val time: String,
    val type: String, // "emergency_sos" | "hospital_transfer"
    val title: String,
    val location: String,
    val destinationHospital: String,
    val status: String,
    val unitAssigned: String,
    val responseTime: String
)

data class EmergencyContact(
    val id: String,
    val name: String,
    val relation: String,
    val phone: String,
    val isPrimary: Boolean
)

data class UserProfile(
    val name: String,
    val phone: String,
    val email: String,
    val bloodType: String,
    val allergies: List<String>,
    val medicalConditions: List<String>,
    val medications: List<String>,
    val emergencyContacts: List<EmergencyContact>,
    val insuranceProvider: String,
    val policyNumber: String,
    val organDonor: Boolean,
    val avatar: String = ""
)

data class TurnInstruction(
    val id: String,
    val instruction: String,
    val distance: String,
    val action: String // "straight" | "turn-right" | "turn-left" | "merge" | "arrive"
)

sealed class ScreenDestination {
    object Home : ScreenDestination()
    object Hospitals : ScreenDestination()
    object History : ScreenDestination()
    object Profile : ScreenDestination()
    data class HospitalDetails(val hospitalId: String) : ScreenDestination()
    data class IncidentDetails(val incidentId: String) : ScreenDestination()
    data class Navigation(val hospitalId: String) : ScreenDestination()
}
