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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchBlack
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

data class PathNode(
    val stepNumber: Int,
    val title: String,
    val pointsRequired: Int,
    val rewardIcon: String,
    val rewardName: String
)

@Composable
fun SeasonPathMap(
    totalPoints: Int,
    modifier: Modifier = Modifier
) {
    val pathNodes = remember {
        listOf(
            PathNode(1, "Cantera", 0, "🌱", "Inicio del Camino"),
            PathNode(2, "Debutante", 100, "🛡️", "1 Salvavidas"),
            PathNode(3, "Titular", 250, "🃏", "Comodín 50/50"),
            PathNode(4, "Goleador", 500, "⭐", "+100 Puntos Bonus"),
            PathNode(5, "Capitán", 1000, "👑", "Avatar Corona de Leyenda"),
            PathNode(6, "Campeón", 2000, "🏆", "Insignia de Campeón"),
            PathNode(7, "Balón de Oro", 3500, "🥇", "Estatus Balón de Oro"),
            PathNode(8, "Leyenda", 5000, "💎", "Trofeo Diamante Supremo")
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardSurface)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🗺️ Camino de Temporada",
                    style = MaterialTheme.typography.titleLarge,
                    color = GoldStreak,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$totalPoints pts",
                    style = MaterialTheme.typography.titleMedium,
                    color = PitchGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Conquista las estaciones del mapa acumulando puntos en tus partidas.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Lista Vertical de Nodos del Mapa
            pathNodes.forEachIndexed { index, node ->
                val isUnlocked = totalPoints >= node.pointsRequired
                val isNextTarget = !isUnlocked && (index == 0 || totalPoints >= pathNodes[index - 1].pointsRequired)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Círculo del Nodo del Mapa
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isUnlocked -> DarkGreenContainer
                                    isNextTarget -> GoldStreak.copy(alpha = 0.2f)
                                    else -> PitchBlack
                                }
                            )
                            .border(
                                width = if (isNextTarget) 2.dp else 1.dp,
                                color = when {
                                    isUnlocked -> PitchGreen
                                    isNextTarget -> GoldStreak
                                    else -> SurfaceBorder
                                },
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isUnlocked) "✅" else node.rewardIcon,
                            fontSize = 18.sp
                        )
                    }

                    // Información de la Estación
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Estación ${node.stepNumber}: ${node.title}",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isUnlocked) PitchGreen else if (isNextTarget) GoldStreak else TextPrimary,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "${node.pointsRequired} pts",
                                style = MaterialTheme.typography.labelLarge,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }

                        Text(
                            text = "Recompensa: ${node.rewardName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
