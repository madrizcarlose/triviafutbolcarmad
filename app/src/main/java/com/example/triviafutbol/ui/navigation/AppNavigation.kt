package com.example.triviafutbol.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.triviafutbol.data.repository.TriviaRepository
import com.example.triviafutbol.domain.model.GameMode
import com.example.triviafutbol.ui.home.HomeScreen
import com.example.triviafutbol.ui.home.HomeViewModel
import com.example.triviafutbol.ui.profile.ProfileScreen
import com.example.triviafutbol.ui.quiz.QuizScreen
import com.example.triviafutbol.ui.quiz.QuizViewModel
import com.example.triviafutbol.ui.ranking.RankingScreen
import com.example.triviafutbol.ui.ranking.RankingViewModel
import com.example.triviafutbol.ui.result.ResultScreen
import com.example.triviafutbol.ui.training.TrainingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Instancia compartida del repositorio
    val sharedRepository = TriviaRepository()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // 🏠 Pantalla de Inicio
        composable(Screen.Home.route) {
            val homeViewModel = viewModel<HomeViewModel> { HomeViewModel(sharedRepository) }
            HomeScreen(
                viewModel = homeViewModel,
                onStartDailyChallenge = {
                    navController.navigate(Screen.Quiz.createRoute(GameMode.DAILY_CHALLENGE.name))
                },
                onStartSprint = {
                    navController.navigate(Screen.Quiz.createRoute(GameMode.SPRINT.name))
                },
                onOpenTraining = {
                    navController.navigate(Screen.Training.route)
                },
                onOpenRanking = {
                    navController.navigate(Screen.Ranking.route)
                },
                onOpenProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        // ⚽ Pantalla de Trivia / Quiz
        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType },
                navArgument("categoryId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val modeString = backStackEntry.arguments?.getString("mode") ?: GameMode.DAILY_CHALLENGE.name
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val mode = GameMode.valueOf(modeString)

            val quizViewModel = viewModel<QuizViewModel> { QuizViewModel(sharedRepository) }

            QuizScreen(
                viewModel = quizViewModel,
                mode = mode,
                categoryId = categoryId,
                onGameFinished = { score, correct, modeName, isRedCard ->
                    navController.navigate(Screen.Result.createRoute(score, correct, modeName, isRedCard)) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        // 🏆 Pantalla de Resultados
        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("correct") { type = NavType.IntType },
                navArgument("mode") { type = NavType.StringType },
                navArgument("isRedCard") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val correct = backStackEntry.arguments?.getInt("correct") ?: 0
            val isRedCard = backStackEntry.arguments?.getBoolean("isRedCard") ?: false

            ResultScreen(
                score = score,
                correctCount = correct,
                isRedCard = isRedCard,
                onNavigateHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateRanking = {
                    navController.navigate(Screen.Ranking.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        // 🎯 Modo Entrenamiento
        composable(Screen.Training.route) {
            TrainingScreen(
                onSelectCategory = { categoryId ->
                    navController.navigate(Screen.Quiz.createRoute(GameMode.TRAINING.name, categoryId))
                },
                onBackToHome = {
                    navController.popBackStack()
                }
            )
        }

        // 📊 Ranking Global en Vivo desde Supabase
        composable(Screen.Ranking.route) {
            val rankingViewModel = viewModel<RankingViewModel> { RankingViewModel(sharedRepository) }
            RankingScreen(
                viewModel = rankingViewModel,
                onBackToHome = {
                    navController.popBackStack()
                }
            )
        }

        // 👤 Perfil
        composable(Screen.Profile.route) {
            val homeViewModel = viewModel<HomeViewModel> { HomeViewModel(sharedRepository) }
            ProfileScreen(
                viewModel = homeViewModel,
                onBackToHome = {
                    navController.popBackStack()
                }
            )
        }
    }
}
