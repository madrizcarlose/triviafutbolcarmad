package com.example.triviafutbol.data.model

import com.example.triviafutbol.domain.model.UserProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String,
    val username: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("total_points") val totalPoints: Int = 0,
    @SerialName("current_streak") val currentStreak: Int = 0,
    @SerialName("best_streak") val bestStreak: Int = 0,
    @SerialName("games_played") val gamesPlayed: Int = 0
) {
    fun toDomainModel(): UserProfile {
        return UserProfile(
            id = id,
            username = username,
            avatarUrl = avatarUrl,
            totalPoints = totalPoints,
            currentStreak = currentStreak,
            bestStreak = bestStreak,
            gamesPlayed = gamesPlayed
        )
    }
}
