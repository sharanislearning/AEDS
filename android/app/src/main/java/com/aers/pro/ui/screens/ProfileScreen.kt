package com.aers.pro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aers.pro.model.UserProfile
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite
import com.aers.pro.ui.theme.TacticalGreen

@Composable
fun ProfileScreen(
    profile: UserProfile,
    onUpdateProfile: (UserProfile) -> Unit
) {
    var autoTransmit by remember { mutableStateOf(true) }
    var organDonor by remember { mutableStateOf(profile.organDonor) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Identity Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(28.dp))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0x22FFFFFF))
                        .border(2.dp, PureWhite, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AM",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = PureWhite
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = profile.name.uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = PureWhite
                        )
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = TacticalGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = profile.phone,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                    Text(
                        text = profile.email,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }
            }
        }

        // Emergency Medical ID Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(28.dp))
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "EMERGENCY MEDICAL ID",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite,
                        letterSpacing = 1.sp
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(EmergencyOrange)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = profile.bloodType,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            color = Black
                        )
                    }
                }

                // Allergies
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "CRITICAL ALLERGIES",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                    Text(
                        text = profile.allergies.joinToString(", ").uppercase(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = PureWhite
                    )
                }

                // Medical Conditions
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "MEDICAL CONDITIONS",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                    Text(
                        text = profile.medicalConditions.joinToString(" • ").uppercase(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = PureWhite
                    )
                }

                // Current Medications
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "ACTIVE MEDICATIONS",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                    Text(
                        text = profile.medications.joinToString(" • ").uppercase(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = PureWhite
                    )
                }
            }
        }

        // Emergency Contacts
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(28.dp))
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "EMERGENCY CONTACTS (AUTO-NOTIFY ON SOS)",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    color = SubtitleWhite
                )

                profile.emergencyContacts.forEach { contact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0x11FFFFFF))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${contact.name.uppercase()} (${contact.relation.uppercase()})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = PureWhite
                            )
                            Text(
                                text = contact.phone,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = SubtitleWhite
                            )
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", tint = Black, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // Preferences Toggles: Auto transmit & Organ Donor
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "AUTO-TRANSMIT MEDICAL ID",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Broadcast allergies & blood type to ALS dispatchers immediately",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                    }
                    Switch(
                        checked = autoTransmit,
                        onCheckedChange = { autoTransmit = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Black,
                            checkedTrackColor = PureWhite,
                            uncheckedThumbColor = SubtitleWhite,
                            uncheckedTrackColor = Color(0x22FFFFFF)
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ORGAN DONOR REGISTRY",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Verified donor badge on state registry",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                    }
                    Switch(
                        checked = organDonor,
                        onCheckedChange = {
                            organDonor = it
                            onUpdateProfile(profile.copy(organDonor = it))
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Black,
                            checkedTrackColor = PureWhite,
                            uncheckedThumbColor = SubtitleWhite,
                            uncheckedTrackColor = Color(0x22FFFFFF)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(72.dp))
    }
}
