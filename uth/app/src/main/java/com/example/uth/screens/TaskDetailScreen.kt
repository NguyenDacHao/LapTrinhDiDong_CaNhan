package com.example.uth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth.model.Attachment
import com.example.uth.model.Subtask
import com.example.uth.model.Task
import com.example.uth.network.ApiClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@Composable
fun TaskDetailScreen(
    taskId: Int,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var task by remember { mutableStateOf<Task?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // --- Logic Tải Task (có xử lý lỗi) ---
    LaunchedEffect(taskId) {
        isLoading = true
        errorMessage = null
        try {
            val response = ApiClient.instance.getTaskDetail(taskId)
            if (response.isSuccess && response.data != null) {
                task = response.data
            } else {
                errorMessage = response.message ?: "Không tìm thấy Task."
                task = null
            }
        } catch (e: HttpException) {
            errorMessage = "Lỗi máy chủ (${e.code()}). Vui lòng thử lại."
            task = null
        } catch (e: IOException) {
            errorMessage = "Lỗi kết nối mạng. Vui lòng kiểm tra Internet của bạn."
            task = null
        } catch (e: Exception) {
            errorMessage = "Đã xảy ra lỗi không xác định: ${e.message}"
            task = null
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TaskDetailTopBar(
                onBack = onBack,
                onDelete = {
                    scope.launch {
                        try {
                            ApiClient.instance.deleteTask(taskId)
                            onBack() // Quay lại sau khi xóa thành công
                        } catch (e: Exception) {
                            println("Lỗi xóa Task: ${e.message}")
                            // Hiển thị Snackbar hoặc Toast thông báo lỗi xóa
                            errorMessage = "Lỗi khi xóa Task. Vui lòng thử lại."
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> ErrorState(message = errorMessage!!, onBack = onBack)
                task != null -> TaskDetailContent(task!!)
            }
        }
    }
}

// --- CÁC COMPOSABLE PHỤ TRỢ ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopBar(onBack: () -> Unit, onDelete: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                "Detail",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
            }
        },
        actions = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Xóa Task", tint = Color(0xFFD32F2F))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun TaskDetailContent(task: Task) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Tiêu đề Task
            Text(task.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            // Mô tả Task
            Text(task.description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(Modifier.height(16.dp))

            // Thẻ Category, Status, Priority
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0CCD3), RoundedCornerShape(8.dp)) // Màu hồng nhạt
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoChip(Icons.Filled.Category, "Category", task.category)
                InfoChip(Icons.Filled.List, "Status", task.status)
                InfoChip(Icons.Filled.Flag, "Priority", task.priority)
            }
        }

        // Subtasks Section
        if (task.subtasks.isNotEmpty()) {
            item {
                Spacer(Modifier.height(8.dp))
                Text("Subtasks", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            items(task.subtasks) { subtask ->
                SubtaskItem(subtask)
            }
        }

        // Attachments Section
        if (task.attachments.isNotEmpty()) {
            item {
                Spacer(Modifier.height(8.dp))
                Text("Attachments", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            items(task.attachments) { attachment ->
                AttachmentItem(attachment)
            }
        }
    }
}

@Composable
fun InfoChip(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = Color.Black.copy(alpha = 0.8f), modifier = Modifier.size(20.dp))
        Text(label, fontSize = 11.sp, color = Color.Gray)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
    }
}

@Composable
fun SubtaskItem(subtask: Subtask) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = subtask.isCompleted,
            onCheckedChange = { /* Xử lý cập nhật subtask */ },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF4CAF50),
                uncheckedColor = Color.Gray
            ),
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = subtask.title,
            fontSize = 15.sp,
            color = if (subtask.isCompleted) Color.Gray else Color.Black
        )
    }
}

@Composable
fun AttachmentItem(attachment: Attachment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.AttachFile,
            contentDescription = "Attachment",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(attachment.fileName, fontSize = 15.sp, color = Color.Black)
        // Có thể thêm icon để mở file ở đây nếu cần
    }
}


@Composable
fun ErrorState(message: String, onBack: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Lỗi: $message",
            color = Color.Red,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onBack) {
            Text("Quay lại")
        }
    }
}