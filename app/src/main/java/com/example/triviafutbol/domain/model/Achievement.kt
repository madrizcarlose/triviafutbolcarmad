package com.example.triviafutbol.domain.model

data class Achievement(
    val id: String,
    val title: String,
    val icon: String,
    val description: String,
    val currentProgress: Int,
    val targetProgress: Int
) {
    val isUnlocked: Boolean
        get() = currentProgress >= targetProgress

    val progressRatio: Float
        get() = (currentProgress.toFloat() / targetProgress.toFloat()).coerceIn(0f, 1f)
}
