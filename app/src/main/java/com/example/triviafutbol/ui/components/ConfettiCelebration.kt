package com.example.triviafutbol.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.PitchGreen
import kotlin.random.Random

private data class ConfettiParticle(
    val xRatio: Float,
    val speed: Float,
    val size: Float,
    val color: Color,
    val initialYRatio: Float,
    val isGoldenStar: Boolean = false
)

@Composable
fun ConfettiCelebration(
    modifier: Modifier = Modifier
) {
    val particles = remember {
        val colors = listOf(
            GoldStreak,
            PitchGreen,
            Color(0xFF00B0FF),
            Color(0xFFFF3D00),
            Color(0xFFFFD600),
            Color.White
        )
        List(50) { idx ->
            ConfettiParticle(
                xRatio = Random.nextFloat(),
                speed = Random.nextFloat() * 0.8f + 0.4f,
                size = Random.nextFloat() * 12f + 6f,
                color = colors.random(),
                initialYRatio = Random.nextFloat(),
                isGoldenStar = (idx % 3 == 0)
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "confettiCelebration")
    val fallProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fall"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        particles.forEach { particle ->
            val y = ((particle.initialYRatio + fallProgress.value * particle.speed) % 1.0f) * height
            val x = particle.xRatio * width

            if (particle.isGoldenStar) {
                // Dibujar destello / estrella dorada
                drawCircle(
                    color = GoldStreak.copy(alpha = 0.4f),
                    radius = particle.size * 1.8f,
                    center = Offset(x, y)
                )
                drawCircle(
                    color = Color.White,
                    radius = particle.size * 0.7f,
                    center = Offset(x, y)
                )
            } else {
                // Confeti clásico multicolor
                drawCircle(
                    color = particle.color,
                    radius = particle.size,
                    center = Offset(x, y)
                )
            }
        }
    }
}
