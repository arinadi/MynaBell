package com.myna.bell.ui.wakeup

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myna.bell.ui.radio.MynaBlack
import com.myna.bell.ui.radio.MynaGold
import com.myna.bell.ui.radio.MynaSurface
import com.myna.bell.ui.radio.MynaWhite
import com.myna.bell.util.TotpGenerator
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun WakeUpScreen(
    onDismiss: () -> Unit
) {
    // Secret handling should be more robust in prod (EncryptedSharedPreferences), 
    // for MVP we use a hardcoded or randomly generated session secret.
    val secret = remember { "MYNABELL_SECRET".toByteArray() }
    
    var currentTotp by remember { mutableStateOf("") }
    var inputCode by remember { mutableStateOf("") }
    var timeLeft by remember { mutableFloatStateOf(30f) }
    var shakeTrigger by remember { mutableStateOf(false) }

    // Timer Loop
    LaunchedEffect(Unit) {
        while (true) {
            val now = System.currentTimeMillis() / 1000
            currentTotp = TotpGenerator.generateTOTP(secret, now)
            timeLeft = 30f - (now % 30)
            delay(1000)
        }
    }

    // Verify Loop
    LaunchedEffect(inputCode) {
        if (inputCode.length == 6) {
            if (inputCode == currentTotp) {
                onDismiss()
            } else {
                shakeTrigger = true
                delay(500)
                shakeTrigger = false
                inputCode = ""
            }
        }
    }

    val shakeOffset by animateFloatAsState(
        targetValue = if (shakeTrigger) 10f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium),
        label = "shake"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MynaBlack)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "07:00", // Needs actual time from system
            style = MaterialTheme.typography.displayLarge.copy(fontSize = 80.sp, fontWeight = FontWeight.Bold),
            color = MynaWhite
        )
        
        Text(
            text = "LIVE • JAZZ FM LONDON", // metadata Placeholder
            style = MaterialTheme.typography.labelLarge,
            color = MynaGold,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

        // TOTP Display
        Card(
            colors = CardDefaults.cardColors(containerColor = MynaSurface),
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = shakeOffset.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("ENTER VERIFICATION CODE", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                Text(
                    text = currentTotp,
                    style = MaterialTheme.typography.displayMedium.copy(fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace),
                    color = MynaGold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                
                LinearProgressIndicator(
                    progress = { timeLeft / 30f },
                    modifier = Modifier.fillMaxWidth().height(4.dp),
                    color = MynaGold,
                    trackColor = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Input Dots
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    repeat(6) { index ->
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (index < inputCode.length) MynaWhite else Color.DarkGray)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Numpad
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            val rows = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("Del", "0", "")
            )
            
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { digit ->
                        if (digit.isNotEmpty()) {
                            NumpadButton(text = digit) {
                                if (digit == "Del") {
                                    if (inputCode.isNotEmpty()) inputCode = inputCode.dropLast(1)
                                } else if (inputCode.length < 6) {
                                    inputCode += digit
                                }
                            }
                        } else {
                            Box(modifier = Modifier.size(80.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NumpadButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(MynaSurface)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineSmall, color = MynaWhite)
    }
}
