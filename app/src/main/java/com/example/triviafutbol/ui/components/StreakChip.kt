package com.example.triviafutbol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.DarkGreenContainer
import com.example.triviafutbol.ui.theme.GoldStreak

@Composable
fun StreakChip(
    streakDays: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(DarkGreenContainer, RoundedCornerShape(16.dp))
            .border(1.dp, GoldStreak.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("🔥", fontSize = 14.sp, modifier = Modifier.padding(end = 4.dp))
        Text(
            text = "$streakDays días",
            style = MaterialTheme.typography.labelLarge,
            color = GoldStreak,
            fontWeight = FontWeight.Bold
        )
    }
}
