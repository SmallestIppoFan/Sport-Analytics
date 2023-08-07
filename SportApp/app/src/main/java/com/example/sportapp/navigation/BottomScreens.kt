package com.example.sportapp.navigation

import androidx.compose.runtime.Composable
import com.example.sportapp.R
import com.example.sportapp.screens.leagues.LeaguesScreen
import com.example.sportapp.screens.live.LiveScreen
import com.example.sportapp.screens.matchs.MatchesScreen

sealed class BottomScreens(val route: String, val label: String,  val icon: Int) {
    object Screen1 : BottomScreens("screen1", "Live", R.drawable.ic_screen1)
    object Screen2 : BottomScreens("screen2", "Matches", R.drawable.ic_screen2)
    object Screen3 : BottomScreens("screen3", "Leagues", R.drawable.ic_screen3)

}
