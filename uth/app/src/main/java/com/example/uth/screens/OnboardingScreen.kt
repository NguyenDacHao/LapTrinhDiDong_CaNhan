package com.example.uth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth.R
import androidx.navigation.NavController
import com.example.uth.model.OnboardingPage
import com.example.uth.navigation.Screen

@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(
            "Easy Time Management",
            "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first.",
            R.drawable.onboard1
        ),
        OnboardingPage(
            "Increase Work Effectiveness",
            "Time management and task determination will give your job statistics better and always improve.",
            R.drawable.onboard2
        ),
        OnboardingPage(
            "Reminder Notification",
            "This application also provides reminders so you don't forget tasks and complete them on time.",
            R.drawable.onboard3
        )
    )

    var pageIndex by remember { mutableIntStateOf(0) }
    val page = pages[pageIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                pages.forEachIndexed { index, _ ->
                    val color = if (index == pageIndex) Color(0xFF007BFF) else Color(0xFFC4C4C4)
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                    if (index < pages.lastIndex) Spacer(modifier = Modifier.width(8.dp))
                }
            }

            TextButton(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
            ) {
                Text("skip", fontSize = 16.sp)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = null,
                modifier = Modifier.size(240.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(page.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                page.description,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = if (pageIndex == 0) Arrangement.Center else Arrangement.SpaceBetween, // Căn giữa nếu là trang 0
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pageIndex > 0) {
                Button(
                    onClick = { pageIndex-- },
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            if (pageIndex > 0) {
                Spacer(modifier = Modifier.width(16.dp))
            }


            Button(
                onClick = {
                    if (pageIndex < pages.lastIndex) pageIndex++
                    else {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                },
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                modifier = if (pageIndex == 0) Modifier
                    .fillMaxWidth() //
                    .height(50.dp)
                else Modifier
                    .weight(1f) //
                    .height(50.dp)
            ) {
                Text(
                    text = if (pageIndex < pages.lastIndex) "Next" else "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}