# AERS Pro — Native Android App (Kotlin & Android SDK)

A tactical emergency response and hospital routing application built natively with **Kotlin**, **Jetpack Compose (Material 3)**, and **Android SDK 35/36**.

---

## 📱 Project Architecture

- **Language & Runtime:** Kotlin 2.0+ / Android SDK 35 (minSdk 26, targetSdk 35)
- **UI Toolkit:** Jetpack Compose + Material 3 with Custom Brutalist Dark Theme
- **State & Lifecycle:** `ViewModel`, Kotlin Coroutines `StateFlow`, `collectAsState()`
- **Navigation:** Single-Activity Compose architecture (`MainActivity.kt` with `ScreenDestination` sealed state hierarchy)
- **Data Layer:** `AersRepository` with observable reactive flows and in-memory cache

---

## 📂 Source Code Structure

```
android/
├── build.gradle.kts                          # Root Gradle build script
├── settings.gradle.kts                       # Root project settings
├── gradle/
│   ├── libs.versions.toml                   # Version catalog (AGP, Compose, Kotlin)
│   └── wrapper/gradle-wrapper.properties     # Gradle 8.11 wrapper
└── app/
    ├── build.gradle.kts                      # Application module dependencies & Compose config
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml               # Permissions (Location, Comms, Internet)
        ├── res/
        │   └── values/
        │       ├── colors.xml                # Tactical color palette
        │       ├── strings.xml
        │       └── themes.xml                # Fullscreen dark window theme
        └── java/com/aers/pro/
            ├── AersApplication.kt            # Application entry
            ├── MainActivity.kt               # Main Activity hosting Compose Scaffold
            ├── model/
            │   └── Models.kt                 # Hospital, Incident, AmbulanceUnit, UserProfile data models
            ├── data/
            │   ├── MockData.kt               # Metro area trauma facilities and response units
            │   └── AersRepository.kt         # Reactive state flow repository
            ├── viewmodel/
            │   └── AersViewModel.kt          # Dispatch ViewModel handling SOS, Comms & Nav
            ├── ui/
            │   ├── theme/
            │   │   ├── Color.kt              # Brutalist black (#000000), Emergency Orange (#FF3E00)
            │   │   ├── Type.kt               # High-contrast typographic scales
            │   │   └── Theme.kt              # AERSProTheme Material 3 wrapper
            │   ├── components/
            │   │   ├── AersTopBar.kt         # Tactical telemetry header
            │   │   ├── AersBottomNav.kt      # Floating pill bottom navigation
            │   │   └── CommunicationModals.kt# Encrypted Comms & Tactical Text dialogs
            │   └── screens/
            │       ├── HomeScreen.kt         # Code 3 SOS Trigger & quick status
            │       ├── HospitalsScreen.kt    # Facility list, filters (ICU, Level I)
            │       ├── HospitalDetailsScreen.kt # ETA specs & trauma readiness
            │       ├── IncidentDetailsScreen.kt # Live ambulance tracker & paramedic card
            │       ├── NavigationScreen.kt   # Turn-by-turn HUD, MPH speedometer & siren toggle
            │       ├── HistoryScreen.kt      # Encrypted NIST FIPS dispatch archives
            │       └── ProfileScreen.kt      # Emergency Medical ID & verified contacts
```

---

## 🚀 How to Run in Android Studio

1. Open **Android Studio** (Ladybug / Koala / Hedgehog or newer with JDK 17+).
2. Select **Open** and select the `/android` directory.
3. Allow Gradle to sync dependencies via the version catalog (`libs.versions.toml`).
4. Select an Android Virtual Device (AVD) running API 26 or higher (API 34/35 recommended).
5. Click **Run ▶** or press `Shift + F10`.

---

## 🛠️ CLI Build

```bash
cd android
./gradlew assembleDebug
```
The compiled debug APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`
