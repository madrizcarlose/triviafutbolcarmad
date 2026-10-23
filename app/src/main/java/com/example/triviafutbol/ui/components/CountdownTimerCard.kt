package com.example.triviafutbol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun CountdownTimerCard(
    modifier: Modifier = Modifier
) {
    var millisUntilMidnight by remember { mutableLongStateOf(getMillisUntilMidnight()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            millisUntilMidnight = getMillisUntilMidnight()
        }
    }

    val hours = (millisUntilMidnight / (1000 * 60 * 60)) % 24
    val minutes = (millisUntilMidnight / (1000 * 60)) % 60
    val seconds = (millisUntilMidnight / 1000) % 60

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, GoldStreak.copy(alpha = 0.6f), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreenContainer)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏆 DESAFÍO COMPLETADO",
                    style = MaterialTheme.typography.titleMedium,
                    color = PitchGreen,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkCardSurface)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Mañana 00:00 hs",
                        style = MaterialTheme.typography.labelLarge,
                        color = GoldStreak
                    )
                }
            }

            Text(
                text = "¡Excelente jugada hoy! Próximo desafío disponible en:",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Reloj Contador de Tiempo HH : MM : SS
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimerBox(value = String.format("%02d", hours), label = "HORAS")
                Text(":", fontSize = 24.sp, color = GoldStreak, fontWeight = FontWeight.Bold)
                TimerBox(value = String.format("%02d", minutes), label = "MINS")
                Text(":", fontSize = 24.sp, color = GoldStreak, fontWeight = FontWeight.Bold)
                TimerBox(value = String.format("%02d", seconds), label = "SEGS")
            }
        }
    }
}

@Composable
private fun TimerBox(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkCardSurface)
                .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = GoldStreak,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = TextSecondary, fontSize = 10.sp)
    }
}

private fun getMillisUntilMidnight(): Long {
    val now = Calendar.getInstance()
    val midnight = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return midnight.timeInMillis - now.timeInMillis
}
