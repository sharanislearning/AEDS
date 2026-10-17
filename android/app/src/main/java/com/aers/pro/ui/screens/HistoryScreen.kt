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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aers.pro.model.DispatchHistoryItem
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite
import com.aers.pro.ui.theme.TacticalGreen

@Composable
fun HistoryScreen(
    history: List<DispatchHistoryItem>,
    onSelectHospitalByName: (String) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("ALL") }
    val filters = listOf("ALL", "SOS DISPATCHES", "TRANSFERS")

    val filteredList = history.filter { item ->
        when (selectedFilter) {
            "SOS DISPATCHES" -> item.type == "emergency_sos"
            "TRANSFERS" -> item.type == "hospital_transfer"
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
            // Security verification banner
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
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = SubtitleWhite,
                        modifier = Modifier.padding(2.dp)
                    )
                    Text(
                        text = "ENCRYPTED ARCHIVES // NIST FIPS-140",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }
                Text(
                    text = "${history.size} LOGS",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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

        items(filteredList) { item ->
            HistoryCard(item = item, onClick = { onSelectHospitalByName(item.destinationHospital) })
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun HistoryCard(
    item: DispatchHistoryItem,
    onClick: () -> Unit
) {
    val isSos = item.type == "emergency_sos"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(DarkSurface)
            .border(1.dp, CardBorder, RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSos) EmergencyOrange else PureWhite)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isSos) "SOS DISPATCH" else "TRANSFER",
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            color = Black
                        )
                    }
                    Text(
                        text = item.date,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = TacticalGreen
                    )
                    Text(
                        text = item.status.uppercase(),
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = TacticalGreen
                    )
                }
            }

            Column {
                Text(
                    text = item.title.uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = PureWhite
                )
                Text(
                    text = "TARGET: ${item.destinationHospital.uppercase()}",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = SubtitleWhite
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "UNIT: ${item.unitAssigned}",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    color = SubtitleWhite
                )
                Text(
                    text = item.responseTime,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }
        }
    }
}
