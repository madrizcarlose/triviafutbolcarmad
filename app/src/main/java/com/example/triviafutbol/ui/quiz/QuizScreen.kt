package com.example.triviafutbol.ui.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.domain.model.GameMode
import com.example.triviafutbol.ui.components.AnimatedCardOverlay
import com.example.triviafutbol.ui.components.CardStatusBadge
import com.example.triviafutbol.ui.components.FutbolButton
import com.example.triviafutbol.ui.components.LifelineButton
import com.example.triviafutbol.ui.components.OptionCard
import com.example.triviafutbol.ui.components.OptionState
import com.example.triviafutbol.ui.sound.SoundManager
import com.example.triviafutbol.ui.theme.CorrectGreen
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.IncorrectRed
import com.example.triviafutbol.ui.theme.PitchBlack
import com.example.triviafutbol.ui.theme.PitchGreen
import com.example.triviafutbol.ui.theme.RedCard
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary
import com.example.triviafutbol.ui.theme.YellowCard

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    mode: GameMode,
    categoryId: String?,
    onGameFinished: (score: Int, correct: Int, mode: String, isRedCard: Boolean) -> Unit
) {
    val session by viewModel.gameSession.collectAsState()
    val selectedIndex by viewModel.selectedOptionIndex.collectAsState()
    val isAnswered by viewModel.isAnswered.collectAsState()
    val sprintTimeLeft by viewModel.sprintTimeLeft.collectAsState()

    LaunchedEffect(Unit) {
        if (session == null) {
            viewModel.initGame(mode, categoryId)
        }
    }

    // Auto-terminar el juego en Modo Sprint cuando el reloj llega a 0
    LaunchedEffect(sprintTimeLeft) {
        if (mode == GameMode.SPRINT && sprintTimeLeft <= 0 && session != null) {
            val current = session
            if (current != null) {
                onGameFinished(current.score, current.correctAnswersCount, mode.name, false)
            }
        }
    }

    val currentSession = session ?: return
    val currentQuestion = currentSession.currentQuestion ?: return

    val isWrongAnswer = isAnswered && (selectedIndex != currentQuestion.correctAnswerIndex)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = PitchBlack
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header con progreso / reloj sprint, puntos y tarjetas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (mode == GameMode.SPRINT) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (sprintTimeLeft <= 10) RedCard.copy(alpha = 0.3f) else DarkCardSurface)
                                .border(1.dp, if (sprintTimeLeft <= 10) RedCard else GoldStreak, RoundedCornerShape(12.dp))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "⏱️ ${sprintTimeLeft}s",
                                style = MaterialTheme.typography.titleLarge,
                                color = if (sprintTimeLeft <= 10) RedCard else GoldStreak,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Text(
                            text = "${currentSession.currentIndex + 1} / ${currentSession.totalQuestions}",
                            style = MaterialTheme.typography.titleLarge,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Indicador de Puntos en vivo
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkCardSurface)
                            .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "⭐ ${currentSession.score} pts",
                            style = MaterialTheme.typography.titleMedium,
                            color = GoldStreak,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (mode != GameMode.SPRINT) {
                        CardStatusBadge(yellowCards = currentSession.yellowCards)
                    }
                }

                // Barra de progreso visual
                if (mode != GameMode.SPRINT) {
                    LinearProgressIndicator(
                        progress = { (currentSession.currentIndex + 1).toFloat() / currentSession.totalQuestions.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = PitchGreen,
                        trackColor = DarkCardSurface
                    )
                }

                // Transición Animada entre Preguntas (Slide + Fade)
                AnimatedContent(
                    targetState = currentSession.currentIndex,
                    transitionSpec = {
                        (slideInHorizontally { width -> width } + fadeIn()) togetherWith
                        (slideOutHorizontally { width -> -width } + fadeOut())
                    },
                    label = "questionSlideAnimation"
                ) { targetIndex ->
                    val questionToDisplay = currentSession.questions.getOrNull(targetIndex) ?: currentQuestion

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Categoría de la pregunta + Comodín 50/50
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkCardSurface)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = questionToDisplay.category.uppercase(),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }

                            // Botón Comodín 50/50
                            LifelineButton(
                                isAvailable = !currentSession.hasUsedFiftyFifty && !isAnswered,
                                onClick = {
                                    SoundManager.playClick()
                                    viewModel.useFiftyFiftyJoker()
                                }
                            )
                        }

                        // Tarjeta de la Pregunta
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkCardSurface)
                        ) {
                            Text(
                                text = questionToDisplay.text,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Opciones de Respuesta (4 tarjetas)
                        questionToDisplay.options.forEachIndexed { index, optionText ->
                            val isOptionDisabledByJoker = currentSession.disabledOptions.contains(index)

                            val optionState = when {
                                isOptionDisabledByJoker -> OptionState.DISABLED_BY_JOKER
                                !isAnswered -> OptionState.DEFAULT
                                index == questionToDisplay.correctAnswerIndex -> OptionState.CORRECT
                                index == selectedIndex -> OptionState.INCORRECT
                                else -> OptionState.DEFAULT
                            }

                            OptionCard(
                                optionText = optionText,
                                optionIndex = index,
                                state = optionState,
                                enabled = !isAnswered,
                                onClick = {
                                    viewModel.selectOption(index)
                                    if (index == questionToDisplay.correctAnswerIndex) {
                                        SoundManager.playCheer()
                                    } else {
                                        SoundManager.playWhistle()
                                    }
                                }
                            )
                        }
                    }
                }

                // Explicación / Feedback post-respuesta
                if (isAnswered) {
                    Spacer(modifier = Modifier.height(8.dp))

                    val isCorrect = (selectedIndex == currentQuestion.correctAnswerIndex)

                    if (currentSession.isRedCardExit) {
                        // Alerta de TARJETA ROJA
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.5.dp, RedCard, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = IncorrectRed.copy(alpha = 0.2f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "🟥 ¡SEGUNDA AMARILLA = TARJETA ROJA!",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = RedCard,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Acumulaste dos respuestas incorrectas. ¡Quedás expulsado de la partida!",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextPrimary
                                )
                            }
                        }
                    } else if (!isCorrect) {
                        // Alerta de Penalización en Sprint o Tarjeta Amarilla 🟨
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, YellowCard, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = YellowCard.copy(alpha = 0.15f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = if (mode == GameMode.SPRINT) "⚠️ ¡INCORRECTO! (-5 segundos)" else "🟨 ¡TARJETA AMARILLA!",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = YellowCard,
                                    fontWeight = FontWeight.Bold
                                )
                                if (currentQuestion.explanation != null) {
                                    Text(
                                        text = currentQuestion.explanation,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    } else {
                        // Feedback Respuesta Correcta ✅
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, CorrectGreen, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CorrectGreen.copy(alpha = 0.15f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = if (mode == GameMode.SPRINT) "✅ ¡CORRECTO! (+10 pts / +2s)" else "✅ ¡CORRECTO! (+10 pts)",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = CorrectGreen,
                                    fontWeight = FontWeight.Bold
                                )
                                if (currentQuestion.explanation != null) {
                                    Text(
                                        text = currentQuestion.explanation,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FutbolButton(
                        text = if (currentSession.isRedCardExit) "VER EXPULSIÓN 🟥"
                        else if (currentSession.currentIndex + 1 >= currentSession.totalQuestions) "VER RESULTADOS 🏆"
                        else "SIGUIENTE PREGUNTA ⚽",
                        onClick = {
                            SoundManager.playClick()
                            viewModel.nextQuestion(onGameFinished)
                        }
                    )
                }
            }
        }

        // Overlay Animado de Tarjeta Amarilla o Roja (Sólo en desafíos estándar)
        if (mode != GameMode.SPRINT) {
            AnimatedCardOverlay(
                isVisible = isWrongAnswer,
                isRedCard = currentSession.isRedCardExit
            )
        }
    }
}
