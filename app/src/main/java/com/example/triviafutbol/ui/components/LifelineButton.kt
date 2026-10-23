package com.example.triviafutbol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.DarkCardSurface
import com.example.triviafutbol.ui.theme.GoldStreak
import com.example.triviafutbol.ui.theme.SurfaceBorder
import com.example.triviafutbol.ui.theme.TextPrimary
import com.example.triviafutbol.ui.theme.TextSecondary

@Composable
fun LifelineButton(
    isAvailable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "50/50"
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isAvailable) DarkCardSurface else Color.Black.copy(alpha = 0.3f))
            .border(
                width = 1.dp,
                color = if (isAvailable) GoldStreak else SurfaceBorder,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(enabled = isAvailable) { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("🃏", fontSize = 16.sp, modifier = Modifier.padding(end = 6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = if (isAvailable) TextPrimary else TextSecondary.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold
        )
    }
}
