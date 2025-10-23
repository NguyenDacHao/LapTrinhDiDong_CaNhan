package com.example.luon.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    summaryEmail: String? = null,
    summaryCode: String? = null,
    summaryPassword: String? = null
) {
    var email by remember { mutableStateOf("") }

    // Kiểm tra xem có dữ liệu tóm tắt nào cần hiển thị không
    val showSummary = summaryEmail != null && summaryCode != null && summaryPassword != null

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

            // Logic mới: Chỉ hiển thị các thành phần còn lại nếu KHÔNG có tóm tắt dữ liệu
            if (showSummary) {
                // Hiển thị tóm tắt dữ liệu (thay thế phần nhập liệu)
                Card(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "THÀNH CÔNG! Dữ liệu đã gửi:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF007AFF)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("Email: $summaryEmail")
                        Text("Code: $summaryCode")
                        // Che mật khẩu khi hiển thị
                        Text("Password: ${"*".repeat(summaryPassword?.length ?: 0)}")
                    }
                }
            } else {
                // Hiển thị giao diện Forget Password bình thường
                Text("Forget Password?", style = MaterialTheme.typography.titleLarge)
                Text("Enter your Email, we will send you a verification code.")
                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Your Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))
                BlueButton(text = "Next") {
                    if (email.isNotEmpty()) {
                        navController.navigate("verify_code/$email")
                    }
                }
            }
        }
    }
}