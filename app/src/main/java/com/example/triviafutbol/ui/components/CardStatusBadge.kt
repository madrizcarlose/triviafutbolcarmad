package com.example.triviafutbol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviafutbol.ui.theme.RedCard
import com.example.triviafutbol.ui.theme.YellowCard

@Composable
fun CardStatusBadge(
    yellowCards: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Indicador de Tarjetas Amarillas acumuladas (Max 2 = Roja)
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(28.dp)
                .background(
                    color = if (yellowCards >= 1) YellowCard else Color.DarkGray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(3.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (yellowCards >= 1) YellowCard else Color.Gray,
                    shape = RoundedCornerShape(3.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (yellowCards >= 1) {
                Text("🟨", fontSize = 12.sp)
            }
        }

        Box(
            modifier = Modifier
                .width(20.dp)
                .height(28.dp)
                .background(
                    color = if (yellowCards >= 2) RedCard else Color.DarkGray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(3.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (yellowCards >= 2) RedCard else Color.Gray,
                    shape = RoundedCornerShape(3.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (yellowCards >= 2) {
                Text("🟥", fontSize = 12.sp)
            }
        }
    }
}
