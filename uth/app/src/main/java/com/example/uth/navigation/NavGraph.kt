package com.example.uth.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uth.screens.HomeScreen
import com.example.uth.screens.LoginScreen
import com.example.uth.screens.OnboardingScreen
import com.example.uth.screens.ProfileScreen
import com.example.uth.screens.SplashScreen
import com.example.uth.model.UserViewModel
import com.example.uth.screens.TaskDetailScreen

@Composable
fun NavGraph(navController: NavHostController) {
    // Khởi tạo ViewModel dùng chung cho toàn app
    val userViewModel: UserViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController, userViewModel)
        }
        composable("home") {
            HomeScreen(onTaskClick = { id ->
                navController.navigate("taskDetail/$id")
            })
        }
        composable("taskDetail/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toIntOrNull() ?: 0
            TaskDetailScreen(taskId = id, onBack = { navController.popBackStack() })
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController, userViewModel)
        }
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Profile : Screen("profile")
}
