package com.example.sportapp.modul

data class Details(
    val last_14_days: Last14Days,
    val last_30_days: Last14Days,
    val last_7_days: Last14Days,
    val yesterday: Yesterday
)