package com.example.triviafutbol.data.repository

import com.example.triviafutbol.data.mock.MockQuestions
import com.example.triviafutbol.data.model.ProfileDto
import com.example.triviafutbol.data.model.QuestionDto
import com.example.triviafutbol.data.remote.SupabaseClientManager
import com.example.triviafutbol.domain.model.Category
import com.example.triviafutbol.domain.model.Question
import com.example.triviafutbol.domain.model.UserProfile
import com.example.triviafutbol.ui.ranking.RankingUser
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class TriviaRepository {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val isSupabaseConfigured: Boolean
        get() = SupabaseClientManager.SUPABASE_URL.contains("supabase.co") && 
                !SupabaseClientManager.SUPABASE_URL.contains("tu-proyecto")

    suspend fun fetchDailyChallengeQuestions(): List<Question> {
        return if (isSupabaseConfigured) {
            try {
                val dtos = SupabaseClientManager.client.postgrest["questions"]
                    .select()
                    .decodeList<QuestionDto>()

                if (dtos.isNotEmpty()) {
                    dtos.map { it.toDomainModel("Mundiales") }.shuffled().take(10)
                } else {
                    getDailyChallengeQuestions()
                }
            } catch (e: Exception) {
                getDailyChallengeQuestions()
            }
        } else {
            getDailyChallengeQuestions()
        }
    }

    suspend fun fetchLeaderboard(): List<RankingUser> {
        val currentUser = _userProfile.value

        if (isSupabaseConfigured) {
            try {
                val profiles = SupabaseClientManager.client.postgrest["profiles"]
                    .select {
                        order(column = "total_points", order = Order.DESCENDING)
                    }
                    .decodeList<ProfileDto>()

                if (profiles.isNotEmpty()) {
                    return profiles.mapIndexed { index, profile ->
                        val isCurrent = (profile.id == currentUser.id || profile.username == currentUser.username)
                        RankingUser(
                            rank = index + 1,
                            username = if (isCurrent) currentUser.username else profile.username,
                            points = if (isCurrent) currentUser.totalPoints else profile.totalPoints,
                            avatarUrl = if (isCurrent) (currentUser.avatarUrl ?: "⚽") else (profile.avatarUrl ?: "⚽"),
                            isCurrentUser = isCurrent
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fallback local con usuarios de demostración si la nube está sin datos
        val defaultList = mutableListOf(
            RankingUser(1, "Messi10_GOAT", 890, avatarUrl = "👑"),
            RankingUser(2, "ScalonetaFan", 760, avatarUrl = "🇦🇷"),
            RankingUser(3, "Xeneize_99", 620, avatarUrl = "⚡"),
            RankingUser(4, currentUser.username, currentUser.totalPoints, avatarUrl = currentUser.avatarUrl ?: "⚽", isCurrentUser = true),
            RankingUser(5, "ElDiego_86", 310, avatarUrl = "🏆"),
            RankingUser(6, "Millonario_18", 290, avatarUrl = "🛡️")
        )
        return defaultList.sortedByDescending { it.points }.mapIndexed { idx, item ->
            item.copy(rank = idx + 1)
        }
    }

    suspend fun updateUserProfile(newUsername: String, newAvatar: String?) {
        _userProfile.update { current ->
            current.copy(
                username = newUsername,
                avatarUrl = newAvatar
            )
        }

        if (isSupabaseConfigured) {
            try {
                val current = _userProfile.value
                val dto = ProfileDto(
                    id = current.id,
                    username = current.username,
                    avatarUrl = current.avatarUrl,
                    totalPoints = current.totalPoints,
                    currentStreak = current.currentStreak,
                    bestStreak = current.bestStreak,
                    gamesPlayed = current.gamesPlayed
                )
                SupabaseClientManager.client.postgrest["profiles"].upsert(dto)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun useStreakSaverDemo() {
        _userProfile.update { current ->
            if (current.streakSaversCount > 0) {
                current.copy(
                    streakSaversCount = current.streakSaversCount - 1,
                    wasStreakProtectedToday = true
                )
            } else current
        }
    }

    fun dismissStreakProtectedBanner() {
        _userProfile.update { current ->
            current.copy(wasStreakProtectedToday = false)
        }
    }

    // Garantiza matemáticamente que TODOS los usuarios reciban las MISMAS 10 preguntas en el mismo día
    fun getDailyChallengeQuestions(): List<Question> {
        val todayString = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val dateSeed = todayString.hashCode().toLong()
        return MockQuestions.dailyQuestions.shuffled(Random(dateSeed)).take(10)
    }

    fun getTrainingCategories(): List<Category> {
        return MockQuestions.categories
    }

    fun getQuestionsForCategory(categoryId: String): List<Question> {
        return MockQuestions.trainingQuestions[categoryId] ?: MockQuestions.dailyQuestions.take(5)
    }

    fun completeDailyChallenge(scoreObtained: Int) {
        _userProfile.update { current ->
            val newPoints = current.totalPoints + scoreObtained
            val newStreak = current.currentStreak + 1
            val bestStreak = maxOf(current.bestStreak, newStreak)
            current.copy(
                totalPoints = newPoints,
                currentStreak = newStreak,
                bestStreak = bestStreak,
                gamesPlayed = current.gamesPlayed + 1,
                hasCompletedDailyChallenge = true,
                wasStreakProtectedToday = false
            )
        }
    }

    fun completeTrainingGame(scoreObtained: Int) {
        _userProfile.update { current ->
            current.copy(
                gamesPlayed = current.gamesPlayed + 1
            )
        }
    }

    fun completeSprintGame(scoreObtained: Int) {
        _userProfile.update { current ->
            val bestSprint = maxOf(current.sprintBestScore, scoreObtained)
            current.copy(
                sprintBestScore = bestSprint,
                gamesPlayed = current.gamesPlayed + 1
            )
        }
    }
}
