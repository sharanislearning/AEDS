package com.aers.pro.data

import com.aers.pro.model.AmbulanceUnit
import com.aers.pro.model.DispatchHistoryItem
import com.aers.pro.model.EmergencyContact
import com.aers.pro.model.Hospital
import com.aers.pro.model.TurnInstruction
import com.aers.pro.model.UserProfile

object MockData {

    val primaryParamedic = AmbulanceUnit(
        id = "unit-42",
        unitNumber = "ALS Unit 42",
        paramedicName = "Rohan Deshmukh",
        paramedicRole = "Lead Paramedic • MBMC EMS Unit 42",
        paramedicRating = 4.9,
        avatar = "https://images.unsplash.com/photo-1622253692010-333f2da6031d?w=300&auto=format&fit=crop&q=80",
        phone = "+91 98200 42911",
        vehicleModel = "Force Traveller Advanced Life Support Ambulance",
        speedMph = 44,
        coordinates = Pair(19.2847, 72.8641)
    )

    val hospitals = listOf(
        Hospital(
            id = "bhaktivedanta",
            name = "Bhaktivedanta Hospital & Research Institute",
            address = "Srishti Complex, Bhaktivedanta Swami Marg, Sector 1, Mira Road East",
            city = "Mira Road, Thane",
            distance = "1.1 km",
            etaMins = 4,
            traumaLevel = "Level I",
            erStatus = "open",
            icuStatus = "available",
            icuBedsAvailable = 8,
            waitTimeMins = 3,
            trafficStatus = "low",
            trafficText = "LOW TRAFFIC",
            phone = "+91 22 2945 2500",
            isFastest = true,
            specialties = listOf("24/7 Cardiac Emergency", "Critical Care ICU", "Accident & Trauma", "Cardiac Ambulance")
        ),
        Hospital(
            id = "wockhardt-mira",
            name = "Wockhardt Super Speciality Hospital",
            address = "The Umrao IMSR, Near Railway Station, Mira Road East",
            city = "Mira Road, Thane",
            distance = "2.4 km",
            etaMins = 7,
            traumaLevel = "Level I",
            erStatus = "open",
            icuStatus = "available",
            icuBedsAvailable = 11,
            waitTimeMins = 6,
            trafficStatus = "moderate",
            trafficText = "MODERATE TRAFFIC",
            phone = "+91 81081 81081",
            isFastest = false,
            specialties = listOf("Comprehensive Stroke Center", "Neuro Trauma", "Cardiology", "Emergency Care")
        ),
        Hospital(
            id = "ashish-multi",
            name = "Ashish Multispeciality Hospital",
            address = "Shanti Nagar, Sector 4, Mira Road East",
            city = "Mira Road, Thane",
            distance = "1.8 km",
            etaMins = 6,
            traumaLevel = "Level II",
            erStatus = "open",
            icuStatus = "available",
            icuBedsAvailable = 4,
            waitTimeMins = 5,
            trafficStatus = "low",
            trafficText = "CLEAR CORRIDOR",
            phone = "+91 22 2811 5599",
            isFastest = false,
            specialties = listOf("24x7 Critical Care", "Trauma Resuscitation", "General Surgery", "Pulmonology")
        ),
        Hospital(
            id = "thunga-hospital",
            name = "Thunga Multispeciality Hospital",
            address = "Beverly Park, Kanakia Road, Mira Road East",
            city = "Mira Road, Thane",
            distance = "3.2 km",
            etaMins = 9,
            traumaLevel = "Level II",
            erStatus = "open",
            icuStatus = "limited",
            icuBedsAvailable = 2,
            waitTimeMins = 11,
            trafficStatus = "moderate",
            trafficText = "MODERATE TRAFFIC",
            phone = "+91 22 2810 1200",
            isFastest = false,
            specialties = listOf("Accident Emergency", "Orthopedic Surgery", "Cardiology", "ICU Care")
        ),
        Hospital(
            id = "shree-siddhivinayak",
            name = "Shree Siddhivinayak Multispeciality",
            address = "Near Shanti Park, Off Western Express Highway, Mira Road East",
            city = "Mira Road, Thane",
            distance = "2.1 km",
            etaMins = 8,
            traumaLevel = "Level II",
            erStatus = "open",
            icuStatus = "available",
            icuBedsAvailable = 3,
            waitTimeMins = 7,
            trafficStatus = "low",
            trafficText = "CLEAR ROUTE",
            phone = "+91 22 2812 3636",
            isFastest = false,
            specialties = listOf("24-Hour Emergency", "Trauma Care", "Intensive Care Unit", "Pediatrics")
        ),
        Hospital(
            id = "mira-superspeciality",
            name = "MIRA Super Speciality Hospital",
            address = "Flyover Junction, Mira Bhayandar Highway, Bhayandar East",
            city = "Mira Bhayandar",
            distance = "4.5 km",
            etaMins = 13,
            traumaLevel = "Level I",
            erStatus = "open",
            icuStatus = "available",
            icuBedsAvailable = 6,
            waitTimeMins = 10,
            trafficStatus = "moderate",
            trafficText = "MED TRAFFIC",
            phone = "+91 22 2818 9000",
            isFastest = false,
            specialties = listOf("24/7 ER & Advanced Ambulance", "Cardio-Thoracic", "Neurosurgery", "Dialysis")
        )
    )

