package com.aers.pro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aers.pro.model.ScreenDestination
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite

@Composable
fun AersBottomNav(
    currentScreen: ScreenDestination,
    onNavigate: (ScreenDestination) -> Unit,
    hasActiveSos: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Black)
                .border(1.dp, CardBorder, RoundedCornerShape(32.dp))
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "HOME",
                isSelected = currentScreen is ScreenDestination.Home,
                onClick = { onNavigate(ScreenDestination.Home) }
            )
            BottomNavItem(
                icon = Icons.Default.LocalHospital,
                label = "HOSPITALS",
                isSelected = currentScreen is ScreenDestination.Hospitals,
                badge = if (hasActiveSos) "SOS" else null,
                onClick = { onNavigate(ScreenDestination.Hospitals) }
            )
            BottomNavItem(
                icon = Icons.Default.History,
                label = "HISTORY",
                isSelected = currentScreen is ScreenDestination.History,
                onClick = { onNavigate(ScreenDestination.History) }
            )
            BottomNavItem(
                icon = Icons.Default.Person,
                label = "PROFILE",
                isSelected = currentScreen is ScreenDestination.Profile,
                onClick = { onNavigate(ScreenDestination.Profile) }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    badge: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) PureWhite else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (isSelected) Black else SubtitleWhite,
                    modifier = Modifier.size(20.dp)
                )
                if (badge != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(EmergencyOrange)
                    )
                }
            }
            if (isSelected) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Black,
                    color = Black
                )
            }
        }
    }
}
