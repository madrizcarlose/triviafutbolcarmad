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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.domain.model.Achievement
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

@Composable
fun AchievementCard(
    achievement: Achievement,
    modifier: Modifier = Modifier
) {
    val isGoldenBoot = achievement.id == "ach_1" || achievement.title.contains("Bota de Oro")
    val borderColor = if (achievement.isUnlocked || isGoldenBoot) GoldStreak else SurfaceBorder
    val containerBg = if (achievement.isUnlocked) DarkGreenContainer else DarkCardSurface

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerBg)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Insignia Dorada para Bota de Oro o Trofeos Desbloqueados
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(if (achievement.isUnlocked || isGoldenBoot) GoldStreak.copy(alpha = 0.25f) else DarkCardSurface)
                    .border(1.5.dp, if (achievement.isUnlocked || isGoldenBoot) GoldStreak else SurfaceBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = achievement.icon,
                    fontSize = 28.sp
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = achievement.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (achievement.isUnlocked || isGoldenBoot) GoldStreak else TextPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = if (achievement.isUnlocked) "DESBLOQUEADO 🏆" else "${achievement.currentProgress}/${achievement.targetProgress}",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (achievement.isUnlocked) PitchGreen else TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Barra de Progreso del Logro
                LinearProgressIndicator(
                    progress = { achievement.progressRatio },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (achievement.isUnlocked || isGoldenBoot) GoldStreak else PitchGreen,
                    trackColor = SurfaceBorder
                )
            }
        }
    }
}
