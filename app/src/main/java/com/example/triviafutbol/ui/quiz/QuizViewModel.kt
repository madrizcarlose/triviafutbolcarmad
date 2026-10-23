package com.example.triviafutbol.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviafutbol.data.repository.TriviaRepository
import com.example.triviafutbol.domain.model.GameMode
import com.example.triviafutbol.domain.model.GameSession
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: TriviaRepository = TriviaRepository()
) : ViewModel() {

    private val _gameSession = MutableStateFlow<GameSession?>(null)
    val gameSession: StateFlow<GameSession?> = _gameSession.asStateFlow()

    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    val selectedOptionIndex: StateFlow<Int?> = _selectedOptionIndex.asStateFlow()

    private val _isAnswered = MutableStateFlow(false)
    val isAnswered: StateFlow<Boolean> = _isAnswered.asStateFlow()

    // Contador de tiempo en segundos para Modo Sprint 60s
    private val _sprintTimeLeft = MutableStateFlow(60)
    val sprintTimeLeft: StateFlow<Int> = _sprintTimeLeft.asStateFlow()

    private var sprintTimerJob: Job? = null

    fun initGame(mode: GameMode, categoryId: String? = null) {
        val questions = when (mode) {
            GameMode.DAILY_CHALLENGE -> repository.getDailyChallengeQuestions()
            GameMode.TRAINING -> repository.getQuestionsForCategory(categoryId ?: "cat_arg")
            GameMode.SPRINT -> repository.getDailyChallengeQuestions().shuffled()
        }

        _gameSession.value = GameSession(
            mode = mode,
            questions = questions
        )
        _selectedOptionIndex.value = null
        _isAnswered.value = false

        if (mode == GameMode.SPRINT) {
            startSprintTimer()
        }
    }

    private fun startSprintTimer() {
        sprintTimerJob?.cancel()
        _sprintTimeLeft.value = 60
        sprintTimerJob = viewModelScope.launch {
            while (_sprintTimeLeft.value > 0) {
                delay(1000L)
                _sprintTimeLeft.update { (it - 1).coerceAtLeast(0) }
            }
        }
    }

    fun selectOption(optionIndex: Int) {
        if (_isAnswered.value) return
        val session = _gameSession.value ?: return
        val currentQ = session.currentQuestion ?: return

        _selectedOptionIndex.value = optionIndex
        _isAnswered.value = true

        val isCorrect = (optionIndex == currentQ.correctAnswerIndex)

        if (isCorrect) {
            _gameSession.update { current ->
                current?.copy(
                    score = current.score + 10,
                    correctAnswersCount = current.correctAnswersCount + 1
                )
            }
            if (session.mode == GameMode.SPRINT) {
                _sprintTimeLeft.update { it + 2 }
            }
        } else {
            if (session.mode == GameMode.SPRINT) {
                _sprintTimeLeft.update { (it - 5).coerceAtLeast(0) }
            } else {
                // Amonestación (Tarjeta Amarilla 🟨)
                val newYellowCards = session.yellowCards + 1
                val isRedCard = (newYellowCards >= 2)

                _gameSession.update { current ->
                    current?.copy(
                        yellowCards = newYellowCards,
                        isGameOver = isRedCard,
                        isRedCardExit = isRedCard
                    )
                }
            }
        }
    }

    fun useFiftyFiftyJoker() {
        val session = _gameSession.value ?: return
        if (session.hasUsedFiftyFifty || _isAnswered.value) return
        val currentQ = session.currentQuestion ?: return

        val incorrectIndices = currentQ.options.indices.filter { it != currentQ.correctAnswerIndex }.shuffled()
        val disabledToHide = incorrectIndices.take(2)

        _gameSession.update { current ->
            current?.copy(
                disabledOptions = disabledToHide,
                hasUsedFiftyFifty = true
            )
        }
    }

    fun nextQuestion(onGameFinished: (score: Int, correct: Int, mode: String, isRedCard: Boolean) -> Unit) {
        val session = _gameSession.value ?: return

        if (session.isRedCardExit) {
            saveGameResult(session)
            onGameFinished(session.score, session.correctAnswersCount, session.mode.name, true)
            return
        }

        val nextIndex = session.currentIndex + 1
        if (nextIndex >= session.totalQuestions) {
            saveGameResult(session)
            onGameFinished(session.score, session.correctAnswersCount, session.mode.name, false)
        } else {
            _gameSession.update { current ->
                current?.copy(
                    currentIndex = nextIndex,
                    disabledOptions = emptyList()
                )
            }
            _selectedOptionIndex.value = null
            _isAnswered.value = false
        }
    }

    private fun saveGameResult(session: GameSession) {
        sprintTimerJob?.cancel()
        when (session.mode) {
            GameMode.DAILY_CHALLENGE -> repository.completeDailyChallenge(session.score)
            GameMode.TRAINING -> repository.completeTrainingGame(session.score)
            GameMode.SPRINT -> repository.completeSprintGame(session.score)
        }
    }

    override fun onCleared() {
        super.onCleared()
        sprintTimerJob?.cancel()
    }
}
