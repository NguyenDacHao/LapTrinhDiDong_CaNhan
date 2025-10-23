package com.example.qltv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.qltv.Screen.LibraryScreen
import com.example.qltv.ui.theme.QLTVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QLTVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LibraryScreen()
                }
            }
        }
    }
}

// Bạn cần thêm Theme cơ bản trong ui/theme/Theme.kt để hiển thị đúng
// Ví dụ về cấu trúc Theme:
// object LibraryManagerTheme { ... }

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    QLTVTheme {
        LibraryScreen()
    }
}