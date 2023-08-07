package com.example.sportapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sportapp.navigation.BottomScreens
import com.example.sportapp.ui.theme.BackgroundColor
import com.example.sportapp.ui.theme.TopAppBarColor


@SuppressLint("SuspiciousIndentation")
@Composable
fun BottomBar() {
    val navController = rememberNavController()
    val screens = listOf(
        BottomScreens.Screen1,
        BottomScreens.Screen2,
        BottomScreens.Screen3
    )
        BottomNavigation(
            backgroundColor = TopAppBarColor, modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .clip(
                    RoundedCornerShape(40.dp),
                )
                .background(BackgroundColor),
            elevation = 20.dp,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            screens.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = screen.icon),
                            contentDescription = screen.label,
                            modifier = Modifier.size(30.dp),
                            tint = androidx.compose.ui.graphics.Color.White
                        )
                    },
                    label = {
                        Text(
                            screen.label,
                            fontWeight = FontWeight.SemiBold,
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    })
            }
        }
}



