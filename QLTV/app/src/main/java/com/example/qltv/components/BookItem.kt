package com.example.qltv.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookItem(bookTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Giả lập ô checkbox (biểu tượng sách đã mượn)
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Đã Mượn",
            tint = Color(0xFF007AFF),
            modifier = Modifier
                .size(20.dp)
                .background(Color.White, RoundedCornerShape(4.dp))
                .border(1.dp, Color(0xFF007AFF), RoundedCornerShape(4.dp))
                .padding(2.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(bookTitle)
    }
}

@Composable
fun NoBooksMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
            color = Color.Gray,
            style = androidx.compose.material.MaterialTheme.typography.body1
        )
    }
}