    val userProfile = UserProfile(
        name = "Sharan Moolya",
        phone = "+91 98334 12044",
        email = "sharankumar.v.moolya24@slrtce.in",
        bloodType = "O-NEGATIVE (O-)",
        allergies = listOf("Penicillin", "Sulfa Antibiotics", "Latex"),
        medicalConditions = listOf("Mild Asthma (Exercise-induced)"),
        medications = listOf("Albuterol Inhaler (PRN)", "Daily Multivitamin"),
        emergencyContacts = listOf(
            EmergencyContact(
                id = "c1",
                name = "Venkatesh Moolya",
                relation = "Family",
                phone = "+91 98201 55432",
                isPrimary = true
            ),
            EmergencyContact(
                id = "c2",
                name = "Dr. A. K. Sharma",
                relation = "Primary Physician",
                phone = "+91 22 2811 7700",
                isPrimary = false
            )
        ),
        insuranceProvider = "Star Health Emergency Comprehensive",
        policyNumber = "SH-MRD-8892104-X",
        organDonor = true,
        avatar = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=300&auto=format&fit=crop&q=80"
    )

    val history = listOf(
        DispatchHistoryItem(
            id = "disp-1092",
            date = "SEP 20, 2026",
            time = "14:22 IST",
            type = "emergency_sos",
            title = "Code 3 Trauma Response",
            location = "Srishti Complex, Mira Road East",
            destinationHospital = "Bhaktivedanta Hospital & Research Institute",
            status = "Completed",
            unitAssigned = "ALS Unit 42 (Rohan D.)",
            responseTime = "3m 36s Intercept"
        ),
        DispatchHistoryItem(
            id = "disp-1088",
            date = "AUG 29, 2026",
            time = "09:15 IST",
            type = "hospital_transfer",
            title = "Urgent Cardiology Transport",
            location = "Shanti Park Junction, Mira Road",
            destinationHospital = "Wockhardt Super Speciality Hospital",
            status = "Completed",
            unitAssigned = "Unit 18 (Cardiac Care)",
            responseTime = "5m 12s Arrival"
        ),
        DispatchHistoryItem(
            id = "disp-1074",
            date = "JUL 11, 2026",
            time = "21:04 IST",
            type = "emergency_sos",
            title = "Severe Respiratory Distress",
            location = "Kanakia Road, Beverly Park",
            destinationHospital = "Thunga Multispeciality Hospital",
            status = "Completed",
            unitAssigned = "MBMC Unit 17 (ALS)",
            responseTime = "4m 02s Intercept"
        )
    )

    val turnSteps = listOf(
        TurnInstruction("t1", "HEAD NORTH ON SRISHTI MAIN RD TOWARD BHAKTIVEDANTA MARG", "350 M", "straight"),
        TurnInstruction("t2", "TURN RIGHT AT ROYAL COLLEGE JUNCTION ONTO MIRA BHAYANDAR RD", "600 M", "turn-right"),
        TurnInstruction("t3", "KEEP RIGHT ON WESTERN EXPRESS HIGHWAY CONNECTOR CORRIDOR", "1.2 KM", "merge"),
        TurnInstruction("t4", "TAKE LEFT RAMP TOWARD SECTOR 1 TRAUMA BAY", "250 M", "turn-left"),
        TurnInstruction("t5", "ARRIVE AT BHAKTIVEDANTA HOSPITAL EMERGENCY BAY (GATE 2)", "50 M", "arrive")
    )
}
