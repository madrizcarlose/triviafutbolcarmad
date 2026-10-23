package com.example.triviafutbol.ui.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.sound.SoundManager
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchBlack
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

data class RankingUser(
    val rank: Int,
    val username: String,
    val points: Int,
    val avatarUrl: String? = "⚽",
    val isCurrentUser: Boolean = false
)

@Composable
fun RankingScreen(
    viewModel: RankingViewModel,
    onBackToHome: () -> Unit
) {
    val rankingList by viewModel.rankingList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isWeeklyTab by viewModel.isWeeklyTab.collectAsState()

    Scaffold(
        containerColor = PitchBlack
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📊 Ranking Global",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "🏠 Inicio",
                    style = MaterialTheme.typography.titleMedium,
                    color = PitchGreen,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        SoundManager.playClick()
                        onBackToHome()
                    }
                )
            }

            Text(
                text = "Clasificación competitiva de los desafíos diarios.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )

            // Selector Semanal / General
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkCardSurface)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isWeeklyTab) DarkGreenContainer else DarkCardSurface)
                        .clickable {
                            SoundManager.playClick()
                            viewModel.setTab(true)
                        }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Semanal 🏆",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isWeeklyTab) PitchGreen else TextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isWeeklyTab) DarkGreenContainer else DarkCardSurface)
                        .clickable {
                            SoundManager.playClick()
                            viewModel.setTab(false)
                        }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "General ⭐",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (!isWeeklyTab) PitchGreen else TextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Estado de Carga o Lista de Jugadores
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(color = PitchGreen)
                        Text("Cargando posiciones en vivo...", color = TextSecondary)
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(rankingList) { _, user ->
                        RankingItemCard(user = user)
                    }
                }
            }
        }
    }
}

@Composable
private fun RankingItemCard(user: RankingUser) {
    val medalIcon = when (user.rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "#${user.rank}"
    }

    val cardBg = if (user.isCurrentUser) DarkGreenContainer else DarkCardSurface
    val borderColor = if (user.isCurrentUser) PitchGreen else SurfaceBorder

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = medalIcon,
                style = MaterialTheme.typography.titleMedium,
                fontSize = if (user.rank <= 3) 22.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (user.rank <= 3) GoldStreak else TextSecondary,
                modifier = Modifier.padding(end = 10.dp)
            )

            // Avatar del Jugador
            Text(
                text = user.avatarUrl ?: "⚽",
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(
                text = user.username,
                style = MaterialTheme.typography.titleMedium,
                color = if (user.isCurrentUser) PitchGreen else TextPrimary,
                fontWeight = if (user.isCurrentUser || user.rank <= 3) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${user.points} pts",
                style = MaterialTheme.typography.titleMedium,
                color = GoldStreak,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
