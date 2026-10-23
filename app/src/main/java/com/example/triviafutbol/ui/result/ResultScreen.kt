package com.example.triviafutbol.ui.result

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.components.ConfettiCelebration
import com.example.triviafutbol.ui.components.FutbolButton
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchBlack
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.RedCard
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

@Composable
fun ResultScreen(
    score: Int,
    correctCount: Int,
    isRedCard: Boolean,
    onNavigateHome: () -> Unit,
    onNavigateRanking: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = PitchBlack
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icono Principal
                Text(
                    text = if (isRedCard) "🟥" else "🏆",
                    fontSize = 72.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = if (isRedCard) "¡EXPULSADO DE LA PARTIDA!" else "¡DESAFÍO FINALIZADO!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = if (isRedCard) RedCard else PitchGreen,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (isRedCard)
                        "Recibiste dos tarjetas amarillas 🟨🟨 en la misma partida."
                    else
                        "¡Excelente trabajo! Sumaste puntos para tu ranking.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Tarjeta con Marcador Final
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCardSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Puntaje Obtenido",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary
                        )

                        Text(
                            text = "+$score pts",
                            style = MaterialTheme.typography.headlineLarge,
                            color = GoldStreak,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("✅ Correctas", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                Text("$correctCount", style = MaterialTheme.typography.titleLarge, color = TextPrimary, fontWeight = FontWeight.Bold)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🟨 Amarillas", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                Text(if (isRedCard) "2" else "1", style = MaterialTheme.typography.titleLarge, color = TextPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botones de Acción
                FutbolButton(
                    text = "VER RANKING GLOBAL 📊",
                    onClick = onNavigateRanking
                )

                Spacer(modifier = Modifier.height(12.dp))

                FutbolButton(
                    text = "VOLVER AL INICIO 🏠",
                    onClick = onNavigateHome
                )
            }
        }

        // Lluvia de Confeti Animada al ganar / finalizar sin expulsión
        if (!isRedCard) {
            ConfettiCelebration()
        }
    }
}
