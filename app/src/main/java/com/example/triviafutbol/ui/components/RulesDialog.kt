package com.example.triviafutbol.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.triviafutbol.ui.sound.SoundManager
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary

@Composable
fun RulesDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardSurface,
        title = {
            Text(
                text = "📖 Reglamento Deportivo",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. Desafío Diario
                RuleSectionCard(
                    title = "🏆 Desafío Diario",
                    description = "10 preguntas por día iguales para todos los usuarios. Cada acierto suma +10 puntos directamente al Ranking Global."
                )

                // 2. Tarjetas Amarillas y Rojas
                RuleSectionCard(
                    title = "🟨 🟥 Tarjetas & Expulsión",
                    description = "Primera respuesta incorrecta = Tarjeta Amarilla (🟨). Segunda incorrecta = Tarjeta Roja (🟥) y expulsión inmediata de la partida conservando tus puntos previos."
                )

                // 3. Comodín 50/50
                RuleSectionCard(
                    title = "🃏 Comodín 50/50",
                    description = "Elimina dos respuestas incorrectas para dejarte sólo 2 opciones. Disponible 1 uso por partida."
                )

                // 4. Rachas y Salvavidas
                RuleSectionCard(
                    title = "🔥 Racha & Salvavidas 🛡️",
                    description = "Jugar diariamente mantiene encendida tu racha. Si olvidas jugar un día, tu Pase Salvavidas 🛡️ protegerá y congelará tu racha intacta."
                )

                // 5. Modo Sprint 60s
                RuleSectionCard(
                    title = "⚡ Sprint 60 Segundos",
                    description = "Comienzas con 60 segundos en el reloj. Acierto: +10 pts y +2 segundos extra. Error: -5 segundos de penalización."
                )
            }
        },
        confirmButton = {
            FutbolButton(
                text = "¡ENTENDIDO, A JUGAR! ⚽",
                onClick = {
                    SoundManager.playClick()
                    onDismiss()
                }
            )
        }
    )
}

@Composable
private fun RuleSectionCard(
    title: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreenContainer)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = PitchGreen,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
        }
    }
}
