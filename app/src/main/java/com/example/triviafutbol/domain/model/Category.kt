package com.example.triviafutbol.domain.model

data class Category(
    val id: String,
    val name: String,
    val icon: String, // Emoji o nombre de ícono
    val description: String,
    val questionCount: Int
)
