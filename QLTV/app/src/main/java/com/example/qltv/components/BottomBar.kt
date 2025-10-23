package com.example.qltv.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar() {
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        // Tab Quản lý (đang được chọn)
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Quản lý") },
            label = { Text("Quản lý") },
            selected = true,
            onClick = {},
            selectedContentColor = Color(0xFF007AFF) // Màu xanh chủ đạo
        )
        // Tab DS Sách
        BottomNavigationItem(
            icon = { Icon(Icons.Default.List, contentDescription = "DS Sách") },
            label = { Text("DS Sách") },
            selected = false,
            onClick = {}
        )
        // Tab Sinh viên
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Sinh viên") },
            label = { Text("Sinh viên") },
            selected = false,
            onClick = {}
        )
    }
}