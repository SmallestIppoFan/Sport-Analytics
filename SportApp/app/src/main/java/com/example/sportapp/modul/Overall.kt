package com.example.sportapp.modul

data class Overall(
    val avg_goals_per_match: Double,
    val both_teams_scored: Int,
    val num_encounters: Int,
    val over_05: Int,
    val over_15: Int,
    val over_25: Int,
    val over_35: Int,
    val total_goals: Int
)