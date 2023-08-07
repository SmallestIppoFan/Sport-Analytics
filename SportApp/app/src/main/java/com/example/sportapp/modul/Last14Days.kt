package com.example.sportapp.modul

data class Last14Days(
    val lost: Int,
    val pending: Int,
    val postponed: Int,
    val total: Int,
    val won: Int
)