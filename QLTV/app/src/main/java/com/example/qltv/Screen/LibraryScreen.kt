package com.example.qltv.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qltv.model.Book
import com.example.qltv.viewmodel.LibraryViewModel
import com.example.qltv.components.BottomBar

@Composable
fun LibraryScreen(viewModel: LibraryViewModel = viewModel()) {
    val student by viewModel.currentStudent.collectAsState()
    val books by viewModel.borrowedBooks.collectAsState()

    Scaffold(
        bottomBar = { BottomBar() }
    ) { padding ->
        // Căn giữa toàn bộ nội dung
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ==== Tiêu đề ====
                Text(
                    text = "Hệ thống",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Quản lý Thư viện",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ==== Sinh viên ====
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Sinh viên",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = student.name,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = viewModel::changeStudent,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D6EFD)),
                            modifier = Modifier.height(50.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Thay đổi", color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ==== Danh sách sách ====
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Danh sách sách",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEFEFEF), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        if (books.isEmpty()) {
                            Text("Chưa có sách nào", color = Color.Gray)
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(books) { book ->
                                    BookItemRow(title = book.title)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ==== Nút thêm ====
                Button(
                    onClick = viewModel::borrowBook,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D6EFD)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Thêm", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ==== Composable hiển thị từng sách ====
@Composable
fun BookItemRow(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(30.dp))
            .border(1.dp, Color.White, RoundedCornerShape(30.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color(0xFFD81B60), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, fontSize = 16.sp, color = Color.Black)
    }
}
