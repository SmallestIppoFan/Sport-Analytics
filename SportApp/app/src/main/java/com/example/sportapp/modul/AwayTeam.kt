package com.example.sportapp.modul

data class AwayTeam(
    val avg_bookie_draw_chance: Double,
    val avg_bookie_lose_chance: Double,
    val avg_bookie_win_chance: Double,
    val avg_goals_conceived: Double,
    val avg_goals_scored: Double,
    val clean_sheet: Int,
    val draw: Int,
    val first_half_draw: Int,
    val first_half_lost: Int,
    val first_half_win: Int,
    val goals_conceived: Int,
    val goals_scored: Int,
    val lost: Int,
    val team_name: String,
    val won: Int
)