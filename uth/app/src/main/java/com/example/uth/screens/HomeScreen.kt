package com.example.uth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth.model.Task
import com.example.uth.network.ApiClient
import kotlinx.coroutines.launch
import com.example.uth.R

// --- HOME SCREEN CHÍNH ---

@Composable
fun HomeScreen(
    onTaskClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Gọi API khi khởi chạy màn hình (refresh data)
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = ApiClient.instance.getTasks()
                tasks = response.data ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                tasks = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottomBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Xử lý khi nhấn nút Thêm Task */ },
                containerColor = Color(0xFF4285F4),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Thêm Task")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when {
                isLoading -> LoadingView()
                tasks.isEmpty() -> EmptyView()
                else -> TaskListView(tasks, onTaskClick)
            }
        }
    }
}

// --- THANH ĐIỀU HƯỚNG VÀ UI PHỤ TRỢ ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Logo hình ảnh UT-H
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo UT-H",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(8.dp))
                // Tên ứng dụng
                Text(
                    text = "SmartTasks",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        actions = {
            IconButton(onClick = { /* Xử lý khi nhấn chuông */ }) {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Thông báo",
                    tint = Color(0xFFFBC02D)
                )
            }
        }
    )
}

@Composable
fun HomeBottomBar() {
    BottomAppBar(
        containerColor = Color.White,
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.height(60.dp)
    ) {
        // Trái
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HomeBottomNavItem(icon = Icons.Filled.Home, label = "Home", isSelected = true)
            HomeBottomNavItem(icon = Icons.Filled.DateRange, label = "Calendar", isSelected = false)
        }

        Spacer(modifier = Modifier.width(60.dp)) // Khoảng trống cho FAB

        // Phải
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HomeBottomNavItem(icon = Icons.Filled.DateRange, label = "Files", isSelected = false)
            HomeBottomNavItem(icon = Icons.Filled.Settings, label = "Settings", isSelected = false)
        }
    }
}

@Composable
fun HomeBottomNavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { /* Xử lý chuyển đổi màn hình */ }
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color.Black else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.EventBusy,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text("No Tasks Yet!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Stay productive — add something to do")
    }
}

// --- TASK LIST VÀ ITEM ---

@Composable
fun TaskListView(tasks: List<Task>, onTaskClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { task ->
            val taskColor = getTaskCardColor(task.id)
            TaskItem(task, taskColor, onClick = { onTaskClick(task.id) })
        }
    }
}

@Composable
fun TaskItem(task: Task, cardColor: Color, onClick: () -> Unit) {
    val isCompleted = task.status.equals("Completed", ignoreCase = true)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Checkbox
            Checkbox(
                checked = isCompleted,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50),
                    uncheckedColor = Color.Black.copy(alpha = 0.6f)
                ),
                modifier = Modifier.size(24.dp)
            )

            // 2. Nội dung chính
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = task.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${task.description.split(".").firstOrNull() ?: "No description provided"}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status: ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = task.status,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = getStatusColor(task.status)
                    )
                }
            }

            // 3. Thời gian/Ngày tháng
            Column(horizontalAlignment = Alignment.End) {
                val displayTime = task.createdAt.takeIf { it.length > 5 }?.substring(11, 16) ?: "N/A"
                Text(
                    text = displayTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = task.dueDate,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// --- HÀM TIỆN ÍCH MÀU SẮC ---

fun getTaskCardColor(taskId: Int): Color {
    // Gán màu sắc giả định dựa trên ID để mô phỏng hình ảnh
    return when (taskId % 3) {
        1 -> Color(0xFFF0CCD3) // Màu hồng nhạt
        2 -> Color(0xFFE5F5D4) // Màu xanh lá nhạt
        else -> Color(0xFFD4E8F8) // Màu xanh dương nhạt
    }
}

fun getStatusColor(status: String): Color {
    return when (status.uppercase()) {
        "IN PROGRESS" -> Color(0xFFD32F2F)
        "PENDING" -> Color(0xFFFBC02D)
        "COMPLETED" -> Color(0xFF388E3C)
        else -> Color.Gray
    }
}