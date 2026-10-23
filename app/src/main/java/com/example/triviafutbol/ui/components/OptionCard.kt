package com.example.triviafutbol.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.CardDisabled
import com.example.triviafutbol.ui.theme.CorrectGreen
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.IncorrectRed
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

enum class OptionState {
    DEFAULT,
    CORRECT,
    INCORRECT,
    DISABLED_BY_JOKER
}

@Composable
fun OptionCard(
    optionText: String,
    optionIndex: Int,
    state: OptionState,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val letterLabel = when (optionIndex) {
        0 -> "A"
        1 -> "B"
        2 -> "C"
        else -> "D"
    }

    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            OptionState.DEFAULT -> DarkCardSurface
            OptionState.CORRECT -> CorrectGreen.copy(alpha = 0.2f)
            OptionState.INCORRECT -> IncorrectRed.copy(alpha = 0.2f)
            OptionState.DISABLED_BY_JOKER -> CardDisabled.copy(alpha = 0.3f)
        },
        label = "bgColor"
    )

    val borderColor by animateColorAsState(
        targetValue = when (state) {
            OptionState.DEFAULT -> SurfaceBorder
            OptionState.CORRECT -> CorrectGreen
            OptionState.INCORRECT -> IncorrectRed
            OptionState.DISABLED_BY_JOKER -> Color.Transparent
        },
        label = "borderColor"
    )

    val textColor = when (state) {
        OptionState.DISABLED_BY_JOKER -> TextSecondary.copy(alpha = 0.4f)
        OptionState.CORRECT -> CorrectGreen
        OptionState.INCORRECT -> IncorrectRed
        OptionState.DEFAULT -> TextPrimary
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(enabled = enabled && state != OptionState.DISABLED_BY_JOKER) { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Círculo con la letra (A, B, C, D)
        Text(
            text = "$letterLabel.",
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 12.dp)
        )

        // Texto de la opción
        Text(
            text = if (state == OptionState.DISABLED_BY_JOKER) "— Descartada (50/50) —" else optionText,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontWeight = if (state != OptionState.DEFAULT) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        // Ícono de estado al responder
        when (state) {
            OptionState.CORRECT -> Text("✅", fontSize = 16.sp)
            OptionState.INCORRECT -> Text("🟨", fontSize = 16.sp)
            else -> {}
        }
    }
}
