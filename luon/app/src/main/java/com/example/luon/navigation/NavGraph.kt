package com.example.luon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.luon.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    // Thêm các tham số tùy chọn cho Summary
    val startDestination = "forget_password?summary_email={summary_email}&summary_code={summary_code}&summary_password={summary_password}"

    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = "forget_password?summary_email={summary_email}&summary_code={summary_code}&summary_password={summary_password}",
            arguments = listOf(
                navArgument("summary_email") { defaultValue = null; nullable = true },
                navArgument("summary_code") { defaultValue = null; nullable = true },
                navArgument("summary_password") { defaultValue = null; nullable = true }
            )
        ) { backStackEntry ->
            val summaryEmail = backStackEntry.arguments?.getString("summary_email")
            val summaryCode = backStackEntry.arguments?.getString("summary_code")
            val summaryPassword = backStackEntry.arguments?.getString("summary_password")
            ForgetPasswordScreen(navController, summaryEmail, summaryCode, summaryPassword)
        }

        composable("verify_code/{email}") {
            val email = it.arguments?.getString("email")
            VerifyCodeScreen(navController, email)
        }

        composable("reset_password/{email}/{code}") {
            val email = it.arguments?.getString("email")
            val code = it.arguments?.getString("code")
            ResetPasswordScreen(navController, email, code)
        }

        composable("confirm/{email}/{code}/{password}") {
            val email = it.arguments?.getString("email")
            val code = it.arguments?.getString("code")
            val password = it.arguments?.getString("password")
            ConfirmScreen(navController, email, code, password)
        }
    }
}