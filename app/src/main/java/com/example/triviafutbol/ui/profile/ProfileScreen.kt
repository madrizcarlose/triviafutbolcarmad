package com.example.triviafutbol.ui.profile

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.domain.model.Achievement
import com.example.triviafutbol.ui.components.AccuracyGaugeCard
import com.example.triviafutbol.ui.components.AchievementCard
import com.example.triviafutbol.ui.components.FutbolButton
import com.example.triviafutbol.ui.components.LevelProgressCard
import com.example.triviafutbol.ui.components.RulesDialog
import com.example.triviafutbol.ui.components.SeasonPathMap
import com.example.triviafutbol.ui.components.TeamBadgeImage
import com.example.triviafutbol.ui.home.HomeViewModel
import com.example.triviafutbol.ui.sound.SoundManager
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchBlack
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
    viewModel: HomeViewModel,
    onBackToHome: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var showRulesDialog by remember { mutableStateOf(false) }

    // 10 Avatares preestablecidos
    val availableAvatars = listOf("⚽", "🏆", "👑", "⚡", "🥇", "🛡️", "🧢", "🔥", "🇦🇷", "🎯")

    // Metas de Alto Nivel a Largo Plazo
    val achievementsList = listOf(
        Achievement(
            id = "ach_1",
            title = "👟 Bota de Oro",
            icon = "👟",
            description = "Acumular 2.500 respuestas correctas en tu carrera.",
            currentProgress = (userProfile.totalPoints / 10),
            targetProgress = 2500
        ),
        Achievement(
            id = "ach_2",
            title = "🏆 Copa de Campeones",
            icon = "🏆",
            description = "Alcanzar 5.000 puntos en el Ranking Global.",
            currentProgress = userProfile.totalPoints,
            targetProgress = 5000
        ),
        Achievement(
            id = "ach_3",
            title = "🔥 Titular Indiscutido",
            icon = "🔥",
            description = "Mantener una racha invicta de 100 días consecutivos.",
            currentProgress = userProfile.currentStreak,
            targetProgress = 100
        ),
        Achievement(
            id = "ach_4",
            title = "🧢 Director Técnico",
            icon = "🧢",
            description = "Completar 200 partidas en el Modo Entrenamiento.",
            currentProgress = userProfile.gamesPlayed,
            targetProgress = 200
        )
    )

    Scaffold(
        containerColor = PitchBlack
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "👤 Mi Perfil",
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

            Spacer(modifier = Modifier.height(4.dp))

            // Avatar & Nombre de usuario
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(DarkGreenContainer)
                    .border(3.dp, PitchGreen, CircleShape)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                TeamBadgeImage(
                    badgeKey = userProfile.avatarUrl,
                    modifier = Modifier.fillMaxSize(),
                    emojiSizeSp = 42
                )
            }

            Text(
                text = userProfile.username,
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            // Botones de Acción de Perfil: Editar Perfil & Reglamento
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkCardSurface)
                        .border(1.dp, PitchGreen, RoundedCornerShape(20.dp))
                        .clickable {
                            SoundManager.playClick()
                            showEditDialog = true
                        }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "✏️ Editar Perfil",
                        style = MaterialTheme.typography.labelLarge,
                        color = PitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkCardSurface)
                        .border(1.dp, GoldStreak, RoundedCornerShape(20.dp))
                        .clickable {
                            SoundManager.playClick()
                            showRulesDialog = true
                        }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "📖 Reglamento",
                        style = MaterialTheme.typography.labelLarge,
                        color = GoldStreak,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Tarjeta de Nivel y Experiencia (XP)
            LevelProgressCard(
                level = userProfile.level,
                levelTitle = userProfile.levelTitle,
                currentPoints = userProfile.totalPoints,
                nextLevelPoints = userProfile.nextLevelPoints,
                progressRatio = userProfile.levelXpProgressRatio
            )

            // Tarjeta de Mapa de Camino de Temporada 🗺️
            SeasonPathMap(totalPoints = userProfile.totalPoints)

            // Tarjeta de Efectividad en Gol (%)
            AccuracyGaugeCard(
                accuracyPercentage = userProfile.accuracyPercentage,
                accuracyLabel = userProfile.accuracyLabel,
                correctAnswers = userProfile.correctAnswersCount,
                totalAnswers = userProfile.totalAnswersCount
            )

            // Grilla de Estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Puntos Totales",
                    value = "${userProfile.totalPoints} pts",
                    icon = "⭐",
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    title = "Racha Actual",
                    value = "${userProfile.currentStreak} días",
                    icon = "🔥",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Mejor Racha",
                    value = "${userProfile.bestStreak} días",
                    icon = "👑",
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    title = "Partidas Jugadas",
                    value = "${userProfile.gamesPlayed}",
                    icon = "🎮",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sección: Vitrina de Logros y Trofeos (Alto Nivel)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🏆 VITRINA DE LOGROS",
                        style = MaterialTheme.typography.titleLarge,
                        color = GoldStreak,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${achievementsList.count { it.isUnlocked }}/${achievementsList.size}",
                        style = MaterialTheme.typography.titleMedium,
                        color = PitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Hitos exigentes de largo plazo para demostrar tu nivel.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                achievementsList.forEach { achievement ->
                    AchievementCard(achievement = achievement)
                }
            }
        }

        // Diálogo de Reglamento Deportivo 📖
        if (showRulesDialog) {
            RulesDialog(onDismiss = { showRulesDialog = false })
        }

        // Diálogo para Editar Perfil y Seleccionar Avatar
        if (showEditDialog) {
            var newUsername by remember { mutableStateOf(userProfile.username) }
            var selectedAvatar by remember { mutableStateOf(userProfile.avatarUrl ?: "⚽") }

            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                containerColor = DarkCardSurface,
                title = {
                    Text(
                        text = "✏️ Editar Mi Perfil",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Nombre de Jugador:", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        OutlinedTextField(
                            value = newUsername,
                            onValueChange = { newUsername = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PitchGreen,
                                unfocusedBorderColor = SurfaceBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("Elegí tu Avatar Favorito:", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(availableAvatars) { avatar ->
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(if (selectedAvatar == avatar) DarkGreenContainer else PitchBlack)
                                        .border(
                                            width = if (selectedAvatar == avatar) 2.dp else 1.dp,
                                            color = if (selectedAvatar == avatar) PitchGreen else SurfaceBorder,
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            SoundManager.playClick()
                                            selectedAvatar = avatar
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(avatar, fontSize = 24.sp)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    FutbolButton(
                        text = "GUARDAR CAMBIOS ✅",
                        onClick = {
                            SoundManager.playClick()
                            if (newUsername.isNotBlank()) {
                                viewModel.updateProfile(newUsername.trim(), selectedAvatar)
                            }
                            showEditDialog = false
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardSurface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = "$icon $title", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
