package com.example.triviafutbol.domain.model

data class UserProfile(
    val id: String = "user_1",
    val username: String = "Goleador10",
    val avatarUrl: String? = "⚽",
    val totalPoints: Int = 340,
    val currentStreak: Int = 3,
    val bestStreak: Int = 7,
    val gamesPlayed: Int = 12,
    val sprintBestScore: Int = 120,
    val totalAnswersCount: Int = 50,
    val correctAnswersCount: Int = 34,
    val streakSaversCount: Int = 1,
    val hasCompletedDailyChallenge: Boolean = false,
    val wasStreakProtectedToday: Boolean = false
) {
    // 🏆 Escala de Niveles por Puntos
    val level: Int
        get() = when {
            totalPoints < 100 -> 1
            totalPoints < 250 -> 2
            totalPoints < 500 -> 3
            totalPoints < 900 -> 4
            totalPoints < 1400 -> 5
            totalPoints < 2000 -> 6
            totalPoints < 3000 -> 7
            totalPoints < 4500 -> 8
            totalPoints < 7000 -> 9
            else -> 10
        }

    val levelTitle: String
        get() = when (level) {
            1 -> "Promesa de Cantera 🌱"
            2 -> "Titular Reservista 🏃"
            3 -> "Titular Confirmado ⚽"
            4 -> "Crack del Equipo ⚡"
            5 -> "Capitán y Figura 🎗️"
            6 -> "Seleccionado Nacional 🇦🇷"
            7 -> "Campeón Continental 🏆"
            8 -> "Balón de Oro 🥇"
            9 -> "Ídolo Histórico 🌟"
            else -> "Leyenda Mundial 👑"
        }

    val nextLevelPoints: Int
        get() = when (level) {
            1 -> 100
            2 -> 250
            3 -> 500
            4 -> 900
            5 -> 1400
            6 -> 2000
            7 -> 3000
            8 -> 4500
            9 -> 7000
            else -> 10000
        }

    val prevLevelPoints: Int
        get() = when (level) {
            1 -> 0
            2 -> 100
            3 -> 250
            4 -> 500
            5 -> 900
            6 -> 1400
            7 -> 2000
            8 -> 3000
            9 -> 4500
            else -> 7000
        }

    val levelXpProgressRatio: Float
        get() {
            val range = nextLevelPoints - prevLevelPoints
            val currentInLevel = totalPoints - prevLevelPoints
            return (currentInLevel.toFloat() / range.toFloat()).coerceIn(0f, 1f)
        }

    val accuracyPercentage: Int
        get() = if (totalAnswersCount > 0) ((correctAnswersCount.toFloat() / totalAnswersCount.toFloat()) * 100).toInt() else 0

    val accuracyLabel: String
        get() = when {
            accuracyPercentage >= 90 -> "🎯 Francotirador"
            accuracyPercentage >= 75 -> "⚽ Goleador Letal"
            accuracyPercentage >= 50 -> "🏃 Titular Regular"
            else -> "🛠️ En Entrenamiento"
        }
}
