package com.example.triviafutbol.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.domain.model.Question
import com.example.triviafutbol.ui.sound.SoundManager
import com.example.triviafutbol.ui.theme.CorrectGreen
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.IncorrectRed
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

data class PrizeOption(
    val title: String,
    val icon: String,
    val color: Color
)

@Composable
fun RouletteDialog(
    questionToClaim: Question,
    onDismiss: () -> Unit,
    onPrizeClaimed: (prizeType: String) -> Unit
) {
    val prizes = remember {
        listOf(
            PrizeOption("+100 Puntos", "⭐", GoldStreak),
            PrizeOption("1 Salvavidas", "🛡️", PitchGreen),
            PrizeOption("50/50 Joker", "🃏", Color(0xFF00B0FF)),
            PrizeOption("+50 Puntos", "⭐", GoldStreak),
            PrizeOption("1 Salvavidas", "🛡️", PitchGreen),
            PrizeOption("+200 Puntos", "🏆", GoldStreak)
        )
    }

    var rotationDegrees by remember { mutableFloatStateOf(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    var wonPrize by remember { mutableStateOf<PrizeOption?>(null) }
    var showQuestionPhase by remember { mutableStateOf(false) }

    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    val animatedRotation by animateFloatAsState(
        targetValue = rotationDegrees,
        animationSpec = tween(durationMillis = 3500, easing = FastOutSlowInEasing),
        finishedListener = {
            isSpinning = false
            wonPrize = prizes[1] // Premio ganado
        },
        label = "rouletteSpin"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardSurface,
        title = {
            Text(
                text = if (!showQuestionPhase) "🎡 Ruleta del Hincha" else "⚽ Pregunta de Desafío",
                style = MaterialTheme.typography.titleLarge,
                color = GoldStreak,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (!showQuestionPhase) {
                    // Fase 1: Girar la Ruleta 🎡
                    Box(
                        modifier = Modifier.size(220.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Ruleta Canvas
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .rotate(animatedRotation)
                        ) {
                            val sweepAngle = 360f / prizes.size
                            prizes.forEachIndexed { idx, prize ->
                                drawArc(
                                    color = prize.color.copy(alpha = 0.85f),
                                    startAngle = idx * sweepAngle,
                                    sweepAngle = sweepAngle,
                                    useCenter = true,
                                    size = Size(size.width, size.height)
                                )
                            }
                        }

                        // Flecha Marcador Superior
                        Text(
                            text = "🔻",
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )

                        // Centro de la Ruleta
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(DarkGreenContainer)
                                .border(2.dp, GoldStreak, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("⚽", fontSize = 28.sp)
                        }
                    }

                    if (wonPrize != null) {
                        // Premio conseguido 🎁
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.5.dp, GoldStreak, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkGreenContainer)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "🎁 ¡GANASTE ${wonPrize?.icon} ${wonPrize?.title}!",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = GoldStreak,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Para reclamarlo en tu cuenta, responde correctamente la siguiente pregunta:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        FutbolButton(
                            text = "RECLAMAR PREMIO ⚽",
                            onClick = {
                                SoundManager.playClick()
                                showQuestionPhase = true
                            }
                        )
                    } else {
                        FutbolButton(
                            text = if (isSpinning) "GIRANDO... 🎡" else "GIRAR RULETA 🎰",
                            enabled = !isSpinning,
                            onClick = {
                                SoundManager.playClick()
                                isSpinning = true
                                rotationDegrees += (360 * 5) + 60f // 5 giros completos
                            }
                        )
                    }
                } else {
                    // Fase 2: Pregunta Desafío para Reclamar el Premio ⚽
                    Text(
                        text = questionToClaim.text,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    questionToClaim.options.forEachIndexed { index, optionText ->
                        val isCorrect = index == questionToClaim.correctAnswerIndex
                        val isSelected = index == selectedAnswerIndex

                        val cardBg = when {
                            !isAnswered -> DarkCardSurface
                            isCorrect -> CorrectGreen.copy(alpha = 0.2f)
                            isSelected -> IncorrectRed.copy(alpha = 0.2f)
                            else -> DarkCardSurface
                        }

                        val cardBorder = when {
                            !isAnswered -> SurfaceBorder
                            isCorrect -> CorrectGreen
                            isSelected -> IncorrectRed
                            else -> SurfaceBorder
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(cardBg)
                                .border(1.dp, cardBorder, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isAnswered) {
                                    selectedAnswerIndex = index
                                    isAnswered = true
                                    if (isCorrect) {
                                        SoundManager.playCheer()
                                    } else {
                                        SoundManager.playWhistle()
                                    }
                                }
                                .padding(14.dp)
                        ) {
                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isAnswered && isCorrect) CorrectGreen else TextPrimary,
                                fontWeight = if (isSelected || (isAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    if (isAnswered) {
                        val isUserCorrect = selectedAnswerIndex == questionToClaim.correctAnswerIndex

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isUserCorrect) "🎉 ¡PREMIO DESBLOQUEADO Y OTORGADO!" else "🟨 ¡RESPUESTA INCORRECTA! Te cobraron falta.",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isUserCorrect) PitchGreen else IncorrectRed,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        FutbolButton(
                            text = "CONTINUAR ⚽",
                            onClick = {
                                SoundManager.playClick()
                                if (isUserCorrect && wonPrize != null) {
                                    onPrizeClaimed(wonPrize!!.title)
                                }
                                onDismiss()
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {}
    )
}
