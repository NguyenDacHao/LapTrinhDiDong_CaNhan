package com.example.luon.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConfirmScreen(navController: NavController, email: String?, code: String?, password: String?) {
    ScreenWithBack(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SmartTasksHeaderContent()
            Spacer(Modifier.height(32.dp))
            Text("Confirm", style = MaterialTheme.typography.titleLarge)
            Text("We are here to help you!", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(24.dp))

            Text("Email: $email")
            Text("Code: $code")
            Text("Password: ${"*".repeat(password?.length ?: 0)}") // Che mật khẩu

            Spacer(Modifier.height(32.dp))
            BlueButton(text = "Submit") {
                // Điều hướng về màn hình forget_password, truyền dữ liệu và reset back stack
                navController.navigate("forget_password?summary_email=$email&summary_code=$code&summary_password=$password") {
                    // Xóa tất cả các màn hình trong back stack (bao gồm cả forget_password cũ)
                    popUpTo("forget_password") { inclusive = true }
                }
            }
        }
    }
}