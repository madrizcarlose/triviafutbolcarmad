package com.example.triviafutbol.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.triviafutbol.R

@Composable
fun TeamBadgeImage(
    badgeKey: String?,
    modifier: Modifier = Modifier,
    emojiSizeSp: Int = 40
) {
    val key = badgeKey ?: "⚽"

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when {
            key.startsWith("drawable:") -> {
                val resId = getDrawableResId(key.removePrefix("drawable:"))
                if (resId != 0) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "Escudo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Text("⚽", fontSize = emojiSizeSp.sp)
                }
            }
            key.startsWith("http") -> {
                AsyncImage(
                    model = key,
                    contentDescription = "Escudo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            else -> {
                Text(key, fontSize = emojiSizeSp.sp)
            }
        }
    }
}

private fun getDrawableResId(name: String): Int {
    return when (name) {
        "ic_boca" -> R.drawable.ic_boca
        "ic_river" -> R.drawable.ic_river
        "ic_realmadrid" -> R.drawable.ic_realmadrid
        "ic_barcelona" -> R.drawable.ic_barcelona
        "ic_mancity" -> R.drawable.ic_mancity
        "ic_argentina" -> R.drawable.ic_argentina
        "ic_brasil" -> R.drawable.ic_brasil
        "ic_uruguay" -> R.drawable.ic_uruguay
        "ic_colombia" -> R.drawable.ic_colombia
        "ic_mexico" -> R.drawable.ic_mexico
        "ic_espana" -> R.drawable.ic_espana
        "ic_francia" -> R.drawable.ic_francia
        "ic_italia" -> R.drawable.ic_italia
        "ic_alemania" -> R.drawable.ic_alemania
        else -> 0
    }
}
