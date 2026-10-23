package com.example.triviafutbol.domain.model

data class GameSession(
    val mode: GameMode,
    val questions: List<Question>,
    val currentIndex: Int = 0,
    val score: Int = 0,
    val correctAnswersCount: Int = 0,
    val yellowCards: Int = 0, // 0, 1, o 2 (tarjeta roja / expulsión)
    val disabledOptions: List<Int> = emptyList(), // Índices ocultados por comodín 50/50
    val hasUsedFiftyFifty: Boolean = false,
    val isGameOver: Boolean = false,
    val isRedCardExit: Boolean = false
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val totalQuestions: Int
        get() = questions.size
}
