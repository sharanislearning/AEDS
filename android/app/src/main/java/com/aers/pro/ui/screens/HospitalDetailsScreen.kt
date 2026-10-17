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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aers.pro.model.Hospital
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite

@Composable
fun HospitalDetailsScreen(
    hospital: Hospital,
    onStartNavigation: (String) -> Unit,
    onViewOtherHospitals: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Upper Card: ETA & Facility Stats
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(32.dp))
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "ESTIMATED ARRIVAL TIME",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    color = SubtitleWhite,
                    letterSpacing = 1.5.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "${hospital.etaMins}",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = PureWhite,
                                lineHeight = 48.sp
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(
                                text = "MINS",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = SubtitleWhite
                            )
                        }
                        Text(
                            text = hospital.name.uppercase(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = PureWhite
                        )
                        Text(
                            text = "${hospital.address}, ${hospital.city}",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(26.dp))
                            .background(Color(0x1AFFFFFF))
                            .border(1.dp, CardBorder, RoundedCornerShape(26.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Directions,
                            contentDescription = null,
                            tint = PureWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Grid of Specs
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SpecBox(
                            label = "TRAUMA LEVEL",
                            value = hospital.traumaLevel,
                            modifier = Modifier.weight(1f)
                        )
                        SpecBox(
                            label = "ER WAIT TIME",
                            value = "~${hospital.waitTimeMins} MINS",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SpecBox(
                            label = "ICU BEDS",
                            value = "${hospital.icuBedsAvailable} FREE",
                            modifier = Modifier.weight(1f)
                        )
                        SpecBox(
                            label = "TRAFFIC",
                            value = hospital.trafficText,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Hotline
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0x11FFFFFF))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = EmergencyOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "DISPATCH HOTLINE",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                    }
                    Text(
                        text = hospital.phone,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }
        }

        // Bottom Navigation Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { onStartNavigation(hospital.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Navigation,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "START NAVIGATION",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Black,
                    letterSpacing = 1.sp
                )
            }

            Button(
                onClick = onViewOtherHospitals,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "← VIEW OTHER HOSPITALS",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = SubtitleWhite
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SpecBox(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x0FFFFFFF))
            .border(0.5.dp, CardBorder, RoundedCornerShape(16.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = label,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                color = SubtitleWhite
            )
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = PureWhite,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
