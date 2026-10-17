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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.Dialog
import com.aers.pro.model.Incident
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite

@Composable
fun IncidentDetailsScreen(
    incident: Incident,
    onCallDriver: () -> Unit,
    onTextDriver: () -> Unit,
    onCancelRequest: (String) -> Unit,
    onViewLiveNavigation: () -> Unit
) {
    var showCancelDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top SOS Active Tag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(EmergencyOrange)
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Emergency,
                        contentDescription = null,
                        tint = Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "SOS DISPATCH ACTIVE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = Black,
                        letterSpacing = 1.sp
                    )
                }
            }

            // Tactical Coordinates & Telemetry Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(DarkSurface)
                    .border(1.dp, CardBorder, RoundedCornerShape(24.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "${incident.unit.unitNumber.uppercase()} // INTERCEPT ROUTE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = EmergencyOrange
                    )
                    Text(
                        text = "LOC: 34.0522° N, 118.2437° W",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = PureWhite
                    )
                    Text(
                        text = "SPEED: ${incident.unit.speedMph} MPH • EMERGENCY CODE 3",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }
            }

            // Floating Bottom HUD Sheet
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(DarkSurface)
                    .border(1.dp, CardBorder, RoundedCornerShape(32.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Header: Intercept Status + ETA Timer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "INTERCEPT STATUS",
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                color = SubtitleWhite,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "AMBULANCE EN ROUTE",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = PureWhite
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = EmergencyOrange,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = incident.address,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = SubtitleWhite
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "0${incident.currentEtaMins}",
                                fontSize = 44.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = PureWhite,
                                lineHeight = 44.sp
                            )
                            Text(
                                text = "MINUTES",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = SubtitleWhite
                            )
                        }
                    }

                    // Progress Bar
                    LinearProgressIndicator(
                        progress = { incident.progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = PureWhite,
                        trackColor = Color(0x22FFFFFF)
                    )

                    // Paramedic Card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x11FFFFFF))
                            .border(0.5.dp, CardBorder, RoundedCornerShape(20.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x22FFFFFF))
                                    .border(1.dp, CardBorder, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "DM",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace,
                                    color = PureWhite
                                )
                            }
                            Column {
                                Text(
                                    text = "RESPONDER",
                                    fontSize = 8.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = SubtitleWhite
                                )
                                Text(
                                    text = incident.unit.paramedicName.uppercase(),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureWhite
                                )
                                Text(
                                    text = incident.unit.paramedicRole,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = SubtitleWhite
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x1AFFFFFF))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = PureWhite,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "${incident.unit.paramedicRating}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = PureWhite
                            )
                        }
                    }

                    // Call and Text Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onCallDriver,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, tint = Black, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.size(6.dp))
                            Text("CALL DRIVER", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Black)
                        }

                        Button(
                            onClick = onTextDriver,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.size(6.dp))
                            Text("TEXT DRIVER", fontSize = 11.sp, fontWeight = FontWeight.Black, color = PureWhite)
                        }
                    }

                    // Live Tactical Nav button
                    Button(
                        onClick = onViewLiveNavigation,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x1AFFFFFF)),
                        shape = RoundedCornerShape(23.dp)
                    ) {
                        Icon(Icons.Default.Navigation, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "VIEW LIVE TACTICAL NAVIGATION ROUTE",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }

                    // Cancel Link
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(onClick = { showCancelDialog = true }) {
                            Text(
                                text = "[CANCEL EMERGENCY REQUEST]",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = SubtitleWhite
                            )
                        }
                    }
                }
            }
        }

        // Cancel Confirmation Dialog
        if (showCancelDialog) {
            Dialog(onDismissRequest = { showCancelDialog = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp))
                        .background(DarkSurface)
                        .border(1.dp, CardBorder, RoundedCornerShape(28.dp))
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "ABORT DISPATCH",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = EmergencyOrange
                        )
                        Text(
                            text = "CANCEL EMERGENCY?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = PureWhite
                        )
                        Text(
                            text = "Unit 42 is currently moving toward your location at 1245 Main St. Are you sure the emergency is resolved?",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { showCancelDialog = false },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Text("KEEP ACTIVE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PureWhite)
                            }
                            Button(
                                onClick = {
                                    showCancelDialog = false
                                    onCancelRequest(incident.id)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmergencyOrange),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Text("CONFIRM CANCEL", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Black)
                            }
                        }
                    }
                }
            }
        }
    }
}
