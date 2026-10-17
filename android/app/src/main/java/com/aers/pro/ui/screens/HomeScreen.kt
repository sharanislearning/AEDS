package com.aers.pro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aers.pro.model.Hospital
import com.aers.pro.model.Incident
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite
import com.aers.pro.ui.theme.TacticalGreen

@Composable
fun HomeScreen(
    hospitals: List<Hospital>,
    activeIncident: Incident?,
    onTriggerSos: () -> Unit,
    onFindHospitals: () -> Unit,
    onSelectHospital: (String) -> Unit,
    onSelectIncident: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Telemetry status strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(TacticalGreen)
                )
                Text(
                    text = "GPS LOCK: 34.0522° N, 118.2437° W",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    color = PureWhite
                )
            }
            Text(
                text = "SYSTEM NOMINAL",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = TacticalGreen
            )
        }

        // Active SOS Banner if active
        if (activeIncident != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(EmergencyOrange)
                    .clickable { onSelectIncident(activeIncident.id) }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Emergency,
                            contentDescription = "Active Emergency",
                            tint = Black,
                            modifier = Modifier.size(28.dp)
                        )
                        Column {
                            Text(
                                text = "ACTIVE SOS DISPATCH",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Black
                            )
                            Text(
                                text = "UNIT 42 EN ROUTE • ETA 4 MINS",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                        }
                    }
                    Text(
                        text = "VIEW >",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = Black
                    )
                }
            }
        }

        // Huge Brutalist SOS Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(DarkSurface)
                .border(2.dp, if (activeIncident != null) EmergencyOrange else PureWhite, RoundedCornerShape(32.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "CRITICAL RESPONSE TRIGGER",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 2.sp,
                    color = SubtitleWhite
                )

                // Large SOS Action Trigger
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .background(EmergencyOrange)
                        .clickable { onTriggerSos() },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "SOS",
                            tint = Black,
                            modifier = Modifier.size(44.dp)
                        )
                        Text(
                            text = "SOS",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = Black,
                            letterSpacing = (-1).sp
                        )
                        Text(
                            text = "CODE 3 DISPATCH",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                    }
                }

                Text(
                    text = "TAP TO TRANSMIT LIVE GPS & MEDICAL ID TO CLOSEST ALS UNIT",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    color = SubtitleWhite,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }

        // Quick Actions Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onFindHospitals,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocalHospital,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "HOSPITALS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Black
                )
            }

            Button(
                onClick = {
                    val fastest = hospitals.find { it.isFastest } ?: hospitals.first()
                    onSelectHospital(fastest.id)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.NearMe,
                    contentDescription = null,
                    tint = PureWhite,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "FASTEST ROUTE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = PureWhite
                )
            }
        }

        // Nearest Trauma Facility Card Preview
        val primaryHospital = hospitals.find { it.isFastest } ?: hospitals.first()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(28.dp))
                .clickable { onSelectHospital(primaryHospital.id) }
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(EmergencyOrange)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "PRIMARY INTERCEPT",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            color = Black
                        )
                    }

                    Text(
                        text = "ETA: ${primaryHospital.etaMins} MINS",
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = primaryHospital.traumaLevel.uppercase(),
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                        Text(
                            text = primaryHospital.name.uppercase(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = PureWhite
                        )
                    }

                    Text(
                        text = primaryHospital.distance,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(72.dp))
    }
}
