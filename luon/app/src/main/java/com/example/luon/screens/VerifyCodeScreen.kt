package com.example.luon.screens



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun VerifyCodeScreen(navController: NavController, email: String?) {
    var code by remember { mutableStateOf("") }

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
            Text("Verify Code", style = MaterialTheme.typography.titleLarge)
            Text("Enter the code we sent to: $email")
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Verification Code") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            BlueButton(text = "Next") {
                if (code.isNotEmpty()) {
                    navController.navigate("reset_password/$email/$code")
                }
            }
        }
    }
}