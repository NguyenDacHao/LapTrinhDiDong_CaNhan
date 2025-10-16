package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.clickable
import androidx.compose.material3.Divider
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "welcome") {
                        composable("welcome") { WelcomeScreen(navController) }
                        composable("components") { ComponentsScreen(navController) }


                        composable("textfield") { TextFieldScreen(navController) }
                        composable("password") { PasswordFieldScreen(navController) }
                        composable("column") { ColumnScreen(navController) }
                        composable("row") { RowScreen(navController) }
                        composable("text") { TextScreen(navController) }
                        composable("image") { ImageScreen(navController) }
                        composable("box") { BoxScreen(navController) }
                        composable("checkbox_multi") { MultipleCheckboxScreen(navController) }
                        composable("checkbox_single") { SingleCheckboxScreen(navController) }

                    }
                }
            }
        }
    }
}


@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.jetpack_logo),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Jetpack Compose",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(250.dp))
        Button(
            onClick = { navController.navigate("components")},
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Text("I'm ready", fontSize = 16.sp)
        }
    }
}


@Composable
fun ComponentsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "UI Components List",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF007AFF),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Display")
        ComponentButton("Text", "Displays text", "text", navController)
        ComponentButton("Image", "Displays an image", "image", navController)

        SectionHeader("Input")
        ComponentButton("TextField", "Input field for text", "textfield", navController)
        ComponentButton("PasswordField", "Input field for passwords", "password", navController)

        SectionHeader("Layout")
        ComponentButton("Column", "Arranges elements vertically", "column", navController)
        ComponentButton("Row", "Arranges elements horizontally", "row", navController)
        ComponentButton("Box", "Stacks elements (Z-axis)", "box", navController)
        SectionHeader("checkbox")
        ComponentButton("Checkbox (Multi)", "Chọn nhiều lựa chọn", "checkbox_multi", navController)
        ComponentButton("Checkbox (Single)", "Chỉ chọn một lựa chọn", "checkbox_single", navController)

    }
}


@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

// --- ComponentButton (Nút điều hướng thành phần) ---
@Composable
fun ComponentButton(
    title: String,
    description: String,
    route: String,
    navController: NavController
) {
    Button(
        onClick = { navController.navigate(route) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD6EAF8),
            contentColor = Color.Black
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

// --- TextScreen (Demo Text) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Component Demo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Áp dụng padding từ Scaffold
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()), // Cho phép cuộn
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // 1. Kích thước và Màu sắc
            SectionTitle("1. Kích thước và Màu sắc")

            Text(
                text = "Tiêu đề Lớn (Display Large)",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Đoạn văn Thường (Body Large)",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF007AFF) // Màu xanh custom
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 2. Kiểu dáng (Font Styles)
            SectionTitle("2. Kiểu dáng (Styles)")

            Text(
                text = "Chữ Đậm (Bold)",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Chữ Nghiêng (Italic)",
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Gạch Chân (Underline)",
                style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Gạch ngang (Strikethrough)",
                style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 3. Căn chỉnh (Alignment)
            SectionTitle("3. Căn chỉnh (Alignment)")

            Text(
                text = "Căn giữa (Center)",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().background(Color(0xFFF0F0F0))
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Căn phải (End)",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().background(Color(0xFFF0F0F0))
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 4. Giới hạn (Overflow)
            SectionTitle("4. Giới hạn (Overflow)")

            Text(
                text = "Đoạn văn này rất dài và sẽ bị giới hạn bởi maxLines. Những phần vượt quá sẽ hiển thị dấu ba chấm (ellipsis).",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))


        }
    }
}
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
    )
    Divider(modifier = Modifier.padding(bottom = 8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TextField") }, // <-- Tiêu đề "TextField"
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(80.dp))


            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Thông tin nhập") },
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    // containerColor = Color.White,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Text thông báo cập nhật
            Text(
                text = "Tự động cập nhật dữ liệu theo TextField",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Images") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            val imageUrl2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs7xai-43it1XUbXNJd9cERnivt7IUV0VTJQ&s"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = imageUrl2,
                        contentDescription = "Image from URL 1",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = imageUrl2,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(R.drawable.inapp_image),
                        contentDescription = "Image from app resources",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "In app",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldScreen(navController: NavController) {
    // 1. State quản lý nội dung mật khẩu
    var password by remember { mutableStateOf("") }
    // 2. State quản lý trạng thái ẩn/hiện mật khẩu (Mặc định là ẩn)
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Logic để xác định kiểu hiển thị (Ẩn dấu chấm hay Hiện chữ)
    val visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    // Logic để xác định Icon (Mắt mở hay Mắt nhắm)
    val icon = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Password Field Demo") },
                navigationIcon = {
                    // Nút mũi tên trở về
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Trở về")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Áp dụng padding từ TopAppBar
                .padding(16.dp),
            // Căn giữa nội dung theo chiều ngang
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Nhập mật khẩu",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter password") },
                singleLine = true,
                // Áp dụng Visual Transformation (ẩn hoặc hiện)
                visualTransformation = visualTransformation,
                // Đảm bảo bàn phím là kiểu mật khẩu
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                // Thêm icon ở cuối TextField để toggle visibility
                trailingIcon = {
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            }
        }
    }

