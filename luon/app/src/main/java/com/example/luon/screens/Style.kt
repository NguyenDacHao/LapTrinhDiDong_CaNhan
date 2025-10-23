package com.example.luon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.luon.R // Thay bằng R của dự án của bạn nếu khác

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithBack(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = {
            // Chỉ hiển thị nút trở về nếu có màn hình trước đó trong Back Stack
            if (navController.previousBackStackEntry != null) {
                TopAppBar(
                    title = { /* Title is often empty in a flow like this */ },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Trở về")
                        }
                    }
                )
            } else {
                // TopAppBar trống cho màn hình gốc (Forget Password)
                TopAppBar(title = {})
            }
        },
        content = content
    )
}

@Composable
fun SmartTasksHeaderContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Giả định R.drawable.logo tồn tại trong dự án của bạn
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "UTH Logo",
            modifier = Modifier
                .height(80.dp)
                .padding(bottom = 12.dp)
        )
        Text("SmartTasks", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF007AFF))
    }
}

@Composable
fun BlueButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}