package com.example.triviafutbol.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Training : Screen("training")
    object Ranking : Screen("ranking")
    object Profile : Screen("profile")
    
    object Quiz : Screen("quiz/{mode}?categoryId={categoryId}") {
        fun createRoute(mode: String, categoryId: String? = null): String {
            return if (categoryId != null) {
                "quiz/$mode?categoryId=$categoryId"
            } else {
                "quiz/$mode"
            }
        }
    }
    
    object Result : Screen("result/{score}/{correct}/{mode}/{isRedCard}") {
        fun createRoute(score: Int, correct: Int, mode: String, isRedCard: Boolean): String {
            return "result/$score/$correct/$mode/$isRedCard"
        }
    }
}
