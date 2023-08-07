package com.example.sportapp.modul

data class DataXXX(
    val available_markets: List<String>,
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
    val is_expired: Boolean,
    val last_update_at: String,
    val prediction_per_market: PredictionPerMarket,
    val result: String,
    val season: String,
    val stadium_capacity: Int,
    val start_date: String
)