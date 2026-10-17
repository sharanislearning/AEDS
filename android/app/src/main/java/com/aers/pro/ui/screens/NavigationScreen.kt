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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.TurnRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.aers.pro.data.MockData
import com.aers.pro.model.Hospital
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite
import com.aers.pro.ui.theme.TacticalGreen

@Composable
fun NavigationScreen(
    hospital: Hospital,
    onEndNavigation: () -> Unit,
    onSwitchToHospitalDetails: () -> Unit
) {
    var stepIndex by remember { mutableIntStateOf(0) }
    var isSirenOn by remember { mutableStateOf(true) }

    val steps = MockData.turnSteps
    val currentStep = steps[stepIndex % steps.size]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top HUD Maneuver Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(DarkSurface)
                .border(2.dp, PureWhite, RoundedCornerShape(28.dp))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(PureWhite),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TurnRight,
                        contentDescription = "Turn Right",
                        tint = Black,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "IN ${currentStep.distance}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = EmergencyOrange
                    )
                    Text(
                        text = currentStep.instruction,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = PureWhite
                    )
                }

                IconButton(
                    onClick = { stepIndex = (stepIndex + 1) % steps.size },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x1AFFFFFF))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Turn",
                        tint = PureWhite,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Center Speedometer & Code 3 Indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Speedometer HUD
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(DarkSurface)
                    .border(1.dp, CardBorder, RoundedCornerShape(24.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "LIVE TELEMETRY",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "48",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = PureWhite
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "MPH",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                    }
                    Text(
                        text = "SPEED LIMIT: 35 (EXEMPT)",
                        fontSize = 8.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TacticalGreen
                    )
                }
            }

            // Siren / Emergency Beacon Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (isSirenOn) Color(0x33FF3E00) else DarkSurface)
                    .border(1.dp, if (isSirenOn) EmergencyOrange else CardBorder, RoundedCornerShape(24.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "AUDIO BEACON",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = if (isSirenOn) EmergencyOrange else SubtitleWhite,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = if (isSirenOn) "CODE 3 SIREN" else "SIREN MUTED",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = PureWhite
                    )
                    Button(
                        onClick = { isSirenOn = !isSirenOn },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSirenOn) PureWhite else Color(0x22FFFFFF)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    ) {
                        Text(
                            text = if (isSirenOn) "MUTE" else "ACTIVATE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = if (isSirenOn) Black else PureWhite
                        )
                    }
                }
            }
        }

        // Bottom Dashboard Drawer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(32.dp))
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DESTINATION",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                        Text(
                            text = hospital.name.uppercase(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = PureWhite
                        )
                        Text(
                            text = "DISTANCE REMAINING: ${hospital.distance}",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${hospital.etaMins}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = PureWhite
                        )
                        Text(
                            text = "MINS",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onSwitchToHospitalDetails,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x1AFFFFFF)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text("FACILITY INFO", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PureWhite)
                    }

                    Button(
                        onClick = onEndNavigation,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text("END NAV", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Black)
                    }
                }
            }
        }
    }
}
