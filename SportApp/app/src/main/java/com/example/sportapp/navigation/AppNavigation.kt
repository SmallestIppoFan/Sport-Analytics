package com.example.sportapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sportapp.screens.apiPerfomance.PerfomanceScreen
import com.example.sportapp.screens.detail.DetailScreen
import com.example.sportapp.screens.live.LiveScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigtaion() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.name) {
        composable(Screens.MainScreen.name) {
            LiveScreen(navController)
        }
        composable(Screens.DetailScreen.name+"/{id}"){
            val id = it.arguments?.getString("id")
            DetailScreen(id,navController)
        }
        composable(Screens.PerfomanceScreen.name){
            PerfomanceScreen(navController)
        }
    }
}

