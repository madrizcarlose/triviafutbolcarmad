package com.example.triviafutbol.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.RedCard
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.YellowCard

@Composable
fun AnimatedCardOverlay(
    isVisible: Boolean,
    isRedCard: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = fadeOut() + scaleOut(),
        modifier = modifier
    ) {
        val rotationAngle by animateFloatAsState(
            targetValue = if (isVisible) -8f else 0f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            label = "cardRotation"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                // Tarjeta 3D Animada
                Box(
                    modifier = Modifier
                        .size(width = 140.dp, height = 200.dp)
                        .rotate(rotationAngle)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isRedCard) RedCard else YellowCard)
                        .border(3.dp, Color.White, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isRedCard) "🟥" else "🟨",
                        fontSize = 72.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Mensaje de la amonestación
                Text(
                    text = if (isRedCard) "¡TARJETA ROJA!" else "¡TARJETA AMARILLA!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = if (isRedCard) RedCard else YellowCard,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isRedCard)
                        "¡Segunda amonestación! Quedás expulsado de la partida."
                    else
                        "Cuidado, una respuesta incorrecta más y recibirás la roja.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
