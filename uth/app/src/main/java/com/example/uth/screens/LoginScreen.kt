package com.example.uth.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uth.R // Đảm bảo bạn có R.drawable.uth_logo và R.drawable.google_icon
import com.example.uth.model.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Bạn cần đảm bảo các hàm này đã được định nghĩa ở AuthUtils.kt hoặc tương tự
// fun getGoogleSignInClient(context: Context): GoogleSignInClient
// @Composable fun rememberGoogleSignInLauncher(onResult: suspend (ActivityResult) -> Unit): ManagedActivityResultLauncher<Intent, ActivityResult>
// suspend fun firebaseAuthWithGoogle(...)

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }
    val googleSignInClient = remember { getGoogleSignInClient(context) }

    // Launcher nhận kết quả đăng nhập Google
    val launcher = rememberGoogleSignInLauncher { result ->
        CoroutineScope(Dispatchers.Main).launch {
            firebaseAuthWithGoogle(
                result = result,
                auth = auth,
                onSuccess = {
                    Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()

                    // Lưu user vào ViewModel
                    userViewModel.user = auth.currentUser

                    // Điều hướng sang ProfileScreen
                    // Đảm bảo "profile" là route hợp lệ trong NavGraph của bạn
                    navController.navigate("profile") {
                        popUpTo("login") { inclusive = true } // Đóng màn hình login
                    }
                },
                onError = { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("GoogleAuth", "Error: $errorMessage")
                }
            )
        }
    }

    // Giao diện người dùng
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Dùng SpaceBetween để đẩy logo lên và text © xuống
    ) {
        // Phần trên: Logo và tên ứng dụng
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp)) // Khoảng cách từ trên xuống

            // Logo UTH
            Image(
                painter = painterResource(id = R.drawable.logo), // !!! THAY THẾ BẰNG ID RESOURCE LOGO CỦA BẠN !!!
                contentDescription = "UTH Logo",
                modifier = Modifier.size(120.dp) // Kích thước logo
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Tên ứng dụng "SmartTasks"
            Text(
                text = "SmartTasks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007BFF) // Màu xanh của "SmartTasks"
            )
            // Mô tả ứng dụng
            Text(
                text = "A simple and efficient to-do app",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(80.dp)) // Khoảng cách giữa phần trên và Welcome text

            // Phần giữa: Welcome text và nút Đăng nhập Google
            Text(
                text = "Welcome",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Ready to explore? Log in to get started.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Nút "SIGN IN WITH GOOGLE"
            Button(
                onClick = {
                    launcher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8F0FE), // Màu nền nút hơi xanh nhạt
                    contentColor = Color(0xFF007BFF) // Màu chữ xanh đậm
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp) // Loại bỏ đổ bóng
            ) {
                // Icon Google (Bạn cần thêm icon Google vào drawable)
                Image(
                    painter = painterResource(id = R.drawable.google_icon), // !!! THAY THẾ BẰNG ID RESOURCE ICON GOOGLE CỦA BẠN !!!
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "SIGN IN WITH GOOGLE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Phần dưới cùng: Copyright text
        Text(
            text = "© UTHSmartTasks",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp) // Khoảng cách từ dưới lên
        )
    }
}