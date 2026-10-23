package com.example.triviafutbol.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

@Composable
fun AccuracyGaugeCard(
    accuracyPercentage: Int,
    accuracyLabel: String,
    correctAnswers: Int,
    totalAnswers: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardSurface)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Indicador Circular Animado de % de Efectividad
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { accuracyPercentage.toFloat() / 100f },
                    modifier = Modifier.size(80.dp),
                    color = PitchGreen,
                    strokeWidth = 7.dp,
                    trackColor = SurfaceBorder
                )

                Text(
                    text = "$accuracyPercentage%",
                    style = MaterialTheme.typography.titleLarge,
                    color = GoldStreak,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Efectividad en Gol",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                Text(
                    text = accuracyLabel,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "✅ $correctAnswers Aciertos de $totalAnswers Preguntas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PitchGreen,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            }
        }
    }
}
