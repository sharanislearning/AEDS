package com.aers.pro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.aers.pro.ui.theme.Black
import com.aers.pro.ui.theme.CardBorder
import com.aers.pro.ui.theme.DarkSurface
import com.aers.pro.ui.theme.EmergencyOrange
import com.aers.pro.ui.theme.PureWhite
import com.aers.pro.ui.theme.SubtitleWhite

@Composable
fun CallDialog(
    paramedicName: String,
    unitNumber: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(32.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "ENCRYPTED TACTICAL DISPATCH // COMMS",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    color = SubtitleWhite,
                    letterSpacing = 1.sp
                )

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0x22FFFFFF))
                        .border(2.dp, PureWhite, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Active Call",
                        tint = PureWhite,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = paramedicName.uppercase(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = PureWhite
                    )
                    Text(
                        text = "$unitNumber • AES-256 SECURE LINE",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SubtitleWhite
                    )
                    Text(
                        text = "CALL CONNECTED • 00:34",
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = EmergencyOrange,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color(0x1AFFFFFF))
                    ) {
                        Icon(Icons.Default.Mic, contentDescription = "Mute", tint = PureWhite)
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color(0x1AFFFFFF))
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Speaker", tint = PureWhite)
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(EmergencyOrange)
                    ) {
                        Icon(Icons.Default.CallEnd, contentDescription = "End Call", tint = Black)
                    }
                }
            }
        }
    }
}

@Composable
fun TextDialog(
    paramedicName: String,
    unitNumber: String,
    onDismiss: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            "Unit 42 en route to 1245 Main St. ETA 4 minutes.",
            "Please confirm if the building entrance is unobstructed."
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(DarkSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(32.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DIRECT SATELLITE COMMS",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = SubtitleWhite
                        )
                        Text(
                            text = "$paramedicName ($unitNumber)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = PureWhite
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PureWhite)
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    messages.forEach { msg ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0x1AFFFFFF))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = msg,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace,
                                color = PureWhite
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = {
                            Text(
                                "TYPE SITREP UPDATE...",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = SubtitleWhite
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PureWhite,
                            unfocusedBorderColor = CardBorder,
                            focusedTextColor = PureWhite,
                            unfocusedTextColor = PureWhite
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )

                    Button(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                messages.add("USER: $messageText")
                                messageText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = Black)
                    }
                }
            }
        }
    }
}
