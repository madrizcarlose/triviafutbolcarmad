package com.example.triviafutbol.data.model

import com.example.triviafutbol.domain.model.Question
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val id: String,
    @SerialName("category_id") val categoryId: String? = null,
    val text: String,
    val options: List<String>,
    @SerialName("correct_answer_index") val correctAnswerIndex: Int,
    val explanation: String? = null,
    val difficulty: String = "Media"
) {
    fun toDomainModel(categoryName: String = "General"): Question {
        return Question(
            id = id,
            text = text,
            options = options,
            correctAnswerIndex = correctAnswerIndex,
            explanation = explanation,
            category = categoryName,
            difficulty = difficulty
        )
    }
}
