package com.example.triviafutbol.ui.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.R
import com.example.triviafutbol.data.mock.MockQuestions
import com.example.triviafutbol.ui.components.CountdownTimerCard
import com.example.triviafutbol.ui.components.RouletteDialog
import com.example.triviafutbol.ui.components.StreakSaverCard
import com.example.triviafutbol.ui.components.TeamBadgeImage
import com.example.triviafutbol.ui.notification.NotificationHelper
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
fun HomeScreen(
    viewModel: HomeViewModel,
    onStartDailyChallenge: () -> Unit,
    onStartSprint: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenRanking: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val isMuted by SoundManager.isMuted.collectAsState()
    val context = LocalContext.current

    var showRouletteDialog by remember { mutableStateOf(false) }

    // Pool de 5 Imágenes HD de Fondo
    val randomBackgroundRes = remember {
        listOf(
            R.drawable.stadium_bg,
            R.drawable.stadium_bg_2,
            R.drawable.stadium_bg_3,
            R.drawable.stadium_bg_4,
            R.drawable.stadium_bg_5
        ).random()
    }

    Scaffold(
        containerColor = PitchBlack,
        bottomBar = {
            NavigationBar(
                containerColor = DarkCardSurface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { SoundManager.playClick() },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio", modifier = Modifier.size(22.dp)) },
                    label = { Text("Inicio", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PitchGreen,
                        selectedTextColor = PitchGreen,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary,
                        indicatorColor = DarkGreenContainer
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        SoundManager.playClick()
                        onOpenRanking()
                    },
                    icon = { Icon(Icons.Default.EmojiEvents, contentDescription = "Ranking", modifier = Modifier.size(22.dp)) },
                    label = { Text("Ranking", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PitchGreen,
                        selectedTextColor = PitchGreen,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary,
                        indicatorColor = DarkGreenContainer
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        SoundManager.playClick()
                        onOpenTraining()
                    },
                    icon = { Icon(Icons.Default.PlayCircle, contentDescription = "Entrenar", modifier = Modifier.size(22.dp)) },
                    label = { Text("Entrenar", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PitchGreen,
                        selectedTextColor = PitchGreen,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary,
                        indicatorColor = DarkGreenContainer
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        SoundManager.playClick()
                        onOpenProfile()
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", modifier = Modifier.size(22.dp)) },
                    label = { Text("Perfil", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PitchGreen,
                        selectedTextColor = PitchGreen,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary,
                        indicatorColor = DarkGreenContainer
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Top Bar con Botones de Sonido (🔊/🔇) y Campanita 🔔
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FÚTBOL QUIZ ⚽",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón Parlante Mute (🔊 / 🔇)
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(DarkCardSurface)
                            .border(1.dp, SurfaceBorder, CircleShape)
                            .clickable { SoundManager.toggleMute() }
                            .padding(8.dp)
                    ) {
                        Text(if (isMuted) "🔇" else "🔊", fontSize = 16.sp)
                    }

                    // Botón Campanita 🔔 (Dispara prueba de notificación local)
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(DarkCardSurface)
                            .border(1.dp, PitchGreen, CircleShape)
                            .clickable {
                                SoundManager.playClick()
                                NotificationHelper.sendDailyReminderNotification(context, userProfile.currentStreak)
                            }
                            .padding(8.dp)
                    ) {
                        Text("🔔", fontSize = 16.sp)
                    }
                }
            }

            // Hero Section: Imagen de Fondo de Estadio Real + Gradiente Oscuro
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentAlignment = Alignment.Center
            ) {
                // 1. Imagen de Fondo HD Aleatoria
                Image(
                    painter = painterResource(id = randomBackgroundRes),
                    contentDescription = "Fondo de Fútbol Dinámico",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // 2. Capa de Gradiente Oscuro para dar contraste e integrarse al tema Dark
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xAA08140D),
                                    Color(0xCC0F2B1D),
                                    PitchBlack
                                )
                            )
                        )
                )

                // 3. Contenido del Perfil del Usuario
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "¡BIENVENIDO, GOLEADOR!",
                        style = MaterialTheme.typography.labelLarge,
                        color = PitchGreen,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Avatar / Escudo de Usuario con Anillo Neón Brillante
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(DarkGreenContainer)
                            .border(3.5.dp, PitchGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        TeamBadgeImage(
                            badgeKey = userProfile.avatarUrl,
                            modifier = Modifier.fillMaxSize(),
                            emojiSizeSp = 40
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = userProfile.username.uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Nivel ${userProfile.level} • ${userProfile.levelTitle}",
                        style = MaterialTheme.typography.labelLarge,
                        color = PitchGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Racha 🔥 ${userProfile.currentStreak} días",
                            style = MaterialTheme.typography.bodyMedium,
                            color = GoldStreak,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "• 🛡️ ${userProfile.streakSaversCount} Salvavidas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botones de Acción / Alerta de Racha Salvada / Reloj de Cuenta Regresiva
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (userProfile.wasStreakProtectedToday) {
                    // Muestra el Banner de Alerta de Racha Salvada por el Salvavidas
                    StreakSaverCard(
                        streakDays = userProfile.currentStreak,
                        remainingSavers = userProfile.streakSaversCount,
                        onDismiss = {
                            SoundManager.playClick()
                            viewModel.dismissStreakProtectedBanner()
                        }
                    )
                }

                if (userProfile.hasCompletedDailyChallenge) {
                    // Muestra el Reloj Contador hasta la medianoche cuando ya jugó hoy
                    CountdownTimerCard()
                } else {
                    // 1. Botón "¡JUGAR AHORA!" Protagonista con Neón
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp)
                            .clip(RoundedCornerShape(31.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF00E676), Color(0xFF00B0FF))
                                )
                            )
                            .padding(2.5.dp)
                            .clickable {
                                SoundManager.playClick()
                                onStartDailyChallenge()
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(29.dp))
                                .background(PitchBlack),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "¡JUGAR DESAFÍO DIARIO! 🔥",
                                style = MaterialTheme.typography.titleMedium,
                                color = PitchGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }

                    // 2. Desafío Diario
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .clip(RoundedCornerShape(27.dp))
                            .background(DarkCardSurface)
                            .border(1.dp, SurfaceBorder, RoundedCornerShape(27.dp))
                            .clickable {
                                SoundManager.playClick()
                                onStartDailyChallenge()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "DESAFÍO DIARIO 🏆",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 3. Ruleta de Premios del Hincha 🎡
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(27.dp))
                        .background(DarkCardSurface)
                        .border(1.5.dp, GoldStreak, RoundedCornerShape(27.dp))
                        .clickable {
                            SoundManager.playClick()
                            showRouletteDialog = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🎡 RULETA DEL HINCHA 🎁",
                        style = MaterialTheme.typography.titleMedium,
                        color = GoldStreak,
                        fontWeight = FontWeight.Bold
                    )
                }

                // 4. Modo Sprint 60s
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(27.dp))
                        .background(DarkCardSurface)
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(27.dp))
                        .clickable {
                            SoundManager.playClick()
                            onStartSprint()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "⚡ SPRINT 60 SEGUNDOS",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                // 5. Modo Entrenamiento (Disponible siempre)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(27.dp))
                        .background(DarkCardSurface)
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(27.dp))
                        .clickable {
                            SoundManager.playClick()
                            onOpenTraining()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MODO ENTRENAMIENTO 📚",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Modal Diálogo de Ruleta del Hincha 🎡
        if (showRouletteDialog) {
            val randomQuestion = remember { MockQuestions.dailyQuestions.random() }
            RouletteDialog(
                questionToClaim = randomQuestion,
                onDismiss = { showRouletteDialog = false },
                onPrizeClaimed = { _ -> }
            )
        }
    }
}
