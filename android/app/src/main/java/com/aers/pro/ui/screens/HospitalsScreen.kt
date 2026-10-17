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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.aers.pro.model.Hospital
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite
import com.aers.pro.ui.theme.TacticalGreen

@Composable
fun HospitalsScreen(
    hospitals: List<Hospital>,
    onSelectHospital: (String) -> Unit,
    onStartRoute: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filters = listOf("ALL", "ICU AVAIL", "LOW TRAFFIC", "LEVEL I")

    val filteredList = hospitals.filter { h ->
        val matchesSearch = h.name.contains(searchQuery, ignoreCase = true) ||
                h.city.contains(searchQuery, ignoreCase = true) ||
                h.specialties.any { it.contains(searchQuery, ignoreCase = true) }

        if (!matchesSearch) return@filter false

        when (selectedFilter) {
            "ICU AVAIL" -> h.icuStatus == "available"
            "LOW TRAFFIC" -> h.trafficStatus == "low"
            "LEVEL I" -> h.traumaLevel == "Level I"
            else -> true
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        "SEARCH MEDICAL FACILITIES...",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = SubtitleWhite
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PureWhite,
                    unfocusedBorderColor = CardBorder,
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
        }

        item {
            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) PureWhite else DarkSurface)
                            .border(1.dp, if (isSelected) PureWhite else CardBorder, RoundedCornerShape(20.dp))
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filter,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = if (isSelected) Black else SubtitleWhite
                        )
                    }
                }
            }
        }

        items(filteredList) { hospital ->
            HospitalCard(
                hospital = hospital,
                onSelect = { onSelectHospital(hospital.id) },
                onRoute = { onStartRoute(hospital.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun HospitalCard(
    hospital: Hospital,
    onSelect: () -> Unit,
    onRoute: () -> Unit
) {
    val isFastest = hospital.isFastest

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(DarkSurface)
            .border(
                width = if (isFastest) 2.dp else 1.dp,
                color = if (isFastest) PureWhite else CardBorder,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header: Fastest tag + Trauma Level
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isFastest) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(EmergencyOrange)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "FASTEST ROUTE",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            color = Black
                        )
                    }
                } else {
                    Text(
                        text = hospital.traumaLevel.uppercase(),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }

                Text(
                    text = "ETA: ${hospital.etaMins} MIN",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }

            // Hospital Name and Big ETA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = hospital.name.uppercase(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = PureWhite
                    )
                    Text(
                        text = hospital.address,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${hospital.etaMins}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = PureWhite
                    )
                    Text(
                        text = hospital.distance,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }
            }

            // Badges: ER, ICU, Traffic
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusBadge(
                    label = if (hospital.erStatus == "open") "ER OPEN" else "ER BUSY",
                    isGood = hospital.erStatus == "open"
                )
                StatusBadge(
                    label = if (hospital.icuStatus == "available") "ICU AVAIL" else "ICU FULL",
                    isGood = hospital.icuStatus == "available"
                )
                StatusBadge(
                    label = hospital.trafficText,
                    isGood = hospital.trafficStatus == "low"
                )
            }

            // Action button
            if (isFastest) {
                Button(
                    onClick = onRoute,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Directions,
                        contentDescription = null,
                        tint = Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "ROUTE NOW",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Black
                    )
                }
            } else {
                Button(
                    onClick = onSelect,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "VIEW DETAILS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = PureWhite
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(label: String, isGood: Boolean) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x1AFFFFFF))
            .border(0.5.dp, CardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(if (isGood) TacticalGreen else EmergencyOrange)
        )
        Text(
            text = label,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = PureWhite
        )
    }
}