@Composable
fun SimpleBlock(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Column Demo (Blocks)") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Trở về")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            // Áp dụng padding từ TopAppBar
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            SimpleBlock(
                color = Color.Red,
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            )
            SimpleBlock(
                color = Color.Green,
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp)
            )
            SimpleBlock(
                color = Color.Blue,
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Row Demo (Blocks)") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Trở về")
                    }
                }
            )
        }
    ) { paddingValues ->
        Row(
            // Áp dụng padding từ TopAppBar
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SimpleBlock(
                color = Color.Red,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.7f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            SimpleBlock(
                color = Color.Green,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.7f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            SimpleBlock(
                color = Color.Blue,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.7f)
            )
        }
    }
}
@Composable
fun DemoScreenTemplate(title: String, navController: NavController, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))
            content()
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Box Component Demo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Trở về")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFBBDEFB))
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp)),

            ) {

                Text(
                    text = "",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color(0x33000000)
                )


                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E88E5))
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("", color = Color.White)
                }


                Text(
                    text = "hao",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Red.copy(alpha = 0.8f))
                        .padding(4.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


data class CheckboxItem(
    val id: Int,
    val label: String,
    var isChecked: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleCheckboxScreen(navController: NavController) {

    val selectionItems = remember {
        mutableStateListOf(
            CheckboxItem(1, "Tôi thích cà phê"),
            CheckboxItem(2, "Tôi thích trà sữa", isChecked = true),
            CheckboxItem(3, "Tôi thích nước ép")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkbox: Chọn Nhiều") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Trở về")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            SectionTitle("Chọn các mục bạn muốn (Multiple)")

            selectionItems.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Cập nhật state list khi click vào toàn bộ hàng
                            selectionItems[index] = item.copy(isChecked = !item.isChecked)
                        }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = { isChecked ->
                            selectionItems[index] = item.copy(isChecked = isChecked)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(item.label, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider()

            // Hiển thị trạng thái
            val selectedLabels = selectionItems.filter { it.isChecked }.joinToString { it.label }
            Text("Đã chọn: $selectedLabels", modifier = Modifier.padding(top = 10.dp))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleCheckboxScreen(navController: NavController) {

    var selectedOptionId by remember { mutableStateOf(1) }

    val selectionLabels = listOf(
        CheckboxItem(1, "Lựa chọn 1: Thẻ tín dụng"),
        CheckboxItem(2, "Lựa chọn 2: Ví điện tử"),
        CheckboxItem(3, "Lựa chọn 3: Thanh toán khi nhận hàng")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkbox: Chọn Một") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Trở về")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            SectionTitle("Chọn một phương thức thanh toán (Single)")

            selectionLabels.forEach { item ->

                val isSelected = item.id == selectedOptionId

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            selectedOptionId = item.id
                        }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = {
                            if (it) {
                                selectedOptionId = item.id
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(item.label, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider()


            val selectedLabel = selectionLabels.first { it.id == selectedOptionId }.label
            Text("Đang chọn: $selectedLabel", modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    JetpackComposeTheme {
        val navController = rememberNavController()
        WelcomeScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ComponentsScreenPreview() {
    JetpackComposeTheme {
        val navController = rememberNavController()
        ComponentsScreen(navController)
    }
}