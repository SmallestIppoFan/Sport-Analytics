package com.example.burgermenu.model

data class DataX(
    val away_strength: Double,
    val away_team: String,
    val competition_cluster: String,
    val competition_name: String,
    val distance_between_teams: Int,
    val federation: String,
    val field_length: Int,
    val field_width: Int,
    val home_strength: Double,
    val home_team: String,
    val id: Int,
    var is_expired: Boolean,
    val last_update_at: String,
    val market: String,
    val odds: Odds,
    val prediction: String,
    val probabilities: Probabilities,
    val result: String,
    val season: String,
    val stadium_capacity: Int,
    val start_date: String,
    val status: String
)