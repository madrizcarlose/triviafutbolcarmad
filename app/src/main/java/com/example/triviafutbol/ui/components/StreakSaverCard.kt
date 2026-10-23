package com.example.triviafutbol.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.TextPrimary

@Composable
fun StreakSaverCard(
    streakDays: Int,
    remainingSavers: Int,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.5.dp, GoldStreak, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreenContainer)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🛡️ ¡RACHA PROTEGIDA Y SALVADA!",
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldStreak,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "🔥 $streakDays días",
                    style = MaterialTheme.typography.labelLarge,
                    color = PitchGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "¡Casi pierdes tu racha! Se utilizó un Pase Salvavidas 🛡️ de tu inventario. Quedan $remainingSavers pases disponibles.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            FutbolButton(
                text = "¡ENTENDIDO, A JUGAR HOY! ⚽",
                onClick = onDismiss
            )
        }
    }
}
